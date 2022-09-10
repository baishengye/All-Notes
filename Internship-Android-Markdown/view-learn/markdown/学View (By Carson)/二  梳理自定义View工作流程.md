本文章做学习记录之用，原文章信息如下:

>作者：Carson带你学安卓
>链接：https://www.jianshu.com/p/e79a55c141d6
>来源：简书

## 二  梳理自定义View工作流程

### 1. 储备知识

#### 1.1 ViewRoot
- 定义
连接器，对应于ViewRootImpl类

- 作用
    1. 连接WindowManager 和 DecorView完成View的三大流程： 
    2. measure、layout、draw

- 特别注意
```java
// 在主线程中，Activity对象被创建后：
// 1. 自动将DecorView添加到Window中 & 创建ViewRootImpll对象
root = new ViewRootImpl(view.getContent(),display);

// 3. 将ViewRootImpll对象与DecorView建立关联
root.setView(view,wparams,panelParentView)
```


#### 1.2 DecorView
- 定义：顶层View
>即 Android 视图树的根节点；同时也是 FrameLayout 的子类

- 作用：显示 & 加载布局
> View层的事件都先经过DecorView，再传递到View

- 特别说明
内含1个竖直方向的LinearLayout，分为2部分：上 = 标题栏（titlebar）、下 = 内容栏（content）
![](https://upload-images.jianshu.io/upload_images/944365-4923b6377b032256.png?imageMogr2/auto-orient/strip|imageView2/2/w/280/format/webp)
$$示意图$$
> 在Activity中通过 setContentView（）所设置的布局文件其实是被加到内容栏之中的，成为其唯一子View = id为content的FrameLayout中
```java
// 在代码中可通过content得到对应加载的布局

// 1. 得到content
ViewGroup content = (ViewGroup)findViewById(android.R.id.content);
// 2. 得到设置的View
ViewGroup rootView = (ViewGroup) content.getChildAt(0);
```

#### 1.3 Window、Activity、DecorView 与 ViewRoot的关系
- 简介
![](https://upload-images.jianshu.io/upload_images/944365-b9c41aa994e8ddf4.png?imageMogr2/auto-orient/strip|imageView2/2/w/970/format/webp)
$$示意图$$

- 之间的关系
![](https://upload-images.jianshu.io/upload_images/944365-34992eb46bdf93e7.png?imageMogr2/auto-orient/strip|imageView2/2/w/981/format/webp)

$$示意图$$
更加详细 & 具体的介绍，请看文章：[Android自定义View基础：ViewRoot、DecorView & Window的简介](https://www.jianshu.com/p/28d396a0f05f)

#### 1.4 自定义View基础
了解自定义View流程前，需了解一定的自定义View基础，具体请看文章：[自定义View基础](https://www.jianshu.com/p/146e5cec4863)

### 2. 绘制准备
回忆上图，可看出最后1步 = 绘制
![](https://upload-images.jianshu.io/upload_images/944365-34992eb46bdf93e7.png?imageMogr2/auto-orient/strip|imageView2/2/w/981/format/webp)
$$示意图$$
- 但在绘制前，系统会有一些绘制准备，即前面几个步骤：创建PhoneWindow类、DecorView类、ViewRootmpl类等
>故，下面我会先将绘制前的准备，再开始讲绘制流程

- 主要包括：DecorView创建 & 显示，具体请看文章：[Android自定义View绘制前的准备：DecorView创建 & 显示](https://www.jianshu.com/p/ac3262d233af)

### 3. 绘制流程概述
从上可知，View的绘制流程开始于：ViewRootImpl对象的performTraversals()
**源码分析**
```java
/**
  * 源码分析：ViewRootImpl.performTraversals()
  */
  private void performTraversals() {

        // 1. 执行measure流程
        // 内部会调用performMeasure()
        measureHierarchy(host, lp, res,desiredWindowWidth, desiredWindowHeight);

        // 2. 执行layout流程
        performLayout(lp, mWidth, mHeight);

        // 3. 执行draw流程
        performDraw();
    }
```
从上面的performTraversals()可知：View的绘制流程从顶级View（DecorView）的ViewGroup开始，一层一层从ViewGroup至子View遍历测绘
>即：自上而下遍历、由父视图到子视图、每一个 ViewGroup 负责测绘它所有的子视图，而最底层的 View 会负责测绘自身

![](https://upload-images.jianshu.io/upload_images/944365-a9470d2b71e38f78.png?imageMogr2/auto-orient/strip|imageView2/2/w/686/format/webp)

绘制的流程 = measure过程、layout过程、draw过程，具体如下
![](https://upload-images.jianshu.io/upload_images/944365-c1adb9dd2d22c056.png?imageMogr2/auto-orient/strip|imageView2/2/w/983/format/webp)
$$示意图$$
![](https://upload-images.jianshu.io/upload_images/944365-858de1faa38df1b2.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)
$$示意图$$

**下面，View绘制的三大流程：measure过程、layout过程、draw过程**

### 4. 详细介绍

#### 4.1 Measure 过程
- 作用
  测量View的宽 / 高
  
>1. 在某些情况下，需要多次测量（measure）才能确定View最终的宽/高；
>2. 该情况下，measure过程后得到的宽 / 高可能不准确；
>3. 此处建议：在layout过程中onLayout()去获取最终的宽 / 高

- 具体流程
![](https://upload-images.jianshu.io/upload_images/944365-4654ff32550dc58c.png?imageMogr2/auto-orient/strip|imageView2/2/w/550/format/webp)

![](https://upload-images.jianshu.io/upload_images/944365-1250b5f61c90147f.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

![](https://upload-images.jianshu.io/upload_images/944365-1250b5f61c90147f.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

- 详细讲解
请看文章：[自定义View Measure过程](https://www.jianshu.com/p/1dab927b2f36)


#### 4.2 Layout过程
- 作用
计算视图（View）的位置
>即计算View的四个顶点位置：Left、Top、Right 和 Bottom

- 具体流程
![](https://upload-images.jianshu.io/upload_images/944365-bb11305f1e40a8fb.png?imageMogr2/auto-orient/strip|imageView2/2/w/560/format/webp)

![](https://upload-images.jianshu.io/upload_images/944365-6baebb31c56040dc.png?imageMogr2/auto-orient/strip|imageView2/2/w/970/format/webp)


- 详细讲解
请看文章：[自定义View Layout过程](https://www.jianshu.com/p/158736a2549d)

4.3 Draw过程
- 作用
绘制View视图

- 具体流程

![](https://upload-images.jianshu.io/upload_images/944365-53962940989bb451.png?imageMogr2/auto-orient/strip|imageView2/2/w/500/format/webp)
![](https://upload-images.jianshu.io/upload_images/944365-c9d3cd1d746be319.png?imageMogr2/auto-orient/strip|imageView2/2/w/1010/format/webp)


- 详细讲解
请看文章：[自定义View Draw过程](https://www.jianshu.com/p/95afeb7c8335)

**至此，关于自定义View的工作流程讲解完毕。**

### 5. 自定义View的步骤

#### 步骤1：实现Measure、Layout、Draw流程
- 从View的工作流程（measure过程、layout过程、draw过程）来看，若要实现自定义View，根据自定义View的种类不同（单一View / ViewGroup），需自定义实现不同的方法
- 主要是：onMeasure()、onLayout()、onDraw()，具体如下
![](https://upload-images.jianshu.io/upload_images/944365-0082de4f47f2d0c3.png?imageMogr2/auto-orient/strip|imageView2/2/w/610/format/webp)

#### 步骤2：自定义属性
1. 在values目录下创建自定义属性的xml文件
2. 在自定义View的构造方法中加载自定义XML文件 & 解析属性值
3. 在布局文件中使用自定义属性


#### 6. 实例讲解
结合原理 & 实现步骤，若需实现1个自定义View，请看文章：[手把手教你写一个完整的自定义View](https://www.jianshu.com/p/e9d8420b1b9c)


