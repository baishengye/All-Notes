```java
val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION   //隐藏导航栏
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY        //全屏
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  //手势呼出导航栏
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = uiOptions
```

```xml
//去除标题栏
<style name="SuperUI.NoActionBar" parent="Theme.MaterialComponents.DayNight.NoActionBar.Bridge">
        ···
</style>


//并且设置给app或者activity
<application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/SuperUI.NoActionBar"
        tools:targetApi="31">
        ···
    </application>
```