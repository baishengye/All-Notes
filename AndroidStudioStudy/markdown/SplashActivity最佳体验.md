```xml
<!--启动时的背景,此时真正的Activity的布局还没加载-->
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:opacity="opaque">
    <item android:drawable="@android:color/background_dark"/>
    <item android:drawable="@drawable/logo"
        android:width="@dimen/logo_wight"
        android:height="@dimen/logo_wight"
        android:gravity="center_horizontal"
        android:top="190dp"/>
</layer-list>
```

```xml
<!--SplashActivity的Theme-->
<style name="SuperUI.NoActionBar.Launcher" parent="SuperUI.NoActionBar">
        <item name="android:windowBackground">@drawable/launch_screen</item>
        <item name="android:windowFullscreen">true</item><!--全屏-->
    </style>
```

```xml
<!--把Theme设置到Activity上-->
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    ···>

    ···

    <application
        ···>
        <activity
            ··
            android:theme="@style/SuperUI.NoActionBar.Launcher">
            ···s
        </activity>
    </application>

</manifest>
```