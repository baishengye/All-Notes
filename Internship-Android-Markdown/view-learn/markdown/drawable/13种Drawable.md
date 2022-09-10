## 前言
在实际项目开发中，我们都会定义很多各式各样的`Drawable`来实现需求上所要求的`UI`效果，如果是稍加复杂的`UI`需求，我们则会通过自定义`View`的方式来实现。今天，我就分享一下一些常用`Drawable`的总结，介绍一下如何静态及动态地生成各式`Drawable`，以及介绍一下自定义`Drawable`。

>`Drawable` 是可绘制对象的一个抽象类，相对比`View`来说，它更加的纯粹，只用来处理绘制的相关工作而不处理与用户的交互事件，所以适合用来处理背景的绘制。

在介绍自定义`Drawable`前，我们先来学习一下几种常见的`Drawable`。

## 可绘制对象资源介绍
可绘制对象是指可在屏幕上绘制的图形，可以通过`getDrawable(int)`等方法来获取，然后应用到 `android:drawable` 和 `android:icon` 等属性方法中。

**下面介绍几种常见的可绘制对象，我会分三个步骤来介绍**：

- 介绍一下在`XML`中的使用方法（会举例说明）。
- 然后介绍一下其属性方法。
- 再以代码的形式来动态实现`XML`中的同样效果（会举例说明）。

### 1. `BitmapDrawable`
**位图图像**。`Android`支持三种格式的位图文件：`.png`(首选)、`.jpg`(可接受)、`.gif`(不建议)。我们可以直接使用文件名作为资源 `ID` 来引用位图文件，也可以在 `XML` 文件中创建别名资源 `ID`，这就叫做 `XML`位图。
**XML位图**：通过`XML`文件来定义，指向位图文件，文件位于`res/drawable/filename.xml`，其文件名就是作为引用的资源 `ID`，如：`R.drawable.filename`。

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/cc36c5cb4cb24d26bc1358bd1357fec2~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

**关于 <bitmap> 属性**：

- `android:src`：引用可绘制对象资源，**必备**。
- `android:tileMode`：定义平铺模式。当平铺模式启用时，位图会重复，**且注意：一旦平铺模式启用， android:gravity 属性就将会被忽略**。
定义平铺属性的值必须是以下值之一：

    - `disabled`：不平铺位图，默认值。
    - `clamp`：当着色器绘制范围超出其原边界时复制边缘颜色。
    - `repeat`：水平和垂直重复着色器的图像。
    - `mirror`：水平和垂直重复着色器的图像，交替镜像图像以使相邻图像始终相接。注意：在平铺模式启用时`android:gravity` 属性将被忽略。

- `android:gravity`：定义位图的重力属性，当位图小于容器时，可绘制对象在其容器中放置的位置。

    - `top`：将对象放在其容器顶部，不改变其大小。
    - `bottom`：将对象放在其容器底部，不改变其大小。
    - `left`：将对象放在其容器左边缘，不改变其大小。
    - `right`：将对象放在其容器右边缘，不改变其大小。
    - `center_vertical`：将对象放在其容器的垂直中心，不改变其大小。
    - `fill_vertical`：按需要扩展对象的垂直大小，使其完全适应其容器。
    - `center_horizontal`：将对象放在其容器的水平中心，不改变其大小。
    - `fill_horizontal`：按需要扩展对象的水平大小，使其完全适应其容器。
    - `center`：将对象放在其容器的水平和垂直轴中心，不改变其大小。
    - `fill`：按需要扩展对象的垂直大小，使其完全适应其容器。这是默认值。
    - `clip_vertical`：可设置为让子元素的上边缘和/或下边缘裁剪至其容器边界的附加选项。裁剪基于垂直重力：顶部重力裁剪上边缘，底部重力裁剪下边缘，任一重力不会同时裁剪两边。
    - `clip_horizontal`：可设置为让子元素的左边和/或右边裁剪至其容器边界的附加选项。裁剪基于水平重力：左边重力裁剪右边缘，右边重力裁剪左边缘，任一重力不会同时裁剪两边。


除了在` XML `文件中定义位图，我们也可以直接通过代码来实现，即`BitmapDrawable`。

```java
val bitmap = BitmapFactory.decodeResource(resources, R.drawable.nick)
val bitmapShape = BitmapDrawable(resources, bitmap)
binding.tv2.background = bitmapShape
```

效果图如下所示：
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/1118e943122948579b360af6222aaa74~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)
 
### 2. `LayerDrawable`
图层列表（`LayerDrawable`）： 是可绘制对象列表组成的可绘制对象。列表中的每个可绘制对象均按照列表顺序绘制，列表中的最后一个可绘制对象绘于顶部。
每个可绘制对象由单一 `<layer-list>` 元素内的 `<item>` 元素表示。

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/69f37105b27d4dda9ae05147d8ec9c36~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

介绍一下其中的属性：

- `<layer-list>`：必备的根元素。包含一个或多个` <item> `元素。
- `<item>`：是 `<layer-list>` 元素的子项，其属性支持定义在图层中所处的位置。

    - `android:drawable`：**必备**。引用可绘制对象资源。
    - `android:top`：整型。顶部偏移（像素）。
    - `android:right`：整型。右边偏移（像素）。
    - `android:bottom`：整型。底部偏移（像素）。
    - `android:left`：整型。左边偏移（像素）。

除了通过在`XML`中实现，我们同样可以通过代码来实现上面同样的效果。
```java
val itemLeft = GradientDrawable().apply {
    setColor(ContextCompat.getColor(requireContext(), R.color.royal_blue))
    setSize(50.px, 50.px)
    shape = GradientDrawable.OVAL
}
val itemCenter = GradientDrawable().apply {
    setColor(ContextCompat.getColor(requireContext(), R.color.indian_red))
    shape = GradientDrawable.OVAL
}
val itemRight = GradientDrawable().apply {
    setColor(ContextCompat.getColor(requireContext(), R.color.yellow))
    shape = GradientDrawable.OVAL
}
val arr = arrayOf(
    ContextCompat.getDrawable(requireContext(), R.drawable.nick)!!,
    itemLeft,
    itemCenter,
    itemRight
)
val ld = LayerDrawable(arr).apply {
    setLayerInset(1, 0.px, 0.px, 250.px, 150.px)
    setLayerInset(2, 125.px, 75.px, 125.px, 75.px)
    setLayerInset(3, 250.px, 150.px, 0.px, 0.px)
}
binding.tv2.background = ld
```

效果图如下所示：
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/1bb40b065e7f4fdfbcdba1b66e41b6cd~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)
 
### 3. `StateListDrawable`
**状态列表**（`StateListDrawable`）：会根据对象状态，使用多个不同的图像来表示同一个图形。

|android:state_pressed="true"|android:state_pressed="false"|
|:---:|:---:|
|![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2670ed9b7cba457a960f02006b16e4a2~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)| ![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/ae9993f1ec5b4929a23de7c121d584d8~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)|

介绍一下其中的属性：
- `<selector>`：必备的根元素。包含一个或多个 `<item>` 元素。
- `<item>`：定义在某些状态期间使用的可绘制对象，必须是 `<selector>` 元素的子项。其属性：

- `android:drawable`：引用可绘制对象资源，必备。
- `android:state_pressed`：布尔值。是否按下对象（例如触摸/点按某按钮）。
- `android:state_checked`：布尔值。是否选中对象。
- `android:state_enabled`：布尔值。是否能够接收触摸或点击事件。


除了通过在`XML`中实现，我们同样可以通过代码来实现上面同样的效果。

```java
val sld = StateListDrawable().apply {
    addState(
        intArrayOf(android.R.attr.state_pressed),
        ContextCompat.getDrawable(requireContext(), R.drawable.basketball)
    )
    addState(StateSet.WILD_CARD, ContextCompat.getDrawable(requireContext(), R.drawable.nick))
}
binding.stateListDrawableTv2.apply {
    background = sld
    setOnClickListener {
        Log.e(TAG, "stateListDrawableTv2: isPressed = $isPressed")
    }
}
```

### 4. `LevelListDrawable`
**级别列表**（`LevelListDrawable`）：管理可绘制对象列表，每个可绘制对象都有设置`Level`等级限制，当使用`setLevel`()时，会加载级别列表中 `android:maxLevel` 值大于或等于传递至方法的值的可绘制对象资源。

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e3459847b0ab450fab85c315ffd36702~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

介绍一下其中的属性：

- `<level-list>`：必备的根元素。包含一个或多个 `<item>` 元素。
- `<item>`：在特定级别下使用的可绘制对象。
- `android:drawable`：**必备**。引用可绘制对象资源。
- `android:maxLevel`：整型。表示该Item允许的最高级别。
- `android:minLevel`：整型。表示该Item允许的最低级别。

在将该 `Drawable `应用到 `View` 后，就可以通过 `setLevel()` 或 `setImageLevel()` 更改级别。
除了通过在`XML`中实现，我们同样可以通过代码来实现上面同样的效果。

```java
class LevelListDrawableFragment : BaseFragment<FragmentLevelListDrawableBinding>() {

    private val lld by lazy {
        LevelListDrawable().apply {
            addLevel(0, 1, getDrawable(R.drawable.nick))
            addLevel(0, 2, getDrawable(R.drawable.tom1))
            addLevel(0, 3, getDrawable(R.drawable.tom2))
            addLevel(0, 4, getDrawable(R.drawable.tom3))
            addLevel(0, 5, getDrawable(R.drawable.tom4))
            addLevel(0, 6, getDrawable(R.drawable.tom5))
            addLevel(0, 7, getDrawable(R.drawable.tom6))
            addLevel(0, 8, getDrawable(R.drawable.tom7))
            addLevel(0, 9, getDrawable(R.drawable.tom8))
            addLevel(0, 10, getDrawable(R.drawable.tom9))
        }
    }

    private fun getDrawable(id: Int): Drawable {
        return (ContextCompat.getDrawable(requireContext(), id)
            ?: ContextCompat.getDrawable(requireContext(), R.drawable.nick)) as Drawable
    }

    private val levelListDrawable by lazy {
        ContextCompat.getDrawable(requireContext(), R.drawable.level_list_drawable)
    }

    override fun initView() {

        binding.levelListDrawableInclude.apply {
            tv1.setText(R.string.level_list_drawable)
            tv1.background = levelListDrawable
            tv2.setText(R.string.level_list_drawable)

            tv2.background = lld
        }

        binding.seekBar.apply {
            //init level
            levelListDrawable?.level = progress
            lld.level = progress
            //add listener
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    levelListDrawable?.level = progress
                    lld.level = progress
                    Log.e(TAG, "onProgressChanged: progreess = $progress")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
        }

    }


}
```

效果图如下所示：

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/fcdf1c3400f24c11a5ea298278725bd2~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

 
### 5. `TransitionDrawable`
转换可绘制对象（`TransitionDrawable`）：可在**两种**可绘制对象资源之间交错淡出。

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2cf26000d3c14f08a1e839fd65966f44~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

介绍一下其中的属性：

- `<transition>`：必备的根元素。包含一个或多个 `<item>` 元素。
- `<item>`：转换部分的可绘制对象。
    - `android:drawable`：必备。引用可绘制对象资源。
    - `android:top、android:bottom、android:left、android:right`：整型。偏移量（像素）。



注意：不能超过两个`Item`，调用 `startTransition()` 向前转换，调用 `reverseTransition()` 向后转换。

除了通过在`XML`中实现，我们同样可以通过代码来实现上面同样的效果。
```java
class TransitionDrawableFragment : BaseFragment<FragmentTransitionDrawableBinding>() {

    private var isShow = false
    private lateinit var manualDrawable: TransitionDrawable

    override fun initView() {
        binding.transitionDrawableInclude.apply {
            val drawableArray = arrayOf(
                ContextCompat.getDrawable(requireContext(), R.drawable.nick),
                ContextCompat.getDrawable(requireContext(), R.drawable.basketball)
            )
            manualDrawable = TransitionDrawable(drawableArray)
            tv2.background = manualDrawable
        }
    }

    private fun setTransition() {
        if (isShow) {
            manualDrawable.reverseTransition(3000)
        } else {
            manualDrawable.startTransition(3000)
        }
    }

    override fun onResume() {
        super.onResume()
        setTransition()
        isShow = !isShow
    }

}
```

效果图如下所示：
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/aec8168453ae4a04b8adb98a707333b5~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)
 
### 6. `InsetDrawable`
插入可绘制对象（`InsetDrawable`）：以指定距离插入其他可绘制对象，当视图需要小于视图实际边界的背景时，此类可绘制对象很有用。

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/189b38f56f414fef9b0121dfa0adab68~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

介绍一下其属性：

- `<inset>`：必备。根元素。
- `android:drawable`：必备。引用可绘制对象资源。
- `android:insetTop、android:insetBottom、android:insetLeft、android:insetRight`：尺寸。插入的，表示为尺寸

除了通过在`XML`中实现，我们同样可以通过代码来实现上面同样的效果。
```java
val insetDrawable = InsetDrawable(
    ContextCompat.getDrawable(requireContext(), R.drawable.nick),
    0f, 0f, 0.5f, 0.25f
)
binding.tv2.background = insetDrawable
```

效果图如下所示：
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8ce38bccf1044d9fa7934d872e29b455~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)
 
### 7. `ClipDrawable`
裁剪可绘制对象（`ClipDrawable`）：根据`level`等级对可绘制对象进行裁剪，可以根据`level`与`gravity`来控制子可绘制对象的宽度与高度。
```xml
<?xml version="1.0" encoding="utf-8"?>
<clip xmlns:android="http://schemas.android.com/apk/res/android"
    android:drawable="@drawable/nick"
    android:clipOrientation="horizontal"
    android:gravity="center">

</clip>
```

介绍一下其属性：

- `<clip>`：必备。根元素。
- `android:drawable`：必备。引用可绘制对象资源。
- `android:clipOrientation`：裁剪方向。
    - `horizontal`：水平裁剪。
    - `vertical`：垂直裁剪。
- `android:gravity`：重力属性。

最后通过设置`level`等级来实现裁剪，`level` 默认级别为 `0`，即完全裁剪，使图像不可见。当级别为 `10,000` 时，图像不会裁剪，而是完全可见。

除了通过在XML中实现，我们同样可以通过代码来实现上面同样的效果。

```java
class ClipDrawableFragment : BaseFragment<FragmentClipDrawableBinding>() {

    private val clipDrawable by lazy {
        ContextCompat.getDrawable(requireContext(), R.drawable.clip_drawable)
    }
    private val manualClipDrawable by lazy {
        ClipDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.nick),
            Gravity.CENTER,
            ClipDrawable.VERTICAL
        )
    }

    override fun initView() {
        binding.clipDrawableInclude.apply {
            tv1.setText(R.string.clip_drawable)
            tv1.background = clipDrawable
            tv2.setText(R.string.clip_drawable)
            tv2.background = manualClipDrawable
        }

        //level 默认级别为 0，即完全裁剪，使图像不可见。当级别为 10,000 时，图像不会裁剪，而是完全可见。
        binding.seekBar.apply {
            //init level
            clipDrawable?.level = progress
            manualClipDrawable.level = progress
            //add listener
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    clipDrawable?.level = progress
                    manualClipDrawable.level = progress
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })
        }
    }

}
```

效果图如下所示：
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/09966e473f1040ae9cd9633c999ac503~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)
 
### 8. `ScaleDrawable`
缩放可绘制对象（`ScaleDrawable`）：根据level等级来更改其可绘制对象大小。
```xml
<?xml version="1.0" encoding="utf-8"?>
<scale xmlns:android="http://schemas.android.com/apk/res/android"
    android:drawable="@drawable/nick"
    android:scaleWidth="100%"
    android:scaleHeight="100%"
    android:scaleGravity="center">

</scale>
```

介绍一下其属性：

- `<scale>`：必备。根元素。
- `android:drawable`：必备。引用可绘制对象资源。
- `android:scaleGravity`：指定缩放后的重力位置。
- `android:scaleHeight`：百分比。缩放高度，表示为可绘制对象边界的百分比。值的格式为 `XX%`。例如：`100%`、`12.5%` 等。
- `android:scaleWidth`：百分比。缩放宽度，表示为可绘制对象边界的百分比。值的格式为 `XX%`。例如：`100%`、`12.5%` 等。

除了通过在`XML`中实现，我们同样可以通过代码来实现上面同样的效果。
```java
val scaleDrawable = ScaleDrawable(
    ContextCompat.getDrawable(requireContext(), R.drawable.nick),
    Gravity.CENTER,
    1f,
    1f
)
binding.tv2.background = scaleDrawable

binding.seekBar.apply {
    //init level
    tv1.background.level = progress
    scaleDrawable.level = progress
    //add listener
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(
            seekBar: SeekBar?,
            progress: Int,
            fromUser: Boolean
        ) {
            tv1.background.level = progress
            scaleDrawable.level = progress
            Log.e(TAG, "onProgressChanged: progreess = $progress")
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }

    })
}
```

效果图如下所示：
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5150c8135a994cfc9c1d95cc3f08e0c3~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)
 
### 9. `ShapeDrawable`
形状可绘制对象（`ShapeDrawable`）：通过XML来定义各种形状的可绘制对象。

介绍一下其属性：

- `<shape>`：必备。根元素。
- `android:shape`：定义形状的类型。
    - `rectangle`：默认形状，填充包含视图的矩形。
    - `oval`：适应包含视图尺寸的椭圆形状。
    - `line`：跨越包含视图宽度的水平线。此形状需要  元素定义线宽。
    - `ring`：环形。
        - `android:innerRadius`：尺寸。环内部（中间的孔）的半径。
        - `android:thickness`：尺寸。环的厚度。
- <corners>：圆角，仅当形状为矩形时适用。
    - `android:radius`：尺寸。所有角的半径。如果想要设置单独某个角，可以使用`android:topLeftRadius、android:topRightRadius、android:bottomLeftRadius、android:bottomRightRadius`。
- `<padding>`：设置内边距。
    - `android:left`：尺寸。设置左内边距。同样还有`android:right、android:top、android:bottom`供选择。
- `<size>`：形状的大小。
    - `android:height`：尺寸。形状的高度。
    - `android:width`：尺寸。形状的宽度。
- `<solid>`：填充形状的纯色。
    - `android:color`：颜色。
- `<stroke>`：形状的笔画
    - `android:width`：尺寸。线宽。
    - `android:color`：颜色。线的颜色。
    - `android:dashGap`：尺寸。短划线的间距。虚线效果。
    - `android:dashWidth`：尺寸。每个短划线的大小。虚线效果。

除了通过在`XML`中实现，我们同样可以通过代码来实现上面同样的效果。

```java
class ShapeDrawableFragment : BaseFragment<FragmentShapeDrawableBinding>() {

    override fun initView() {
        val roundRectShape =
            RoundRectShape(
                floatArrayOf(20f.px, 20f.px, 20f.px, 20f.px, 0f, 0f, 0f, 0f),
                null,
                null
            )
        binding.tv2.background = MyShapeDrawable(roundRectShape)
    }

    /**
     * TODO: 使用 GradientDrawable 效果更好
     */
    class MyShapeDrawable(shape: Shape) : ShapeDrawable(shape) {
        private val fillPaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.parseColor("#4169E1")
        }
        private val strokePaint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.parseColor("#FFBB86FC")
            strokeMiter = 10f
            strokeWidth = 5f.px
            pathEffect = DashPathEffect(floatArrayOf(10f.px, 5f.px), 0f)
        }

        override fun onDraw(shape: Shape?, canvas: Canvas?, paint: Paint?) {
            super.onDraw(shape, canvas, paint)
            shape?.draw(canvas, fillPaint)
            shape?.draw(canvas, strokePaint)
        }
    }

}
```

效果图如下所示：
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2d4dbffc88444e33bac940206326a0c1~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)
 
### 10. `GradientDrawable`
渐变可绘制对象（`GradientDrawable`）：如其名，实现渐变颜色效果。其实也是属于ShapeDrawable。

介绍一下其属性：

- `<shape>`：必备。根元素。
- `gradient`：表示渐变的颜色。
    - `android:angle`：整型。表示渐变的角度。0 表示为从左到右，90 表示为从上到上。注意：必须是 45 的倍数。默认值为 0。
    - `android:centerX`：浮点型。表示渐变中心相对 `X` 轴位置 `(0 - 1.0`)。`android:centerY`同理。
    - `android:startColor`：颜色。起始颜色。`android:endColor、android:centerColor`分别表示结束颜色与中间颜色。
    - `android:gradientRadius`：浮点型。渐变的半径。仅在 `android:type="radial"` 时适用。
        - android:type：渐变的类型。
        - linear：线性渐变。默认为该类型。
        - radial：径向渐变，也就是雷达式渐变，起始颜色为中心颜色。
        - sweep：流线型渐变。

除了通过在`XML`中实现，我们同样可以通过代码来实现上面同样的效果。

```java
val gradientDrawable = GradientDrawable().apply {
    shape = GradientDrawable.OVAL
    gradientType = GradientDrawable.RADIAL_GRADIENT
    colors = intArrayOf(Color.parseColor("#00F5FF"), Color.parseColor("#BBFFFF"))
    gradientRadius = 100f.px
}
binding.tv2.background = gradientDrawable
```

效果图如下所示：
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0e36eef4179146a2b3a8b4f061c214e2~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)
 
### 11. `AnimationDrawable`
动画可绘制对象（`AnimationDrawable`）：用于创建逐帧动画的可绘制对象。

```xml
<?xml version="1.0" encoding="utf-8"?>
<animation-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:drawable="@drawable/nick"
        android:duration="1000" />
    <item
        android:drawable="@drawable/basketball"
        android:duration="1000" />

</animation-list>
```

介绍一下其属性：

- `<animation-list>`：必备。根元素。
- `<item>`：每一帧的可绘制对象。
    - `android:drawable`：必备。引用可绘制对象资源。
    - `android:duration`：该帧的持续时间，单位为毫秒。
    - `android:oneshot`：布尔值。代表是否只单次展示该动画，默认为`false`。

除了通过在`XML`中实现，我们同样可以通过代码来实现上面同样的效果。

```java
val animationDrawable = AnimationDrawable().apply {
    ContextCompat.getDrawable(requireContext(), R.drawable.nick)
        ?.let { addFrame(it, 1000) }
    ContextCompat.getDrawable(requireContext(), R.drawable.basketball)
        ?.let { addFrame(it, 1000) }
}
binding.tv2.background = animationDrawable
animationDrawable.start()
```

效果图如下所示：
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/aee15c56343040bfa2410fb4dd3d37b9~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)


### 12. `ColorDrawable`
- 1).`Java`中定义`ColorDrawable`:
```java
ColorDrawable drawable = new ColorDrawable(0xffff2200);  
txtShow.setBackground(drawable);  
```

- 2).在`xml`中定义`ColorDrawable`:
```xml
<?xml version="1.0" encoding="utf-8"?>  
<color  
    xmlns:android="http://schemas.android.com/apk/res/android"  
    android:color="#FF0000"/>  
```


### 13. `NiewPatchDrawable`
- `xml`定义`NinePatchDrawable`:
```xml
<!--pic9.xml-->  
<!--参数依次为:引用的.9图片,是否对位图进行抖动处理-->  
<?xml version="1.0" encoding="utf-8"?>  
<nine-patch  
    xmlns:android="http://schemas.android.com/apk/res/android"  
    android:src="@drawable/dule_pic"  
    android:dither="true"/>  
```


### 12. 自定义 `Drawable`
介绍完了几种常见的可绘制对象资源，接下来我们进一步学习一下，如果进行自定义`Drawable`。

```java
class JcTestDrawable : Drawable() {

    override fun draw(p0: Canvas) {
        TODO("Not yet implemented")
    }

    override fun setAlpha(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun setColorFilter(p0: ColorFilter?) {
        TODO("Not yet implemented")
    }

    override fun getOpacity(): Int {
        TODO("Not yet implemented")
    }

}
```

从上述代码可以看出，我们需要继承`Drawable()`，然后实现4个方法，分别是：

- `setAlpha`：为`Drawable`指定一个`alpha`值，`0` 表示完全透明，`255` 表示完全不透明。
- `setColorFilter`：为`Drawable`指定可选的颜色过滤器。`Drawable`的`draw`绘图内容的每个输出像素在混合到 `Canvas` 的渲染目标之前将被颜色过滤器修改。传递 `null` 会删除任何现有的颜色过滤器。
- `getOpacity`：返回`Drawable`的透明度，如下所示：
    - `PixelFormat.TRANSLUCENT`：半透明的。
    - `PixelFormat.TRANSPARENT`：透明的。
    - `PixelFormat.OPAQUE`：不透明的。
    - `PixelFormat.UNKNOWN`：未知。
- `draw`：在边界内进行绘制（通过`setBounds()`），受`alpha`与`colorFilter`所影响。

接下里为大家举个例子。


### 13. 举例：滚动篮球

功能介绍：当我们点击屏幕，篮球会滚向该坐标。

如下图所示：
![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/db5a686df4c640cdb6c1a86e9d2baf2e~tplv-k3u1fbpfcp-zoom-in-crop-mark:3024:0:0:0.awebp)

实现步骤可以简单分为两步：

1. 绘制一个篮球。
2. 获取到用户点击坐标，使用属性动画让篮球滚动到该位置。

**绘制篮球**

首先说绘制篮球这一步，这一步不需要与用户进行交互，所以我们采用**自定义Drawable**来进行绘制。

如下所示：
```java
class BallDrawable : Drawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#D2691E")
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1f.px
        color = Color.BLACK
    }

    override fun draw(canvas: Canvas) {
        val radius = bounds.width().toFloat() / 2
        canvas.drawCircle(
            bounds.width().toFloat() / 2,
            bounds.height().toFloat() / 2,
            radius,
            paint
        )

        //the vertical line of the ball
        canvas.drawLine(
            bounds.width().toFloat() / 2,
            0f,
            bounds.width().toFloat() / 2,
            bounds.height().toFloat(),
            linePaint
        )
        //the transverse line of the ball
        canvas.drawLine(
            0f,
            bounds.height().toFloat() / 2,
            bounds.width().toFloat(),
            bounds.height().toFloat() / 2,
            linePaint
        )

        val path = Path()
        val sinValue = kotlin.math.sin(Math.toRadians(45.0)).toFloat()
        //left curve
        path.moveTo(radius - sinValue * radius,
            radius - sinValue * radius
        )
        path.cubicTo(radius - sinValue * radius,
            radius - sinValue * radius,
            radius,
            radius,
            radius - sinValue * radius,
            radius + sinValue * radius
        )
        //right curve
        path.moveTo(radius + sinValue * radius,
            radius - sinValue * radius
        )
        path.cubicTo(radius + sinValue * radius,
            radius - sinValue * radius,
            radius,
            radius,
            radius + sinValue * radius,
            radius + sinValue * radius
        )
        canvas.drawPath(path, linePaint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return when (paint.alpha) {
            0xff -> PixelFormat.OPAQUE
            0x00 -> PixelFormat.TRANSPARENT
            else -> PixelFormat.TRANSLUCENT
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}
```

**滚动**
绘制好篮球后，接着就是获取到用户的点击坐标，为了更好的举例，这里我放在自定义`View`中进行完成。
如下所示：
```java
class CustomBallMovingSiteView(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attributeSet, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    private lateinit var ballContainerIv: ImageView
    private val ballDrawable = BallDrawable()
    private val radius = 50

    private var rippleAlpha = 0
    private var rippleRadius = 10f

    private var rawTouchEventX = 0f
    private var rawTouchEventY = 0f
    private var touchEventX = 0f
    private var touchEventY = 0f
    private var lastTouchEventX = 0f
    private var lastTouchEventY = 0f

    private val ripplePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isDither = true
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 2f.px
        alpha = rippleAlpha
    }

    init {
        initView(context, attributeSet)
    }

    private fun initView(context: Context, attributeSet: AttributeSet?) {
        //generate a ball by dynamic
        ballContainerIv = ImageView(context).apply {
            layoutParams = LayoutParams(radius * 2, radius * 2).apply {
                gravity = Gravity.CENTER
            }

            setImageDrawable(ballDrawable)
            //setBackgroundColor(Color.BLUE)
        }

        addView(ballContainerIv)
        setWillNotDraw(false)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        lastTouchEventX = touchEventX
        lastTouchEventY = touchEventY

        event?.let {
            rawTouchEventX = it.x
            rawTouchEventY = it.y
            touchEventX = it.x - radius
            touchEventY = it.y - radius
        }

        ObjectAnimator.ofFloat(this, "rippleValue", 0f, 1f).apply {
            duration = 1000
            start()
        }

        val path = Path().apply {
            moveTo(lastTouchEventX, lastTouchEventY)
            quadTo(
                lastTouchEventX,
                lastTouchEventY,
                touchEventX,
                touchEventY
            )
        }

        val oaMoving = ObjectAnimator.ofFloat(ballContainerIv, "x", "y", path)
        val oaRotating = ObjectAnimator.ofFloat(ballContainerIv, "rotation", 0f, 360f)

        AnimatorSet().apply {
            duration = 1000
            playTogether(oaMoving, oaRotating)
            start()
        }

        return super.onTouchEvent(event)
    }

    fun setRippleValue(currentValue: Float) {
        rippleRadius = currentValue * radius
        rippleAlpha = ((1 - currentValue) * 255).toInt()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        ripplePaint.alpha = rippleAlpha
        //draw ripple for click event
        canvas?.drawCircle(rawTouchEventX, rawTouchEventY, rippleRadius, ripplePaint)
    }
}
```

简单概括一下：首先我们会动态的生成一个`View`，将其背景设置为我们刚刚绘制的`BallDrawable()`来构成一个篮球。然后通过`onTouchEvent()`方法来获取到用户的点击坐标，再通过属性动画，让球滚动到该坐标。