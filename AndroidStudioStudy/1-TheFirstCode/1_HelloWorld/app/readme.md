## 文件夹的含义

### 1. app:代码资源存放,开发目录
1. build 与外层的build功能相似，包含编译时自动生成的文件
2. libs 存放项目中使用的第三方jar包,并且这个目录下的jar包会自动添加到项目的构建路径里
3. src.androidTest 存放Android Test测试用例，对项目进行自动化测试
4. src.java 所有java和kotlin代码存放处
5. src.res 所有图片，字符，布局等资源存放在此处
   (1). drawable 存放图片，后缀表示图片的分辨率,最常用的是-xxhdpi文件夹(其他还有-xhdpi,-hdpi),需要自己创建.
   (2). layout 存放布局
   (3). values 存放字符串
      详细的看vaules文件夹下的文件中的注释
   (4). mipmap开头的文件夹是存放app使用的图标的，为了兼容各种系统，所以mipmap文件夹会比较多
6. AndroidManifest.xml 整个Android项目的配置文件,在程序中定义的四大件都需要在这个文件中注册,还可以哥应用程序添加权限声明
7. test 用来编写Unit Test测试用例,是对项目进行自动化的另一种方式
8. .gitignore 将app模块内指定目录或文件排除在版本控制之外
9. app.iml idea标识文件
10. build.gradle app模块的gradle构建脚本,指出很多项目构建相关的配置
    详解见文件注释
11. proguard-rules.pro 对app的加密文件，防止破解

### 2. build:存放编译自动生成的文件
### 3. gradle:包含gradle wrapper的配置文件,默认联网使用
### 4. .gitignore:git版本控制,git操控
### 5. build.gradle:项目全局的gradle构建脚本
   详解见文件注释
### 6. gradle.properties: 全局的gradle配置文件
### 7. gradlew和gradlew.bat:gradlew是在linux或者mac中使用的,gradlew.bat是在windows下使用的
### 8. .iml:自动生成的项目识别文件，标识这是一个idea项目
### 9. local.properties: 指定本机中Android SDK的路径
### 10. setting.gradle: 用于指定项目中所有引入的模块
- 注意:其实我们大部分操作都在app文件夹下实施


## 5种日志打印
- Log.v(): 用于打印那些最为琐碎的、意义最小的日志信息。对应级别verbose，是Android日志里面级别最低的一种。
- Log.d(): 用于打印一些调试信息，这些信息对你调试程序和分析问题应该是有帮助的。对应级别debug，比verbose高一级。
- Log.i(): 用于打印一些比较重要的数据，这些数据应该是你非常想看到的、可以帮你分析用户行为数据。对应级别info，比debug高一级。
- Log.w(): 用于打印一些警告信息，提示程序在这个地方可能会有潜在的风险，最好去修复一下这些出现警告的地方。对应级别warn，比info高一级。
- Log.e(): 用于打印程序中的错误信息，比如程序进入到了catch语句当中。当有错误信息打印出来的时候，一般都代表你的程序出现严重问题了，必须尽快修复。对应级别error，比warn高一级。
- 注: 为什么不用system.out.print()而用Log呢?(Log搭配Logcat超强,详情使用见代码文件)
  system.out.print()缺点:
      1. 日志开关不可控
      2. 不能添加日志标签
      3. 日志不能分级
  