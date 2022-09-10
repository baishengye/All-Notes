## 三、`ServiceMethod`

### 1. `ServiceMethod`
前面提到`loadServiceMethod`执行的时候会调用`ServiceMethod`.`parseAnnotations()`方法将`method`转化为`ServiceMethod`实例。
```java
ServiceMethod<?> loadServiceMethod(Method method) {
    ServiceMethod<?> result = ServiceMethod.parseAnnotations(this, method);
}
```
`ServiceMethod`是一个抽象类，存储了我们网络请求的基本信息，他的`parseAnnotations()`方法如下
```java
static <T> ServiceMethod<T> parseAnnotations(Retrofit retrofit, Method method) {
	RequestFactory requestFactory = RequestFactory.parseAnnotations(retrofit, method);
	Type returnType = method.getGenericReturnType();
	if (Utils.hasUnresolvableType(returnType)) {
	  throw methodError(method,"Method return type must not include a type variable or wildcard: %s",returnType);
	}
	if (returnType == void.class) {
	  throw methodError(method, "Service methods cannot return void.");
	}
	return HttpServiceMethod.parseAnnotations(retrofit, method, requestFactory);
}
```
1. 首先通过`RequestFactory.parseAnnotations()`方法获取`RequestFactory`对象，它主要是解析我们定义在方法上面的注解。
2. 然后判断方法的返回值是否合法：返回值不能包含泛型(如`T)`或者通配符(如`? extends Number`)，并且返回值不能为void。
3. 最后调用`HttpServiceMethod.parseAnnotations()`方法获取`ServiceMethod`对象。

### 2. `HttpServiceMethod`
`HttpServiceMethod`是`ServiceMethod`的子类
```java
static <ResponseT, ReturnT> HttpServiceMethod<ResponseT, ReturnT> parseAnnotations(Retrofit retrofit, Method method, RequestFactory requestFactory) {
    //获取返回类型
    Type adapterType;
	//获取CallAdapter
    CallAdapter<ResponseT, ReturnT> callAdapter = createCallAdapter(retrofit, method, adapterType, annotations);
	//获取Converter
    Converter<ResponseBody, ResponseT> responseConverter =createResponseConverter(retrofit, method, responseType);
	//创建返回值
	return HttpServiceMethod;
}
```

#### 2.1 获取返回类型
首先通过判断当前函数是不是挂起函数，然后初始化不同的`adapterType`。
```java
if (isKotlinSuspendFunction) {
  Type[] parameterTypes = method.getGenericParameterTypes();
  Type responseType =
      Utils.getParameterLowerBound( 0, (ParameterizedType) parameterTypes[parameterTypes.length - 1]);
  if (getRawType(responseType) == Response.class && responseType instanceof ParameterizedType) {
    responseType = Utils.getParameterUpperBound(0, (ParameterizedType) responseType);
    continuationWantsResponse = true;
  }
  adapterType = new Utils.ParameterizedTypeImpl(null, Call.class, responseType);
  annotations = SkipCallbackExecutorImpl.ensurePresent(annotations);
} else {
  adapterType = method.getGenericReturnType();
}
```
这里为什么需要区分函数是不是挂起函数呢，我们需要一个例子看一下：
```java
suspend fun testSuspend(param:String):List<String>
suspend fun testResponse(param:String):Response<String>
fun testCommon(param:String):List<String>
```
我们先准备3个函数，前两个用`suspend`修饰，然后反编译为`java`代码
```java
Object testSuspend(@NotNull String var1, @NotNull Continuation var2);
List testCommon(@NotNull String var1);
Object testResponse(@NotNull String var1, @NotNull Continuation var2);
```

可以看出，挂起函数转化为`java`代码之后会增加一个`Continuation`类型的参数，而且返回值会变为`Object`，这种情况下如果我们继续使用`getGenericReturnType()`获取返回值的话，就不能获取到真正的返回值，而真正的返回值可以通过`Continuation`参数获取，获取出来之后，如果返回值是
`Response`就还需要进一步获取真实类型`T`。最后对于挂起函数的返回值再用`Call`包装一下变为`Call<List>`和`Call`。

#### 2.2 获取CallAdapter
`callAdapter`最后是调用`retrofit`的`nextCallAdapter()`方法获取的。
```java
public CallAdapter<?, ?> nextCallAdapter(CallAdapter.Factory skipPast, Type returnType, Annotation[] annotations) {
    int start = callAdapterFactories.indexOf(skipPast) + 1;
    for (int i = start, count = callAdapterFactories.size(); i < count; i++) {
        CallAdapter<?, ?> adapter = callAdapterFactories.get(i).get(returnType, annotations, this);
        if (adapter != null) {
            return adapter;
        }
    }
	//......省略异常日志
    throw new IllegalArgumentException(builder.toString());
}
```
通过`skipPast`，`returnType`，`annotations`从创建`Retrofit`对象时候初始化的`callAdapterFactories`中筛选出我们需要的`callAdapterFactory`，然后调用它的`get()`方法获取`CallAdapter`。

#### 2.3 获取`ResponseConverter`
`responseConverter`获取方式与`callAdapter`类似，也是在`Retrofit`类中获取的。
```java
public <T> Converter<ResponseBody, T> nextResponseBodyConverter(Converter.Factory skipPast, Type type, Annotation[] annotations) {
    int start = converterFactories.indexOf(skipPast) + 1;
    for (int i = start, count = converterFactories.size(); i < count; i++) {
        Converter<ResponseBody, ?> converter =
                converterFactories.get(i).responseBodyConverter(type, annotations, this);
        if (converter != null) {
            //noinspection unchecked
            return (Converter<ResponseBody, T>) converter;
        }
    }
	//......省略异常日志
    throw new IllegalArgumentException(builder.toString());
}
```
获取完`converterFactory`之后，由`converterFactory`创建`Converter`。

#### 2.4 创建HttpServiceMethod对象
```java
if (!isKotlinSuspendFunction) {
  return new CallAdapted<>(requestFactory, callFactory, responseConverter, callAdapter);
} else if (continuationWantsResponse) {
  return (HttpServiceMethod<ResponseT, ReturnT>)
      new SuspendForResponse<>( requestFactory, callFactory,responseConverter, (CallAdapter<ResponseT, Call<ResponseT>>) callAdapter);
} else {
  return (HttpServiceMethod<ResponseT, ReturnT>)
      new SuspendForBody<>( requestFactory, callFactory,responseConverter,(CallAdapter<ResponseT, Call<ResponseT>>) callAdapter,continuationBodyNullable);
}
```
最后根据`isKotlinSuspendFunction`和`continuationWantsResponse`创建不同的返回值，`CallAdapted`，`SuspendForResponse`，`SuspendForBody`三个类都是`HttpServiceMethod`的子类。
`HttpServiceMethod`还有一个重要的`invoke()`方法。它将传入的参数`args`配合`requestFactory`等其他参数一起构造了`Call`对象。
```java
final @Nullable ReturnT invoke(Object[] args) {
	Call<ResponseT> call = new OkHttpCall<>(requestFactory, args, callFactory, responseConverter);
	return adapt(call, args);
}
```
`invoke()`方法的调用是在前面的动态代理的代理类中。
```java
//Retrofit.create()中，省略部分代码
new InvocationHandler() {                  
    Object invoke(Object proxy, Method method, @Nullable Object[] args){
        return loadServiceMethod(method).invoke(args)
    }
};
```
`invoke()`方法创建了`Call`对象之后会调用抽象方法`adapt()`,`adapt()`的具体的逻辑由下面三个不同类型的`HttpServiceMethod`实现。

##### 2.4.1 `CallAdapted`
如果没有使用`Kotlin`的协程，最后创建的是`CallAdapted`对象。
```java
static final class CallAdapted<ResponseT, ReturnT> extends HttpServiceMethod<ResponseT, ReturnT> {
	private final CallAdapter<ResponseT, ReturnT> callAdapter;
	//.......省略构造函数
	protected ReturnT adapt(Call<ResponseT> call, Object[] args) {
	  return callAdapter.adapt(call);
	}
}
```
`Retrofit`为我们提供了一个默认的`CallAdapter`，他是由`DefaultCallAdapterFactory`创建，调用了`callAdapter.adapt(call)`之后，会将`call`包装成`ExecutorCallbackCall`对象，后续调用`execute()`方法执行请求。
需要注意的是只有形如`Call`或者`Call<? extends Foo>`的返回类型才会创建`CallAdapter`，对于其他类型比如`Observable`，需要我们自己提供`CallAdapterFactory`创建`CallAdapter`。如果没有提供则会创建`CallAdapter`失败，然后抛出异常。

##### 2.4.2 `SuspendForResponse`
对于`Kotlin`的挂起函数，如果返回类型是`Response`类型，就会创建`SuspendForResponse`对象。
```java
static final class SuspendForResponse<ResponseT> extends HttpServiceMethod<ResponseT, Object> {
	private final CallAdapter<ResponseT, Call<ResponseT>> callAdapter;
	//.......省略构造函数
	protected Object adapt(Call<ResponseT> call, Object[] args) {
	  call = callAdapter.adapt(call);
	  Continuation<Response<ResponseT>> continuation = (Continuation<Response<ResponseT>>) args[args.length - 1];
	  try {
	    return KotlinExtensions.awaitResponse(call, continuation);
	  } catch (Exception e) {
	    return KotlinExtensions.suspendAndThrow(e, continuation);
	  }
	}
}
```

##### 2.4.3 `SuspendForBody`
如果挂起函数返回类型不是`Response`类型，就会创建`SuspendForBody`对象。
```java
static final class SuspendForBody<ResponseT> extends HttpServiceMethod<ResponseT, Object> {
    private final CallAdapter<ResponseT, Call<ResponseT>> callAdapter;
    private final boolean isNullable;
    //.......省略构造函数
    protected Object adapt(Call<ResponseT> call, Object[] args) {
      call = callAdapter.adapt(call);
      Continuation<ResponseT> continuation = (Continuation<ResponseT>) args[args.length - 1];
      try {
        return isNullable? KotlinExtensions.awaitNullable(call, continuation) : KotlinExtensions.await(call,continuation);
      } catch (Exception e) {
        return KotlinExtensions.suspendAndThrow(e, continuation);
      }
    }
}
```
`SuspendForResponse`和`SuspendForBody`都是对`Kotlin`协程的支持，基本逻辑也是一致的，首先通过`callAdapter.adapt(call)`包装`Call`，然后调用`KotlinExtensions`中的方法并返回结果,由于`java`调用`kotlin`的`suspend`方法需要传入`Continuation`对象，所以会先从参数中获取出来。
```java
@JvmName("awaitNullable")
suspend fun <T : Any> Call<T?>.await(): T? {
  return suspendCancellableCoroutine { continuation ->
    continuation.invokeOnCancellation {
      cancel()
    }
    enqueue(object : Callback<T?> {
      override fun onResponse(call: Call<T?>, response: Response<T?>) {
        if (response.isSuccessful) {
          continuation.resume(response.body())
        } else {
          continuation.resumeWithException(HttpException(response))
        }
      }
      override fun onFailure(call: Call<T?>, t: Throwable) {
        continuation.resumeWithException(t)
      }
    })
  }
}
```
`awaitResponse()`和`await()`基本一致，这里以`await()`为例，通过`suspendCancellableCoroutine`，我们可以将回调转化为挂起协程。与之相似还有`suspendCoroutine`，不同的是`suspendCancellableCoroutine`获取到的协程是一个`CancellableContinuation`对象，它支持取消，在`Retrofit`中我们需要再协程取消的时候取消`okhttp`的请求，即在`invokeOnCancellation` 中调用`cancel()`方法
`enqueue`方法执行的是`OkHttpCall`的`enqueue`，最后会通过`OkHttp`发起网络请求。成功之后通过`resume`恢复挂起并返回数据，如果失败则恢复挂起抛出异常。

```java
作者：晚来天欲雪_
链接：https://juejin.cn/post/7110814534993985549
来源：稀土掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```