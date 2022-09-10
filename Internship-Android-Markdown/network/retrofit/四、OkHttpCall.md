## 四、OkHttpCall

通过前面分析，我们知道在`HttpServiceMethod#invoke()`方法中，会创建`Call`对象(或者说是`okHttp`对应的`OkHttpCall`)。
```java
final @Nullable ReturnT invoke(Object[] args) {
	Call<ResponseT> call = new OkHttpCall<>(requestFactory, args, callFactory, responseConverter);
	return adapt(call, args);
}
```
`OkHttpCall`是`retrofit2.Call`的实现类，`retrofit2.Call`定义了以下方法
```java
public interface Call<T> extends Cloneable {
  Response<T> execute() throws IOException;
  void enqueue(Callback<T> callback);
  boolean isExecuted();
  void cancel();
  boolean isCanceled();
  Call<T> clone();
  Request request();
  Timeout timeout();
}
```
熟悉`OkHttp`的朋友应该知道`retrofit2.Call`与`okhttp3.Call`基本相似。

### 1. `enqueue`
`OkHttpCall`提供了**同步**和**异步**两种请求方式，之前简单分析过同步的方式，这里就以**异步**为例。
```java
public void enqueue(final Callback<T> callback) {
	//1 创建okhttp3.Call
	okhttp3.Call call = rawCall = createRawCall();
	//2 执行请求
	call.enqueue(new okhttp3.Callback() {
		@Override
		public void onResponse(okhttp3.Call call, okhttp3.Response rawResponse) {
			//处理响应结果
			Response<T> response = parseResponse(rawResponse);
		}
    });
}
```
从这里可以看出真正的请求其实是由`okhttp`完成的，`Retrofit`只是对其进行了封装，简化了我们使用的流程。
`enqueue()`方法比较长，但是只是主要只是做了3件事。

1. 创建okhttp3.Call对象
2. 执行请求，获取结果
3. 处理响应结果，回调给外部

### 2. 创建okhttp3.Call
```java
private okhttp3.Call createRawCall() throws IOException {
	okhttp3.Call call = callFactory.newCall(requestFactory.create(args));
	if (call == null) {
		throw new NullPointerException("Call.Factory returned null.");
	}
	return call;
}
```
`okhttp3.Call`对象是由`callFactory`创建的，`callFactory`是我们初始化`Retrofit`时传入的参数，一般情况下都是`OkHttpClient`对象。再单独使用`OkHttp`的时候，我们创建一个`Call`对象的方式是下面这样的
```java
Request request = new Request.Builder().build();
client.newCall(request)
```
通过构造者模式创建`Request`对象，但是在`Retrofit`中，则是由`requestFactory`创建，简化了我们的使用方式。

### 3. 执行请求
获取到`okhttp3.Call`对象之后就可以调用`enqueue(callback)`发起异步请求
```java
call.enqueue(new okhttp3.Callback() {
    @Override
    public void onResponse(okhttp3.Call call, okhttp3.Response rawResponse) {
        Response<T> response;
        try {
            response = parseResponse(rawResponse);
        } catch (Throwable e) {
            throwIfFatal(e);
            callFailure(e);
            return;
        }
    }
    public void onFailure(okhttp3.Call call, IOException e) {
        callFailure(e);
    }
})
```
使用方式和`OkHttp`完全一致。

### 4. 处理响应结果
通过上一步执行了请求之后，就能获取到结果了，如果失败回调`onFailure()`方法，如果成功则还需要对结果进行解析
```java
Response<T> parseResponse(okhttp3.Response rawResponse) throws IOException {
    ResponseBody rawBody = rawResponse.body();
    rawResponse = rawResponse
            .newBuilder()
            .body(new NoContentResponseBody(rawBody.contentType(), rawBody.contentLength()))
            .build();
    int code = rawResponse.code();
    if (code < 200 || code >= 300) {
        try {
            ResponseBody bufferedBody = Utils.buffer(rawBody);
            return Response.error(bufferedBody, rawResponse);
        } finally {
            rawBody.close();
        }
    }
    if (code == 204 || code == 205) {
        rawBody.close();
        return Response.success(null, rawResponse);
    }
    ExceptionCatchingResponseBody catchingBody = new ExceptionCatchingResponseBody(rawBody);
    try {
        T body = responseConverter.convert(catchingBody);
        return Response.success(body, rawResponse);
    } catch (RuntimeException e) {
        catchingBody.throwIfCaught();
        throw e;
    }
}
```

1. 首先复制一个`Response`，然后删除他的主体数据，以便后面使用无主体数据的`Response`传递响应结果。
2. 然后判断`Http`响应码，我们知道`2xx`的响应码代表成功，但是`204`和`205`这两个比较特殊，他们的响应内容为空，所以只是返回了`rawResponse`，这意味着我们在业务层通过`response.body()`获取到的结果可能是`null`。
3. 最后对响应体`rawBody`做个包装，包装成为`ExceptionCatchingResponseBody`对象，然后交给`responseConverter`转换为我们需要的实体类型

这里首先将`Response`的`body`移除是因为`ResponseBody`里面的`source`是具有状态的对象。

```java
作者：晚来天欲雪_
链接：https://juejin.cn/post/7111289318282362910
来源：稀土掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```