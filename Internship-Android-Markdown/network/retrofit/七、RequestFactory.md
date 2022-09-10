## 七、`RequestFactory`

在`ServiceMethod`类中，我们通过`RequestFactory.parseAnnotations()`获取`RequestFactory`对象
```java
static <T> ServiceMethod<T> parseAnnotations(Retrofit retrofit, Method method) {
	RequestFactory requestFactory = RequestFactory.parseAnnotations(retrofit, method);
}
```

### 1. `RequestFactory`
`RequestFactory`是生产`Request`的地方，他使用构造者模式创建
```java
final class RequestFactory {
    static RequestFactory parseAnnotations(Retrofit retrofit, Method method) {
        return new Builder(retrofit, method).build();
    }
    Builder(Retrofit retrofit, Method method) {
      this.retrofit = retrofit;
      this.method = method;
      this.methodAnnotations = method.getAnnotations();
      this.parameterTypes = method.getGenericParameterTypes();
      this.parameterAnnotationsArray = method.getParameterAnnotations();
    }
}
```
- `getAnnotations()` 获取方法上的注解
- `getGenericParameterTypes()` 获取方法的参数类型
- `getParameterAnnotations()` 获取方法的参数上的注解

**以下面这个方法为例**
```java
@POST("xxx")
fun tesAnnotation(@Query("age") age: Int):Call<Optional<String>>
```
测试输出结果为
```java
if(method.getName().equals("tesAnnotation")){
    System.out.println(Arrays.toString(method.getAnnotations()));               -------------->     [@retrofit2.http.POST(value="xxx")]
    System.out.println(Arrays.toString(method.getGenericParameterTypes()));     -------------->     [int]
    System.out.println(Arrays.toString(method.getParameterAnnotations()[0]));   -------------->     [@retrofit2.http.Query(encoded=false, value="age")]
}
```
需要注意的是`getParameterAnnotations()`返回的结果是一个二维数组，因为例子中只有一个参数，所以直接取第一个元素。

### 2. 创建RequestFactory
```java
RequestFactory build() {
    //1 解析方法上的注解
    for (Annotation annotation : methodAnnotations) {
        parseMethodAnnotation(annotation);
    } 
    //2 检查方法上的注解(省略部分代码)
    throw methodError("")
    //3 解析参数
    int parameterCount = parameterAnnotationsArray.length;
    parameterHandlers = new ParameterHandler<?>[parameterCount];
    for (int p = 0, lastParameter = parameterCount - 1; p < parameterCount; p++) {
    parameterHandlers[p] =
        parseParameter(p, parameterTypes[p], parameterAnnotationsArray[p], p == lastParameter);
    }
    //4 检查参数上的注解(省略部分代码)
    throw methodError("")
    //5 创建RequestFactory
    return new RequestFactory(this);
}
```
`build()`方法主要做了下面五件事
1. 解析方法上的注解
2. 检查方法上的注解
3. 解析参数获取参数上的注解
4. 检查参数上的注解
5. 创建`RequestFactory`

#### 2.1 解析方法上的注解
使用过`Retrofit`的朋友都知道，在`APIService`方法上的注解不仅可以是`HTTP`请求方式的注解，还能是`@Headers`,`@FormUrlEncoded`等，`parseMethodAnnotation()`就是专门来处理这些注解的。
```java
private void parseMethodAnnotation(Annotation annotation) {
    if (annotation instanceof GET) {
        parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
        //省略POST PUT等注解
    } else if (annotation instanceof retrofit2.http.Headers) {
        String[] headersToParse = ((retrofit2.http.Headers) annotation).value();
        if (headersToParse.length == 0) {
          throw methodError(method, "@Headers annotation is empty.");
        }
        headers = parseHeaders(headersToParse);
    } else if (annotation instanceof Multipart) {
        if (isFormEncoded) {
            throw methodError(method, "Only one encoding annotation is allowed.");
        }
        isMultipart = true;
    } else if (annotation instanceof FormUrlEncoded) {
        if (isMultipart) {
            throw methodError(method, "Only one encoding annotation is allowed.");
        }
        isFormEncoded = true;
    }
}
```
`parseMethodAnnotation()`主要做了注解分类，对于像`GET`，`POST`这些`HTTP`请求方式的注解都交由`parseHttpMethodAndPath()`处理，`@Headers`由`parseHeaders()`处理,最后是`@Multipart`和`@FormUrlEncoded`两个注解，我们可以看到，他们2个最多只能2选1.，否则会抛出异常。

#### 2.2 `parseHttpMethodAndPath`
```java
private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
    if (this.httpMethod != null) {
        throw methodError(method, "Only one HTTP method is allowed. Found: %s and %s.", this.httpMethod, httpMethod);
    }
    this.httpMethod = httpMethod;
    this.hasBody = hasBody;
    if (value.isEmpty()) {return;}
    int question = value.indexOf('?');
    if (question != -1 && question < value.length() - 1) {
        String queryParams = value.substring(question + 1);
        Matcher queryParamMatcher = PARAM_URL_REGEX.matcher(queryParams);
        if (queryParamMatcher.find()) {
          throw methodError( method,"URL query string \"%s\" must not have replace block. "+ "For dynamic query parameters use @Query.",queryParams);
        }
    }
    this.relativeUrl = value;
    this.relativeUrlParamNames = parsePathParameters(value);
}
```

1. 首先检查当前方法`HTTP`请求方式的注解是否唯一，因为我们一次只能以一种请求方式访问服务器。
2. 然后检查注解的参数，一般是`URL`地址，我们知道，`?`是用来分隔实际的`URL`和`参数`,如果`URL`中存在`?`，则动态查询参数不能搭配`@Path`使用，这时候应该使用`@Query`。
3. 如果有动态查询参数的话，通过`parsePathParameters()`查找后放在`relativeUrlParamNames`中保存，查询过程与上一步检查参数一致，都是使用`PARAM_URL_REGEX`正则。`relativeUrlParamNames`是`Set`类型，所以参数会被去重。

#### 2.3 `parseHeaders`
`parseHeaders()`方法主要是对传入的`Header`字符串检测并处理成`Headers`对象。
```java
private Headers parseHeaders(String[] headers) {
    Headers.Builder builder = new Headers.Builder();
    for (String header : headers) {
        int colon = header.indexOf(':');
        if (colon == -1 || colon == 0 || colon == header.length() - 1) {
            throw methodError(method, "@Headers value must be in the form \"Name: Value\". Found: \"%s\"", header);
        }
        String headerName = header.substring(0, colon);
        String headerValue = header.substring(colon + 1).trim();
        if ("Content-Type".equalsIgnoreCase(headerName)) {
            try {
                contentType = MediaType.get(headerValue);
            } catch (IllegalArgumentException e) {
                throw methodError(method, e, "Malformed content type: %s", headerValue);
            }
        } else {
            builder.add(headerName, headerValue);
        }
    }
    return builder.build();
}
```
首先会检查请求头，请求头的规范是`Name: Value`,如果不存在字符`':'`,或者以他开始或者结尾 都不符合`header`标准，直接抛出异常。对于`Content-Type`类型的`Header`解析成功后存放在了`contentType`中，其余的都用来构建`Headers`对象。

### 3. 方法参数注解检查
```java
if (httpMethod == null) {
    throw methodError(method, "HTTP method annotation is required (e.g., @GET, @POST, etc.).");
}
if (!hasBody) {
    if (isMultipart) {
      throw methodError(method,"Multipart can only be specified on HTTP methods with request body (e.g., @POST).");
    }
    if (isFormEncoded) {
        throw methodError(method, "FormUrlEncoded can only be specified on HTTP methods with "+ "request body (e.g., @POST).");
    }
}
```

`APIService`方法必须要有一个`HTTP`方法注解。
如果使用了`@Multipart`或者`@FormUrlEncoded`，则必须使用带有请求体的`HTTP`方法注解，例如`@Patch`,`@Post`,`@Put`。

### 4. 参数解析
```java
int parameterCount = parameterAnnotationsArray.length;
parameterHandlers = new ParameterHandler<?>[parameterCount];
for (int p = 0, lastParameter = parameterCount - 1; p < parameterCount; p++) {
    parameterHandlers[p] =  parseParameter(p, parameterTypes[p], parameterAnnotationsArray[p], p == lastParameter);
}
```
`parameterAnnotationsArray`是一个二维数组`Annotation[][]`,存放的是`method.getParameterAnnotations()`获取的结果。第一维是参数的注解(可能为`null`)，这里会给每一个注解创建一个`ParameterHandler`

#### 4.1 `parseParameter`
```java
private @Nullable ParameterHandler<?> parseParameter(int p, Type parameterType, @Nullable Annotation[] annotations, boolean allowContinuation) {
    ParameterHandler<?> result = null;
    if (annotations != null) {
        for (Annotation annotation : annotations) {
            ParameterHandler<?> annotationAction = parseParameterAnnotation(p, parameterType, annotations, annotation);
            if (annotationAction == null) {
               continue;
            }
            if (result != null) {
               throw parameterError(method, p, "Multiple Retrofit annotations found, only one allowed.");
            }
            result = annotationAction;
        }
   }
   if (result == null) {
      if (allowContinuation) {
         try {
            if (Utils.getRawType(parameterType) == Continuation.class) {
               isKotlinSuspendFunction = true;
               return null;
            }
         } catch (NoClassDefFoundError ignored) {
         }
      }
      throw parameterError(method, p, "No Retrofit annotation found.");
   }
   return result;
}
```
如果参数有注解，则由`parseParameterAnnotation()`方法解析并且转化为`ParameterHandler`对象，同一个参数最多只能有一个`Retrofit`注解
如果没有`Retrofit`注解，先判断当前参数是不是`Continuation`对象，因为`kotlin`挂起函数在转化为`java`方法的时候，会在方法最后添加一个`Continuation`参数，这种情况下可以没有`Retrofit`注解，其余情况必须要有一个。

`parseParameterAnnotation()`方法主要是对`@Url`，`@Path`，`@Query`等`Retrofit`注解做处理，以最常用的`@Query`为例
```java
private ParameterHandler<?> parseParameterAnnotation(int p, Type type, Annotation[] annotations, Annotation annotation) {
    validateResolvableType(p, type);
    Query query = (Query) annotation;
    String name = query.value();
    boolean encoded = query.encoded();
    Class<?> rawParameterType = Utils.getRawType(type);
    gotQuery = true;
    if (Iterable.class.isAssignableFrom(rawParameterType)) {
      if (!(type instanceof ParameterizedType)) {
        throw parameterError(
            method,
            p,
            rawParameterType.getSimpleName()
                + " must include generic type (e.g., "
                + rawParameterType.getSimpleName()
                + "<String>)");
      }
      ParameterizedType parameterizedType = (ParameterizedType) type;
      Type iterableType = Utils.getParameterUpperBound(0, parameterizedType);
      Converter<?, String> converter = retrofit.stringConverter(iterableType, annotations);
      return new ParameterHandler.Query<>(name, converter, encoded).iterable();
    } else if (rawParameterType.isArray()) {
      Class<?> arrayComponentType = boxIfPrimitive(rawParameterType.getComponentType());
      Converter<?, String> converter =
          retrofit.stringConverter(arrayComponentType, annotations);
      return new ParameterHandler.Query<>(name, converter, encoded).array();
    } else {
      Converter<?, String> converter = retrofit.stringConverter(type, annotations);
      return new ParameterHandler.Query<>(name, converter, encoded);
    }
}
```
首先对`type`进行校验，其中不能包含泛型和通配符,然后需要创建一个`ParameterHandler.Query()`对象返回，他需要用到`converter`对象，`converter`由`retrofit.stringConverter()`提供，`stringConverter()`方法第一个参数是个`Type`类型，对于`Query`而言，如果参数`type`是个`Iterable`，那么需要获取真实迭代的对象作为参数，如果参数`type`是个数组，则需要获取数组的类型，对于基本数据类型需要装箱成对应的引用类型。其他类型则无需转换，直接传递。

#### 4.2 `ParameterHandler`
前面使用的`ParameterHandler.Query`是`ParameterHandler`的实现类。
```java
abstract class ParameterHandler<T> {
  abstract void apply(RequestBuilder builder, @Nullable T value) throws IOException;
  final ParameterHandler<Iterable<T>> iterable() {}
  final ParameterHandler<Object> array() {}
}
```
`apply()`是个抽象方法需要子类实现，`ParameterHandler.Query`实现如下:
```java
void apply(RequestBuilder builder, @Nullable T value) throws IOException {
  if (value == null) return; 
  String queryValue = valueConverter.convert(value);
  if (queryValue == null) return;
  builder.addQueryParam(name, queryValue, encoded);
}
```
可以看到`apply`实际是对请求参数在做处理，将对应的值注入到`RequestBuilder`中。而`iterable()`和`array()`则是对迭代器和数组类型的参数的适配。

### 5. 创建`Request`
`RequestFactory`实例创建之后，便可以调用其`create()`方法进行创建`Request`对象。
```java
okhttp3.Request create(Object[] args) throws IOException {
    ParameterHandler<Object>[] handlers = (ParameterHandler<Object>[]) parameterHandlers;
    int argumentCount = args.length;
    if (argumentCount != handlers.length) {
		//长度不一致则抛出异常
	}
    RequestBuilder requestBuilder =new RequestBuilder(//省略参数...);
	//kotlin挂起函数会在方法最后增加一个Continuation参数，这里需要将它排除掉
    if (isKotlinSuspendFunction) {
      argumentCount--;
    }

    List<Object> argumentList = new ArrayList<>(argumentCount);
    for (int p = 0; p < argumentCount; p++) {
      argumentList.add(args[p]);
      handlers[p].apply(requestBuilder, args[p]);
    }

    return requestBuilder.get().tag(Invocation.class, new Invocation(method, argumentList)).build();
  }
```
`Object[] args`是我们调用`ApiService`方法传进来的参数，他应该和通过注解解析到的参数长度一致，否则就会抛出异常。然后创建`RequestBuilder`对象，如果当前方法是`kotlin`挂起函数，则需要排除最后一个`Continuation`参数。然后遍历`handlers`调用`apply()`方法将参数设置到`requestBuilder`中。最后调用`requestBuilder.build()`方法创建`Request`对象。

```java
作者：晚来天欲雪_
链接：https://juejin.cn/post/7112099022231322632
来源：稀土掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```