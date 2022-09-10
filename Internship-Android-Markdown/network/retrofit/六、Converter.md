## 六、`Converter`

前面通过`OkHttpCall`我们获取到了接口响应，但是这时候拿到的是`ResponseBody`，而实际使用过程中我们需要的往往是一个实体类，`Retrofit`通过`Converter`完成了这一转换。
```java
T body = responseConverter.convert(catchingBody);
```
`Converter`由`Converter.Factory`创建

### 1. `Converter.Factory`
```java
abstract class Factory {
	public @Nullable Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
	  return null;
	}
	public @Nullable Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,Annotation[] methodAnnotations, Retrofit retrofit) {
	  return null;
	}
	public @Nullable Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
	  return null;
	}
	protected static Type getParameterUpperBound(int index, ParameterizedType type) {
	  return Utils.getParameterUpperBound(index, type);
	}
	protected static Class<?> getRawType(Type type) {
	  return Utils.getRawType(type);
	}
}
```
从代码结构可以看出`Converter`作为一个数据转换器，它不仅能转换返回的数据，还能转换我们的请求体
1. `responseBodyConverter()` 把响应数据`ResponseBody`转换为我们需要的结构。
2. `requestBodyConverter()` 把请求的数据转换为`RequestBody`。
3. `stringConverter()` 把请求的类型转换为`String`。
4. `getParameterUpperBound()` 获取泛型第`index`个参数的上界，比如`index=1`并且`Map<String,? extends Runnable>`就返回`Runnable`。
`getRawType()` 获取返回值的原始类型，例如`List<? extends Runnable>`就返回`List`。

`Converter.Factory`只是一个抽象类，实际工作由他的实现类完成，`Retrofit`为我们默认提供了`BuiltInConverters`和`OptionalConverterFactory`。
并且在创建`Retrofit`的时候也默认添加了这两个工厂。
```java
public Retrofit build() {
	converterFactories.add(new BuiltInConverters());
	converterFactories.addAll(this.converterFactories);
	converterFactories.addAll(platform.defaultConverterFactories());
}
```
由平台提供的`defaultConverterFactories`如下
```java
List<? extends Converter.Factory> defaultConverterFactories() {
	return hasJava8Types ? singletonList(OptionalConverterFactory.INSTANCE) : emptyList();
}
```
只有在`Java8`以上才会提供`OptionalConverterFactory`。

#### 1.1 BuiltInConverters
`responseBodyConverter()`实现比较简单，只能处理`ResponseBody`，`Void`，`Unit(kotlin)`三种类型。
```java
public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
	if (type == ResponseBody.class) {
	  return Utils.isAnnotationPresent(annotations, Streaming.class)
	      ? StreamingResponseBodyConverter.INSTANCE
	      : BufferingResponseBodyConverter.INSTANCE;
	}
	if (type == Void.class) {
	  return VoidResponseBodyConverter.INSTANCE;
	}
	if (checkForKotlinUnit) {
	  try {
	    if (type == Unit.class) {
	      return UnitResponseBodyConverter.INSTANCE;
	    }
	  } catch (NoClassDefFoundError ignored) {
	    checkForKotlinUnit = false;
	  }
	}
	return null;
}
```
首先会根据我们声明的不同的返回类型创建不同的`Converter`，对于`ResponseBody`，一般情况下是字符流形式返回，这种情况下会返回`BufferingResponseBodyConverter`，当我们下载文件时，我们需要以二进制流形式返回，这种情况下，我们会在方法上声明`@Streaming`注解，当`Retrofit`读取到此注解的时候就会为我们返回`StreamingResponseBodyConverter`。
```java
//BufferingResponseBodyConverter
public ResponseBody convert(ResponseBody value) throws IOException {
	return Utils.buffer(value);
}
//StreamingResponseBodyConverter
public RequestBody convert(RequestBody value) {
	return value;
}
```
`Void`和`Unit(kotlin)`的处理都比较简单，他们对应的`Converter`主要是为了调用`ResponseBody#close()`方法，安全关闭。
```java
static final class UnitResponseBodyConverter implements Converter<ResponseBody, Unit> {
    static final UnitResponseBodyConverter INSTANCE = new UnitResponseBodyConverter();
    public Unit convert(ResponseBody value) {
      value.close();
      return Unit.INSTANCE;
    }
}
```
`requestBodyConverter()`方法中只是判断了请求类型是不是`RequestBody`,对于`RequestBody`的请求类型会原样处理。
```java
public Converter<?, RequestBody> requestBodyConverter(Type type Annotation[] pa,Annotation[] ma,Retrofit retrofit) {
	if (RequestBody.class.isAssignableFrom(Utils.getRawType(type))) {
	  return RequestBodyConverter.INSTANCE;
	}
	return null;
}
```

#### 1.2 OptionalConverterFactory
`Optional`是在`Java8`中引入解决`NullPointerExceptions`问题的类。所以`OptionalConverterFactory`只有在`Java8`以上或者`Android Api24`以上才会引入。
```java
final class OptionalConverterFactory extends Converter.Factory {
  static final Converter.Factory INSTANCE = new OptionalConverterFactory();
  public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
    if (getRawType(type) != Optional.class)return null;
    Type innerType = getParameterUpperBound(0, (ParameterizedType) type);
    Converter<ResponseBody, Object> delegate = retrofit.responseBodyConverter(innerType, annotations);
    return new OptionalConverter<>(delegate);
  }
}
```
`OptionalConverterFactory`只是重写了`responseBodyConverter()`方法。

首先判断类型是否为`Optional`，对于非法类型直接返回`null`,然后获取`Optional`所包装的真实类型`innerType`，通过这个`innerType`在`retrofit`中查找能处理它的`Converter`，找到之后通过`Optional`将其包装为`OptionalConverter`。
```java
static final class OptionalConverter<T> implements Converter<ResponseBody, Optional<T>> {
    public Optional<T> convert(ResponseBody value) throws IOException {
      return Optional.ofNullable(delegate.convert(value));
    }
}
```
可以看出`OptionalConverterFactory`仅仅只做了下拆除`Optional`包装，对于真正的类型转换还需要其他的`Converter`，也就是说到目前未知我们只能处理`ResponseBody`，`Void`，`Unit(kotlin)`三种类型，但是实际项目中我们经常需要直接转换为业务实体类，所幸`Retrofit`支持自定义`Converter`并且已经为我们实现了很多`ResponseBody`转实体类的`Converter`,以配个`Gson`使用的`GsonConverterFactory`为例。

#### 1.3 `GsonConverterFactory`
`GsonConverterFactory`不是`Retrofit`核心库提供的类，所以首先需要额外引入依赖。
```xml
com.squareup.retrofit2:converter-gson:2.9.0
```
然后注册进`Retrofit`
```java
addConverterFactory(GsonConverterFactory.create(gson))
```
`GsonConverterFactory`实现了`responseBodyConverter()`和`requestBodyConverter()`方法。
```java
public final class GsonConverterFactory extends Converter.Factory {
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new GsonResponseBodyConverter<>(gson, adapter);
	}
	public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] pa,Annotation[] ma,Retrofit retrofit){
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new GsonRequestBodyConverter<>(gson, adapter);
	}
}
```
与`BuiltInConverters`和`OptionalConverterFactory`不同的是，它没有判断类型`type`，来者不拒，全部交给`Gson`处理。

##### 1.3.1 `GsonResponseBodyConverter`
```java
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
	//省略部分代码
	public T convert(ResponseBody value) throws IOException {
		JsonReader jsonReader = gson.newJsonReader(value.charStream());
		return = adapter.read(jsonReader);
	}
}
```
`convert`内部通过`gson`的`newJsonReader()`方法获取`JsonReader`对象，然后通过`TypeAdapter`获取对应的类型。

##### 1.3.2 `GsonRequestBodyConverter`
```java
final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
	//省略部分代码
  public RequestBody convert(T value) throws IOException {
    Buffer buffer = new Buffer();
    Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
    JsonWriter jsonWriter = gson.newJsonWriter(writer);
    adapter.write(jsonWriter, value);
    jsonWriter.close();
    return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
  }
}
```
通过`Gson`将请求参数写入`Buffer`，然后通过`RequestBody.create()`创建`RequestBody`对象并返回。

```java
作者：晚来天欲雪_
链接：https://juejin.cn/post/7112034017905967112
来源：稀土掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```