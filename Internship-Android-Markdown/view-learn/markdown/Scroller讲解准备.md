### 3.3.1 使用Scroller

**Scroller#startScroll()**
其实只是设置了起始位置和滑动的距离以及时间,真正启动滑动的是`invalidate()`

**invalidate()**
`invalidate()`会触发`View`的`onDraw()`使`View`重新绘制,那么在`onDraw()`被调用的时候会调用`computeScroll()`.

**computeScroll()**
`computeScroll()`里面就可以写我们需要的滑动逻辑了(实际还是用`scrollBy/scrollTo`实现的滑动),只不过`computeScroller`会把一大段距离分成一小段一小段的，看起来像动画
```java
if (mScroller.computeScrollOffset()) {
    scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
    postInvalidate();
}
```
`getCurrX()/getCurrY()`就是细分后的距离,`postInvalidate()`会再次触发`onDraw()`进而触发`computeScroll()`，循环往复直至完成动画
```java
public boolean computeScrollOffset() {
    ...
    int timePassed = (int)(AnimationUtils.currentAnimationTimeMillis() -mStartTime);
    if (timePassed < mDuration) {
        switch (mMode) {
            case SCROLL_MODE:
            final float x = mInterpolator.getInterpolation(timePassed *mDurationReciprocal);
            mCurrX = mStartX + Math.round(x * mDeltaX);
            mCurrY = mStartY + Math.round(x * mDeltaY);
            break;
            ...
        }
    }
    return true;
}
```
其中的`computeScrollOffset()`起到一个计算每一小段距离的作用，类似于估值器
如果我们想要滑动的过程中有不同的速率效果，可以在`new Scroller(context,Interpolator)`指定不同的插值器，也可以自己定义插值器

`Scroller`是无法改变View的实际位置的，只能改变`View`的内容的位置
