```xml
在AndroidManifest.xml中
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.xiaoyingbo.neteasecloudmusic">

    <uses-sdk tools:overrideLibrary="com.qmuiteam.qmui"/>

    ...
</manifest>
```
```java
//然后在使用的时候需要进行版本判断
if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
    QMUIStatusBarHelper.translucent(this@SplashActivity)
    ...
}
```