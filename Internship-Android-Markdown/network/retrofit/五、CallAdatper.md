## CallAdapter

通过前面的分析，我们知道在`OkHttpCall`中完成了请求，最后我们获取到的结果是`Call`类型，但是在实际使用中我们或许会和`RxJava`配合使用，需要的返回结果为`Observable`,这里使用了适配器模式，，只要指定对应的适配器就可以将返回的`Call`类型适配成我们需要的任意类型。

### 1. `CallAdapter`
```java
public interface CallAdapter<R, T> {
  Type responseType();
  T adapt(Call<R> call);
}
```
- `responseType()`返回泛型对应的真实类型。
- `adapt()` 将Call类型适配成T类型。

### 2. `CallAdapter.Factory`
`CallAdapter`由`CallAdapterFactory`创建，他是`CallAdapter`的一个内部类。
```java
abstract class Factory {
	public abstract @Nullable CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit);
	protected static Type getParameterUpperBound(int index, ParameterizedType type) {
	  return Utils.getParameterUpperBound(index, type);
	}
	protected static Class<?> getRawType(Type type) {
	  return Utils.getRawType(type);
	}
}
```
- `get()`方法会判断`returnType`，`annotations`是否和对应的`CallAdapter`匹配，如果匹配就返回`adapter`，否则返回`null`。
- `getParameterUpperBound()` 获取泛型第`index`个参数的上界，比如`index=1`并且`Map<String,? extends Runnable>`就返回`Runnable`。
- `getRawType()` 获取返回值的原始类型，例如`List<? extends Runnable>`就返回`List`。

`get()`方法一般都是3个作用:
1. 首先根据参数判断自己能否创建对应的`CallAdapter`，如果不能就返回`null`，由后面的`Factory`处理
2. 然后对返回类型`returnType`进行校验
3. 最后创建`CallAdapter`并返回。

`Retrofit`给我们提供了默认的`CallAdapterFactory`，在构建`Retrofit`实例的时候会初始化。
```java
callAdapterFactories.addAll(platform.defaultCallAdapterFactories(callbackExecutor));
```
根据不同的平台类型，提供默认的`CallAdapterFactory`也不一样。
```java
List<? extends CallAdapter.Factory> defaultCallAdapterFactories( Executor callbackExecutor) {
    DefaultCallAdapterFactory executorFactory = new DefaultCallAdapterFactory(callbackExecutor);
    return hasJava8Types
        ? asList(CompletableFutureCallAdapterFactory.INSTANCE, executorFactory)
        : singletonList(executorFactory);
}
```

#### 1.1 `DefaultCallAdapterFactory`
```java
final class DefaultCallAdapterFactory extends CallAdapter.Factory {
	@Override
    public @Nullable CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
	    if (getRawType(returnType) != Call.class) {
	      return null;
	    }
	    if (!(returnType instanceof ParameterizedType)) {
	      //抛出异常
	    }
	    return new CallAdapter
	}
}
```
`DefaultCallAdapterFactory`只能处理`Call`类型的返回结果，说明它处理的比较简单，只是对`Call`做了一下封装，然后判断返回类型是否为参数化类型，最后创建`CallAdapter`返回。

##### 1.1.1 `CallAdapter`
```java
return new CallAdapter<Object, Call<?>>() {
  public Type responseType() {
    return responseType;
  }
  public Call<Object> adapt(Call<Object> call) {
    return executor == null ? call : new ExecutorCallbackCall<>(executor, call);
  }
};
```
在`executor`不为空的情况下，会将`Call`适配成`ExecutorCallbackCall`类型，在`ExecutorCallbackCall`中主要是借助`callbackExecutor`完成了线程切换，这是因为`OkHttp`响应结果的线程没有在`UI`线程，对于`Android`而言，需要切换到`UI`线程才能操作`UI`，这里`CallAdapter`自动完成了切换。

### 2. `RxJava2CallAdapterFactory`
`RxJava2CallAdapterFactory`不在`Retrofit`核心包中，需要额外引入依赖并调用`addCallAdapterFactory()`注入。
```java
public final class RxJava2CallAdapterFactory extends CallAdapter.Factory {
  public @Nullable CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
		//1 判断能否适配当前类型
		Class<?> rawType = getRawType(returnType);
		//2 校验返回结果类型
		boolean isResult = false;
		boolean isBody = false;
		Type responseType;
		//3 返回CallAdapter
		return new RxJava2CallAdapter(responseType, scheduler, isAsync, isResult, isBody, isFlowable, isSingle, isMaybe, false);
  }
}
```

#### 2.1 判断能否适配当前类型
```java
Class<?> rawType = getRawType(returnType);
if (rawType == Completable.class) {
  return new RxJava2CallAdapter(Void.class, scheduler, isAsync, false, true, false, false, false, true);
}
boolean isFlowable = rawType == Flowable.class;
boolean isSingle = rawType == Single.class;
boolean isMaybe = rawType == Maybe.class;
if (rawType != Observable.class && !isFlowable && !isSingle && !isMaybe) {
  return null;
}
```
`Completable`没有泛型类型，所以创建`CallAdapter`时候`responseType`参数类型传递为`Void.class`。
`RxJava2CallAdapterFactory`只支持`Completable`，`Observable`，`Flowable`，`Single`，`Maybe`几种类型，其余的类型无法处理，直接返回`null`。

#### 2.2 返回结果校验
```java
if (!(returnType instanceof ParameterizedType)) {
	throw new IllegalStateException();
}
Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
Class<?> rawObservableType = getRawType(observableType);
if (rawObservableType == Response.class) {
	if (!(observableType instanceof ParameterizedType)) {
		throw new IllegalStateException();
	}
	responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
} else if (rawObservableType == Result.class) {
	if (!(observableType instanceof ParameterizedType)) {
		throw new IllegalStateException();
	}
  responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
  isResult = true;
} else {
  responseType = observableType;
  isBody = true;
}
```
1. 判断返回类型是否为参数化类型。
2. 获取实际类型，如果实际类型是`Response`或者`Result`，则需要`observableType`也是参数化类型。

#### 2.3 `RxJava2CallAdapter`
由于支持的类型过多，这里省略部分。
```java
final class RxJava2CallAdapter<R> implements CallAdapter<R, Object> {
	public Object adapt(Call<R> call) {
	    Observable<Response<R>> responseObservable =
	        isAsync ? new CallEnqueueObservable<>(call) : new CallExecuteObservable<>(call);
	    Observable<?> observable;
	    if (isResult) {
	      observable = new ResultObservable<>(responseObservable);
	    } else if (isBody) {
	      observable = new BodyObservable<>(responseObservable);
	    } else {
	      observable = responseObservable;
	    }
		//省略部分处理
	    return RxJavaPlugins.onAssembly(observable);
  }
}
```
默认情况下，我们使用的是`RxJava2CallAdapterFactory#create()`创建的工厂，这种情况下`isAsync`为`false`，所以首先生成的是`CallExecuteObservable`对象。

`CallExecuteObservable`是`Observable`的子类，它完成了对`Call`的转换。
```java
final class CallExecuteObservable<T> extends Observable<Response<T>> {
	protected void subscribeActual(Observer<? super Response<T>> observer) {
		//只保留了主流程
		try {
		  Response<T> response = call.execute();
		  observer.onNext(response);
		  observer.onComplete();
		} catch (Throwable t) {
		  //异常处理
		}
	}
}
```
熟悉`RxJava2`的朋友都比较熟悉这里，通过`observer`将`call`执行的结果发射出去，如果执行顺利就发射`onComplete`事件,否则发射`onError`事件。

```java
作者：晚来天欲雪_
链接：https://juejin.cn/post/7111688277702230023
来源：稀土掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```