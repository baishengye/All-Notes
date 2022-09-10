## 自定义View 绘制过程(Draw)

### 目录
![](https://upload-images.jianshu.io/upload_images/944365-43cb857e90e42fb6.png?imageMogr2/auto-orient/strip|imageView2/2/w/1030/format/webp)

### 1. 作用
绘制View视图

### 2. 储备知识
具体请看文章：[自定义View基础 - 最易懂的自定义View原理系列](https://www.jianshu.com/p/146e5cec4863)

### 3. draw过程详解
类似measure过程、layout过程，draw过程根据View的类型分为2种情况：

![](https://upload-images.jianshu.io/upload_images/944365-2dc3a798a3039bb5.png?imageMogr2/auto-orient/strip|imageView2/2/w/410/format/webp
)
接下来，我将详细分析这2种情况下的draw过程

#### 3.1 单一View的draw过程
##### 应用场景
在无现成的控件View满足需求、需自己实现时，则使用自定义单一View
>如：制作一个支持加载网络图片的ImageView控件
>注：自定义View在多数情况下都有替代方案：图片 / 组合动画，但二者可能会导致内存耗费过大，从而引起内存溢出等问题。


##### 具体使用
继承自View、SurfaceView 或 其他View；不包含子View

##### 原理（步骤）

1. View绘制自身（含背景、内容）；
2. 绘制装饰（滚动指示器、滚动条、和前景）


##### 具体流程


下面我将一个个方法进行详细分析：draw过程的入口 = draw（）
```java
/**
  * 源码分析：draw()
  * 作用：根据给定的 Canvas 自动渲染View包括其所有子 View）。
  * 绘制过程：
  *   1. 绘制view背景
  *   2. 绘制view内容
  *   3. 绘制子View
  *   4. 绘制装饰（渐变框，滑动条等等）
  * 注：
  *    a. 在调用该方法之前必须要完成 layout 过程
  *    b. 所有的视图最终都是调用 View 的 draw()绘制视图（ ViewGroup 没有复写此方法）
  *    c. 在自定义View时，不应该复写该方法，而是复写 onDraw(Canvas) 方法进行绘制
  *    d. 若自定义的视图确实要复写该方法，那么需先调用 super.draw(canvas)完成系统的绘制，然后再进行自定义的绘制
  */ 
  public void draw(Canvas canvas) {

    ...// 仅贴出关键代码
  
    int saveCount;

    // 步骤1： 绘制本身View背景
      if (!dirtyOpaque) {
          drawBackground(canvas); 
          // ->分析1
      }

      // 若有必要，则保存图层（还有一个复原图层）
      // 优化技巧：当不需绘制 Layer 时，“保存图层“和“复原图层“这两步会跳过
      // 因此在绘制时，节省 layer 可以提高绘制效率
      final int viewFlags = mViewFlags;
      if (!verticalEdges && !horizontalEdges) {

        // 步骤2：绘制本身View内容
        if (!dirtyOpaque) 
            onDraw(canvas);
        // 单一View中：默认为空实现，需复写
        // ViewGroup中：需复写
        // ->分析2

        // 步骤3：绘制子View
        // 由于单一View无子View，故View中：默认为空实现
        // ViewGroup中：系统已经复写好对其子视图进行绘制我们不需要复写
            dispatchDraw(canvas);
        // ->分析3

        // 步骤4：绘制装饰，如滑动条、前景色等等
        onDrawScrollBars(canvas);
        // ->分析4

        return;
    }
    ...    
}


/**
  * 分析1：drawBackground(canvas)
  * 作用：绘制View本身的背景
  */ 
  private void drawBackground(Canvas canvas) {
        // 获取背景 drawable
        final Drawable background = mBackground;
        if (background == null) {
            return;
        }
        // 根据在 layout 过程中获取的 View 的位置参数，来设置背景的边界
        setBackgroundBounds();

        ...

        // 获取 mScrollX 和 mScrollY值 
        final int scrollX = mScrollX;
        final int scrollY = mScrollY;
        if ((scrollX | scrollY) == 0) {
            background.draw(canvas);
        } else {
            // 若 mScrollX 和 mScrollY 有值，则对 canvas 的坐标进行偏移
            canvas.translate(scrollX, scrollY);


            // 调用 Drawable 的 draw 方法绘制背景
            background.draw(canvas);
            canvas.translate(-scrollX, -scrollY);
        }
   } 

/**
  * 分析2：onDraw(canvas)
  * 作用：绘制View本身的内容
  * 注：
  *   a. 由于 View 的内容各不相同，所以该方法是一个空实现
  *   b. 在自定义绘制过程中，需由子类去实现复写该方法，从而绘制自身的内容
  *   c. 谨记：自定义View中 必须且只需复写onDraw()
  */
  protected void onDraw(Canvas canvas) {
      
        ... // 复写从而实现绘制逻辑

  }

/**
  * 分析3： dispatchDraw(canvas)
  * 作用：绘制子View
  * 注：由于单一View中无子View，故为空实现
  */
  protected void dispatchDraw(Canvas canvas) {

        ... // 空实现

  }

/**
  * 分析4： onDrawScrollBars(canvas)
  * 作用：绘制装饰，如滚动指示器、滚动条、和前景等
  */
  public void onDrawForeground(Canvas canvas) {
        onDrawScrollIndicators(canvas);
        onDrawScrollBars(canvas);

        final Drawable foreground = mForegroundInfo != null ? mForegroundInfo.mDrawable : null;
        if (foreground != null) {
            if (mForegroundInfo.mBoundsChanged) {
                mForegroundInfo.mBoundsChanged = false;
                final Rect selfBounds = mForegroundInfo.mSelfBounds;
                final Rect overlayBounds = mForegroundInfo.mOverlayBounds;

                if (mForegroundInfo.mInsidePadding) {
                    selfBounds.set(0, 0, getWidth(), getHeight());
                } else {
                    selfBounds.set(getPaddingLeft(), getPaddingTop(),
                            getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
                }

                final int ld = getLayoutDirection();
                Gravity.apply(mForegroundInfo.mGravity, foreground.getIntrinsicWidth(),
                        foreground.getIntrinsicHeight(), selfBounds, overlayBounds, ld);
                foreground.setBounds(overlayBounds);
            }

            foreground.draw(canvas);
        }
    }
```
至此，单一View的draw过程已分析完毕。

##### 总结
单一View的draw过程解析如下：

>即 只需绘制View自身

![](https://upload-images.jianshu.io/upload_images/944365-17d21164ec837057.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

#### 3.2 ViewGroup的draw过程
##### 应用场景
利用现有的组件根据特定的布局方式来组成新的组件

##### 具体使用
继承自ViewGroup 或 各种Layout；含有子 View

>如：底部导航条中的条目，一般都是上图标(ImageView)、下文字(TextView)，那么这两个就可以用自定义ViewGroup组合成为一个Veiw，提供两个属性分别用来设置文字和图片，使用起来会更加方便。

![](https://upload-images.jianshu.io/upload_images/944365-3eb8d5d9d7d9e9fd.png?imageMogr2/auto-orient/strip|imageView2/2/w/504/format/webp)

##### 原理（步骤）

1. ViewGroup绘制自身（含背景、内容）；
2. ViewGroup遍历子View & 绘制其所有子View；
    >类似于单一View的draw过程
3. ViewGroup绘制装饰（滚动指示器、滚动条、和前景）
自上而下、一层层地传递下去，直到完成整个View树的draw过程

![](https://upload-images.jianshu.io/upload_images/944365-7133935cb1e56190.png?imageMogr2/auto-orient/strip|imageView2/2/w/661/format/webp)

##### 具体流程

下面我将对每个步骤和方法进行详细分析：draw过程的入口 = draw（）
```java
/**
  * 源码分析：draw（）
  * 与单一View的draw（）流程类似
  * 作用：根据给定的 Canvas 自动渲染 View（包括其所有子 View）
  * 绘制过程：
  *   1. 绘制view背景
  *   2. 绘制view内容
  *   3. 绘制子View
  *   4. 绘制装饰（渐变框，滑动条等等）
  * 注：
  *    a. 在调用该方法之前必须要完成 layout 过程
  *    b. 所有的视图最终都是调用 View 的 draw （）绘制视图（ ViewGroup 没有复写此方法）
  *    c. 在自定义View时，不应该复写该方法，而是复写 onDraw(Canvas) 方法进行绘制
  *    d. 若自定义的视图确实要复写该方法，那么需先调用 super.draw(canvas)完成系统的绘制，然后再进行自定义的绘制
  */ 
  public void draw(Canvas canvas) {

    ...// 仅贴出关键代码
  
    int saveCount;

    // 步骤1： 绘制本身View背景
        if (!dirtyOpaque) {
            drawBackground(canvas);
        }

    // 若有必要，则保存图层（还有一个复原图层）
    // 优化技巧：当不需绘制 Layer 时，“保存图层“和“复原图层“这两步会跳过
    // 因此在绘制时，节省 layer 可以提高绘制效率
    final int viewFlags = mViewFlags;
    if (!verticalEdges && !horizontalEdges) {

    // 步骤2：绘制本身View内容
        if (!dirtyOpaque) 
            onDraw(canvas);
        // View 中：默认为空实现，需复写
        // ViewGroup中：需复写

    // 步骤3：绘制子View
    // ViewGroup中：系统已复写好对其子视图进行绘制，不需复写
        dispatchDraw(canvas);
        
    // 步骤4：绘制装饰，如滑动条、前景色等等
        onDrawScrollBars(canvas);

        return;
    }
    ...    
}
由于 步骤2：drawBackground（）、步骤3：onDraw（）、步骤5：onDrawForeground（），与单一View的draw过程类似，此处不作过多描述

下面直接进入与单一View draw过程最大不同的步骤4：dispatchDraw()
/**
  * 源码分析：dispatchDraw（）
  * 作用：遍历子View & 绘制子View
  * 注：
  *   a. ViewGroup中：由于系统为我们实现了该方法，故不需重写该方法
  *   b. View中默认为空实现（因为没有子View可以去绘制）
  */ 
    protected void dispatchDraw(Canvas canvas) {
        ......

         // 1. 遍历子View
        final int childrenCount = mChildrenCount;
        ......

        for (int i = 0; i < childrenCount; i++) {
                ......
                if ((transientChild.mViewFlags & VISIBILITY_MASK) == VISIBLE ||
                        transientChild.getAnimation() != null) {
                  // 2. 绘制子View视图 ->>分析1
                    more |= drawChild(canvas, transientChild, drawingTime);
                }
                ....
        }
    }

/**
  * 分析1：drawChild（）
  * 作用：绘制子View
  */
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        // 最终还是调用了子 View 的 draw （）进行子View的绘制
        return child.draw(canvas, this, drawingTime);
    }
```
至此，ViewGroup的draw过程已分析完毕。

##### 总结
ViewGroup的draw过程如下：
![](https://upload-images.jianshu.io/upload_images/944365-1842dddc9c06df05.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

### 4. 其他细节问题：View.setWillNotDraw()
```java
/**
  * 源码分析：setWillNotDraw()
  * 定义：View 中的特殊方法
  * 作用：设置 WILL_NOT_DRAW 标记位；
  * 注：
  *   a. 该标记位的作用是：当一个View不需要绘制内容时，系统进行相应优化
  *   b. 默认情况下：View 不启用该标记位（设置为false）；ViewGroup 默认启用（设置为true）
  */ 

public void setWillNotDraw(boolean willNotDraw) {

    setFlags(willNotDraw ? WILL_NOT_DRAW : 0, DRAW_MASK);

}

// 应用场景
// a. setWillNotDraw参数设置为true：当自定义View继承自 ViewGroup 、且本身并不具备任何绘制时，设置为 true 后，系统会进行相应的优化。
// b. setWillNotDraw参数设置为false：当自定义View继承自 ViewGroup 、且需要绘制内容时，那么设置为 false，来关闭 WILL_NOT_DRAW 这个标记位。
```

### 5. 总结
本文全面总结了自定义View的Draw过程，总结如下
|View类型|	绘制流程(Draw)|
|:---:|:---:|
|单一View|	仅绘制视图View本身。|
|视图组ViewGroup|	绘制视图本身通知包含的所有子View绘制试视图。|

![](https://upload-images.jianshu.io/upload_images/944365-75cc4adf2162d5ec.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)



**本文章作学习笔记之用，原文章**
```java
作者：Carson带你学安卓
链接：https://www.jianshu.com/p/95afeb7c8335
来源：简书
```