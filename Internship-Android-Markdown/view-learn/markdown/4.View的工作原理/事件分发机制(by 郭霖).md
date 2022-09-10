## 事件分发机制

### 基本认识
#### 事件分发的对象

事件分发的对象是触摸事件(touch事件),当用户触摸屏幕的时候，将产生点击事件。事件类型分为四种:

|ACRION_DOWN|按下屏幕时,触发此事件，只会触发一次|
|:--|:--|
|ACTION_MOVE|移动手指时,触发此事件,会多次触发|
|ACTION_UP|手指离开屏幕时，触发此事件，指挥触发一次|
|ACTION_CANCEL|取消事件时,触发此事件，只会触发一次|


同一个事件序列：指从手指刚接触屏幕，到手指离开屏幕的那一刻结束，在这一过程产生的一系列事件，这个序列一般以down事件开始，中间含有多个move事件，最终以up事件结束


#### 事件分发的本质
将点击事件(MotionEvent)传递到某个具体的View处理的整个过程

#### 事件分发的顺序
Activity->Window->DecorView->ViewGroup->View。一个点击事件发生后，总是先传递给当前的Activity，然后Window传给DecorView再传给ViewGroup，最后传给View.

**注**: 在《开发艺术探索》中的事件分发的顺序是:Activity->Window->View。而有的博客上是:Activity->ViewGroup->View，不过其实是一样的：Window是抽象类，其唯一的实现类是PhoneWindow，PhoneWindow将事件直接传递给DecorView，而DecorView继承FrameLayout，FrameLayout又是ViewGroup的子类，所以Window事件分发其实是ViewGroup来实现的，所以事件传递的顺序还是:Activity->ViewGroup->View

### 核心方法
View的事件分发机制主要由事件分发->事件拦截->事件处理三步来进行逻辑控制，这三步对应三个核心方法.

#### 事件分发: dispatchTouchEvent
用来进行事件的分发，如果事件能够传递给当前的View，则该方法一旦被调用，返回结果受当前View的onTouchEvent和下级的disPacthTouchEvent的影响，表示是否消耗当前的事件

```java
/**
*return:
****true: 当前View消耗所有事件
****false:停止分发，交由上层空间的onTouchEvent方法进行消费,如果本层空间是Activity，则事件将被系统消费，处理
*/
public boolean dispatchTouchEvent(MotionEvent ev){}
```

#### 事件拦截：onInterceptTouchEvent
需注意的是在Activity,ViewGroup,View中只有ViewGroup有这个方法。故一旦有点击事件传递给View，则View的onTouchEvent方法就会被调用。

在dispatchTouch Event内部使用，用来判断是否拦截事件。如果当前View拦截了某个事件，那么该事件序列的其它方法也由当前View处理，故该方法不会被再次调用，因为已经无须询问它是否要拦截该事件。

```java
/**
 * 
 * @param ev
 * @return
 *ture:对事件拦截，交给本层的onTouchEvent进行处理
 *false:不拦截，分发到子View，由子View的dispatchTouchEvent进行处理
 *super.onInterceptTouchEvent(ev):默认不拦截
 */
 @Override
 public boolean onInterceptTouchEvent(MotionEvent ev) {
    return super.onInterceptTouchEvent(ev);
 }
```

#### 事件处理：onTouchEvent

在dispatchTouchEvent中调用，用来处理点击事件，返回结果表示是否消耗当前事件，如果不消耗，则在同一事件序列中，当前View无法再接受到剩下的事件，并且事件将重新交给它的父元素处理，即父元素的onTouchEvent会被调用。

```java
/**
 * @return 
 * true:表示onTouchEvent处理后消耗了当前事件
 * false:不响应事件，不断的传递给上层的onTouchEvent方法处理，直到某个View的onTouchEvent返回true,则认为该事件被消费，如果到最顶层View还是返回false,则该事件不消费，将交由Activity的onTouchEvent处理。
 * super.onTouchEvent(ev):默认消耗当前事件，与返回true一致。
 */
@Override
public boolean onTouchEvent(MotionEvent event) {
    return super.onTouchEvent(event);
}
```

### 事件分发机制
在分析事件分发机制时，应该从事件分发的顺序入手一步一步解剖。从上文我们知道事件分发顺序为：**Activity->Window->DecorView->ViewGroup->View**。由于Window与DecorView可以看作是Activity->ViewGroup的过程，故这里将从三部分通过源码来分析事件分发机制：
- Activity对点击事件的分发机制
- ViewGroup对点击事件的分发机制
- View对点击事件的分发机制

#### Activity事件的分发机制
当一个点击事件发生时，事件总是最先传递到当前Activity中，由Activity的dispatchTouchEvent来进行事件分发。而Activity会将事件传递给Window对象来分发，Window对象再传递给DecorView。下面将进行源码分析来验证这个过程：

**看看源码:Activity#dispatchTouchEvent**
```java
public boolean dispatchTouchEvent(MotionEvent ev) {
    //点击事件的开始一般为按下事件,所以总是true
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
        onUserInteraction();
    }
    //如果Activity所属Window的dispatchTouchEvent返回了ture
    //则Activity.dispatchTouchEvent返回ture,点击事件停止往下传递
    if (getWindow().superDispatchTouchEvent(ev)) {
        return true;
    }
    //如果Window的dispatchTouchEvent返回了false,则点击事件传递给Activity.onTouchEvent
    return onTouchEvent(ev);
 }
```
通过源码我们可以知道当点击事件发生时，首先会执行onUserInteraction();这个方法又是什么呢？不急，我们跟踪下去
```java
    //空方法，当该Activity在栈顶时，触屏点击home,back,menu会触发此方法
    public void onUserInteraction() {
    }
```
源码中可以看出在Activity中该方法为空方法，当该Activity在栈顶时，触屏点击home,back,menu会触发此方法，所以这个方法可以实现屏保功能。让我们回到Activity中的dispatchTouchEvent方法中，接着调用了getWindow().superDispatchTouchEvent(ev)方法将事件交给Activity所附属的Window进行分发，如果最终事件被消耗了，则返回true,如果事件没人处理，则Activity调用在自己的onTouchEvent()方法来处理事件

getWindow是一个Window对象，在Window源码中我们可以发现其实Window就是一个抽象类，显而易见其方法自然是抽象方法，所以我们必须找出其具体实现类。


**看看源码:Window#superDispatchTouchEvent**
```java
/**
 * Abstract base class for a top-level window look and behavior policy.  An
 * instance of this class should be used as the top-level view added to the
 * window manager. It provides standard UI policies such as a background, title
 * area, default key processing, etc.
 *
 * <p>The only existing implementation of this abstract class is
 * android.view.PhoneWindow, which you should instantiate when needing a
 * Window.
 */
 public abstract class Window {
    /**
     *用于自定义窗口，如Dialog，将触摸屏事件传递到视图层次结构的下一级。应用程序开发人员不需要实现
     *或调用它。
     *抽象方法
     */
    public abstract boolean superDispatchTouchEvent(MotionEvent event);

 }
```
`The only existing implementation of this abstract class isandroid.view.PhoneWindow, which you should instantiate when needing aWindow.`
Window的唯一实现类是`PhoneWindow`

**看看源码:PhoneWindow#superDispatchTouchEvent**
```java
/**
 * Android-specific Window.
 * <p>
 * todo: need to pull the generic functionality out into a base class
 * in android.widget.
 *
 * @hide
 */
public class PhoneWindow extends Window implements MenuBuilder.Callback {
    // This is the top-level view of the window, containing the window decor.
    private DecorView mDecor;
    @Override
    public boolean superDispatchTouchEvent(MotionEvent event) {
        return mDecor.superDispatchTouchEvent(event);
    }
}
```
从源码中可以发现PhoneWindow将事件直接传递给了DecorView,而这个DecorView又是什么?

**看看源码:DecorView#superDispatchTouchEvent**
```java
public class DecorView extends FrameLayout implements RootViewSurfaceTaker, WindowCallbacks {    
    ......
    public boolean superDispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
    ......
}
```
**看看源码:FrameLayout**
```java
//FrameLayout继承了ViewGroup中的dispatchTouchEvent,没有重写
@RemoteView
public class FrameLayout extends ViewGroup {
    ......
}
```
其实DecorView就是我们通过setContentView设置布局的父容器,我们可以通过getWindow().getDecorView().findViewById(android.R.id.content).getChildAt(0)这个方式就能获取到setContentView中设置的布局。

从DecorView的源码中可以发现DecorView是继承FrameLayout,而FrameLayout又是继承ViewGroup的，故DecorView的间接父类为ViewGroup,在DecorView中superDispatchTouchEvent方法是使用super来调用父类的dispatchTouchEvent，故等于调用ViewGroup的dispatchTouchEvent方法（从源码中我们可以得知FrameLayout并没有dispatchTouchEvent这个方法），于是DecorView将事件传递到了ViewGroup去处理。也可以这么说，事件已经传递到了顶级View也就是Activity中通过setContentView所设置的View（顶级View通常为ViewGroup）。

流程图:
[![jJGxJA.jpg](https://s1.ax1x.com/2022/07/04/jJGxJA.jpg)](https://imgtu.com/i/jJGxJA)
到这里，验证了前面提到的事件分发的顺序是：Activity->Window->DecorView->ViewGroup。那么ViewGroup又是如何将事件传递给View呢？让我们来继续分析！

#### ViewGroup事件的分发机制

从上面Activity事件的分发机制我们可以知道，ViewGroup事件分发机制是从dispatchTouchEvent()开始的，所以我们从这部分的源码开始分析，由于该方法代码量很多，下面将根据需要贴出相关代码：

**看看源码:ViewGroup#dispatchTouchEvent**
```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    ........
    // Check for interception.
    final boolean intercepted;  //是否拦截
    /*
    * 当事件由ViewGroup的子元素处理时，mFirstTouchTarget会被赋值并指向子元素
    */
    if (actionMasked == MotionEvent.ACTION_DOWN 
        || mFirstTouchTarget != null) {
        final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
        if (!disallowIntercept) {
            intercepted = onInterceptTouchEvent(ev);
            ev.setAction(action); // restore action in case it was changed
        } else {
            intercepted = false;
        }
    } else {
        // There are no touch targets and this action is not an initial down
        // so this view group continues to intercept touches.
        intercepted = true;
    }
}
```
从上面源码我们可以知道，ViewGroup判断是否要拦截只会是在ACTION_DOWN的时候，或者是mFirstTouchTarget != null。mFirstTouchTarget 从后面的代码才能知道其作用。它的作用就是：当事件被ViewGroup的某个子View处理时，mFirstTouchTarget 就会指向这个子View。

所以当事件被这个ViewGroup拦截时，子类就不会处理这个事件，因此mFirstTouchTarget =null,那么这个时候ACTION_MOVE和ACTION_UP事件到来时，由于判断条件为false,将导致ViewGroup的onInterceptTouchEvent不会再被调用，然后intercepted被赋予true，所以同一事件序列的其它事件都会默认交给该ViewGroup来处理。在上面源码中我们还可以发现这么一句语句：
```java
final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
```
FLAG_DISALLOW_INTERCEPT是个标记位，这个标记位是通过 requestDisallowInterceptTouchEvent(boolean disallowIntercept)方法来设置的，一般用在子View中。如果FLAG_DISALLOW_INTERCEPT被设置后，ViewGroup将无法拦截除了ACTION_DOWN以外的其它点击事件，这是因为ViewGroup在分发事件中，如果是ACTION_DOWN事件，将会重置FLAG_DISALLOW_INTERCEPT这个标记位。让我们来看看源码。
```java
    if (actionMasked == MotionEvent.ACTION_DOWN) {
    // Throw away all previous state when starting a new touch gesture.
    // The framework may have dropped the up or cancel event for the previous gesture
    // due to an app switch, ANR, or some other state change.
    //
    cancelAndClearTouchTargets(ev);
    resetTouchState();  //对FLAG_DISALLOW_INTERCWPT进行重置
}
```
上面源码是在判断是否拦截的前面的，所以能够重置标记位，从这里我们也可以发现，当点击事件为ACTION_DOWN时，ViewGroup总是会调用自己的onInterceptTouchEvent来询问自己是否要拦截事件。

requestDisallowInterceptTouchEvent方法针对的是ACTION_DOWN以外的其他事件，并且是在不拦截ACTION_DOWN事件的情况下才会起作用。

接下来让我们瞧瞧ViewGroup不再拦截事件的时候，事件的分发情况，源码如下：
```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    ...
    if (onFilterTouchEventForSecurity(ev)) {
        ...
        if (!canceled && !intercepted) {
            ...
            final View[] children = mChildren; 
            for (int i = childrenCount - 1; i >= 0; i--) {  //遍历ViewGroup的所有子元素
                final int childIndex = getAndVerifyPreorderedIndex(childrenCount, i, customOrder);
                final View child = getAndVerifyPreorderedView(preorderedList, children, childIndex);
                // If there is a view that has accessibility focus we want it
                // to get the event first and if not handled we will perform a
                // normal dispatch. We may do a double iteration but this is
                // safer given the timeframe.
                if (childWithAccessibilityFocus != null) {
                    if (childWithAccessibilityFocus != child) {
                        continue;
                    }
                    childWithAccessibilityFocus = null;
                    i = childrenCount - 1;
                }
            
                /**
                ** 判断子元素是否能够接受到点击事件：
                ** 子元素是否在播动画和点击事件的坐标是否落在子元素的区域内
                ** 如果某个元素满足这两个条件，则事件交给它来处理
                **/

                if (!canViewReceivePointerEvents(child)
                || !isTransformedTouchPointInView(x, y, child, null)) {
                    ev.setTargetAccessibilityFocus(false);
                    continue;
                }

                newTouchTarget = getTouchTarget(child);
                if (newTouchTarget != null) {
                    // Child is already receiving touch within its bounds.
                    // Give it the new pointer in addition to the ones it is handling.
                    newTouchTarget.pointerIdBits |= idBitsToAssign;
                    break;
                }

                resetCancelNextUpFlag(child);

                //dispatchTransformedTouchEvent实际调用的是子元素的dispatchTouchEvent方法

                if (dispatchTransformedTouchEvent(ev, false, child, idBitsToAssign)) {
                    // Child wants to receive touch within its bounds.
                    mLastTouchDownTime = ev.getDownTime();
                    if (preorderedList != null) {
                        // childIndex points into presorted list, find original index
                        for (int j = 0; j < childrenCount; j++) {
                            if (children[childIndex] == mChildren[j]) {
                                mLastTouchDownIndex = j;
                                    break;
                            }
                        }
                    } else {
                        mLastTouchDownIndex = childIndex;
                    }
                    mLastTouchDownX = ev.getX();
                    mLastTouchDownY = ev.getY();

                    newTouchTarget = addTouchTarget(child, idBitsToAssign);
                    //记录ACTION_DOWN事件已经被处理了
                    alreadyDispatchedToNewTouchTarget = true;
                    break;
                }

                // The accessibility focus didn't handle the event, so clear
                // the flag and do a normal dispatch to all children.
                ev.setTargetAccessibilityFocus(false);
            }
            ...
        }
        ...
    }
    ...
}

```
从上面代码中我们可以知道，不拦截事件时，首先会遍历ViewGroup的所有子元素，然后判断子元素是否能够接受到点击事件。判断的依据是：子元素是否在播放动画和点击事件的坐标是否落在子元素的区域内。如果找到一个目标子View来处理事件时，则调用dispatchTransformedTouchEvent()方法。来看看这个方法重要实现逻辑：
```java
private boolean dispatchTransformedTouchEvent(MotionEvent event, boolean cancelView child, int desiredPointerIdBits) {
        final boolean handled;

        // Canceling motions is a special case.  We don't need to perform any transformations
        // or filtering.  The important part is the action, not the contents.
        final int oldAction = event.getAction();
        if (cancel || oldAction == MotionEvent.ACTION_CANCEL) {
            event.setAction(MotionEvent.ACTION_CANCEL);
            if (child == null) {
                handled = super.dispatchTouchEvent(event);
            } else {
                handled = child.dispatchTouchEvent(event);
            }
            event.setAction(oldAction);
            return handled;
        }
}
```
可以发现由于在上面中的child并不等于null,所以将直接调用子元素的dispatchTouchEvent方法，使得事件传递到子View上，然后继续分发。你以为这样就结束了？答案肯定是没有，从上面源码中我们可以发现当子元素的dispatchTouchEvent返回true后，还有相应操作:
```java
newTouchTarget = addTouchTarget(child, idBitsToAssign);
//记录ACTION_DOWN事件已经被处理了
alreadyDispatchedToNewTouchTarget = true;
break;
```

这几行代码完成了mFirstTouchTarget的赋值并终止了对子元素的遍历。如果子元素的dispatchTouchEvent返回false,则ViewGroup就会把事件分发给下一个元素（如果还有子元素的话），看到这你也许又纳闷了，mFirstTouchTarget的赋值？怎么没看见mFirstTouchTarget的影子呢，答案其实在addTouchTarget这个方法中：
```java
private TouchTarget addTouchTarget(@NonNull View child, int pointerIdBits) {
    final TouchTarget target = TouchTarget.obtain(child, pointerIdBits);
    target.next = mFirstTouchTarget;
    mFirstTouchTarget = target;
    return target;
}
```
这个方法可以看出其实mFirstTouchTarget是一种单链表结构，首先根据坐标点找到了目标子View，然后将子View放在链表头上，从而实现了mFirstTouchTarget！=null。

到这里就完成了ViewGroup一轮的事件分发了，然而还没有结束，如果遍历了所有子元素后事件都没有被合适处理呢？
没有合适处理包括了两种情况：
* ViewGroup没有子元素
* 子元素处理了点击事件，但是在dispatchTouchEvent中返回了false（默认是返回true，只有重写View的这个方法或者在onTouchEvent中返回了false）
```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    ...
    if (onFilterTouchEventForSecurity(ev)) {   
        ...
        if (mFirstTouchTarget == null) {
             // No touch targets so treat this as an ordinary view.
            handled = dispatchTransformedTouchEvent(ev, canceled, null,TouchTarget.ALL_POINTER_IDS);
        } else {
        }
        ...
    }
    ...
}
```
可以看出这时候还是调用了dispatchTransformedTouchEvent方法，不过这时候第三个参数不是child而是null,所以会调用下面这句代码：
```java
//dispatchTransformedTouchEvent
handled = super.dispatchTouchEvent(event);
```
super其实就是View中的dispatchTouchEvent方法，所以点击事件开始交由View来处理。

ViewGroup并没有调用onTouchEvent，ViewGroup也没有去重写onTouchEvent。流程图如下：
[![jJrDJK.png](https://s1.ax1x.com/2022/07/04/jJrDJK.png)](https://imgtu.com/i/jJrDJK)


#### View事件的分发机制
从上面对ViewGroup事件分发机制可知，View事件分发机制是从dispatchTouchEvent开始的

**源码：View#dispatchTouchEvent**
```java
public boolean dispatchTouchEvent(MotionEvent event) {
    ......
    boolean result = false;
    ......
    //判断窗口是否被遮挡，如果被遮挡则返回false,比如有时候两个View是会重叠的，导致其中一个被遮挡了。
    if (onFilterTouchEventForSecurity(event)) {
        if ((mViewFlags & ENABLED_MASK) == ENABLED && handleScrollBarDragging(event)) {
            result = true;
        }
        //noinspection SimplifiableIfStatement
        ListenerInfo li = mListenerInfo;
        //判断是否设置了mOnTouchListener，如果设置了onTouchListener,且onTouch方法返回了ture,
        //则result = true
        if (li != null && li.mOnTouchListener != null
            && (mViewFlags & ENABLED_MASK) == ENABLED
            && li.mOnTouchListener.onTouch(this, event)) {
            result = true;
        }
        //在result = ture情况下，就不会调用onTouchEvent,可见onTouchListener的优先级高onTouchEvent
        if (!result && onTouchEvent(event)) {
            result = true;
        }
    }
    ......
    return result;
}
```
由于View是一个单独元素，没有子元素可以继续向下传递事件，只能自己处理事件，所以代码也会明显减少。从上面的源码中我们可以看到View对点击事件的处理过程，result代表是否消耗该事件，然后进行onTouchListener的判断，如果onTouchListenter中的onTouch方法返回了true,那么就不会再调用onTouchEvent方法，由此可见onTouchListener的优先级高于onTouchEvent。

然后来看看onTouchEvent的实现
```java
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        final int viewFlags = mViewFlags;
        final int action = event.getAction();

        final boolean clickable = ((viewFlags & CLICKABLE) == CLICKABLE
                || (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE)
                || (viewFlags & CONTEXT_CLICKABLE) == CONTEXT_CLICKABLE;

        //不可用状态下点击事件的处理，依然会消耗点击事件
        if ((viewFlags & ENABLED_MASK) == DISABLED) {
            if (action == MotionEvent.ACTION_UP && (mPrivateFlags & PFLAG_PRESSED) != 0) {
                setPressed(false);
            }
            mPrivateFlags3 &= ~PFLAG3_FINGER_DOWN;
            // A disabled view that is clickable still consumes the touch
            // events, it just doesn't respond to them.
            return clickable; 
        }
        //如果VIew设置了代理，将会执行代理的onTouchEvent方法
        if (mTouchDelegate != null) {
            if (mTouchDelegate.onTouchEvent(event)) {
                return true;
            }
        }

        if (clickable || (viewFlags & TOOLTIP) == TOOLTIP) {
            switch (action) {
                case MotionEvent.ACTION_UP:
                    .....
                    boolean prepressed = (mPrivateFlags & PFLAG_PREPRESSED) != 0;
                          ......
                          //经过种种判断
                           performClickInternal();
                    break;

                case MotionEvent.ACTION_DOWN:
                    ....
                    break;

                case MotionEvent.ACTION_CANCEL:
                    ....
                    break;

                case MotionEvent.ACTION_MOVE:
                    ....
                    break;
            }
           //若该控件可点击，就一定返回true
            return true;
        }
        //若该控件不可点击，就一定返回false
        return false;
    }
```
从上面代码可以知道只要View的CLICKABLE，LONG_CLICKABLE，CONTEXT_CLICKABLE有一个为true，那么它就会消耗该事件，不管它是不是DISABLE状态。然后假如控件可点击，就对四种事件类型进行相对应的处理，这里值得一说的是ACTION_UP事件，从源码中可以发现在ACTION_UP事件发生时，会触发performClickInternal方法。这个方法内部实现又是怎样的呢？如下:
```java
    private boolean performClickInternal() {
        // Must notify autofill manager before performing the click actions to avoid scenarios where
        // the app has a click listener that changes the state of views the autofill service might
        // be interested on.
        notifyAutofillManagerOnClick();

        return performClick();
    }
```
我们可以发现最后还是会调用performClick，而在performClick内部中：
```java
  public boolean performClick() {
        // We still need to call this method to handle the cases where performClick() was called
        // externally, instead of through performClickInternal()
        notifyAutofillManagerOnClick();

        final boolean result;
        final ListenerInfo li = mListenerInfo;
        if (li != null && li.mOnClickListener != null) {
            playSoundEffect(SoundEffectConstants.CLICK);
            li.mOnClickListener.onClick(this);
            result = true;
        } else {
            result = false;
        }

        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);

        notifyEnterOrExitForAutoFillIfNeeded(true);

        return result;
    }      
```
只要我们通过setOnClickListener为View注册点击事件，那么就会给li.mOnClickListener赋值，则会调用onClick方法。
[![jJgU5n.png](https://s1.ax1x.com/2022/07/04/jJgU5n.png)](https://imgtu.com/i/jJgU5n)
从流程图我们可以发现onTouch,onTouchEvent,onClick的优先级：onTouch>onTouchEvent>onClick。