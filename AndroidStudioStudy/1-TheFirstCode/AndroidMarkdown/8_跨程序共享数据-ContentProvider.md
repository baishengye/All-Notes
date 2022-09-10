---
title: ContentProvider
author: Bob
top: true
toc: true
mathjax: true
categories: Android学习
tags:
  - 第一行代码
  - ContentProvider
abbrlink: 
summary: 《第一行代码》的ContentProvider学习
date: 2022-02-12 00:00:00
---


# 跨程序共享数据-`ContentProvider`
如果程序与程序之间的数据不能共享的话，你就不能从抖音里看到你的联系人发了什么视频了，所以我们会采用安全可靠的`ContentPricider`技术

## `ContentPricider`
- 主要用于程序与程序之间的数据共享
- 提供了一套完整的机制，允许一个程序访问另一个程序中的数据
- 同时保证了被访问数据的安全性
- 是Android实现跨程序共享数据的标准方式
  
## 运行时权限
访问数据不可避免要涉及权限问题

### Android权限机制
当时我们在尝试静态广播的时候，为了监听开机广播，我们在`AndroidManifest.xml`中添加了一句权限声明
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.bo.a6_learnbroadcastreceiver">
    ···
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    ···
</manifest>
```
声明权限后用户的两个方面得到了保护:
- 如果用户在低于Android6.0的系统的设备上安装该程序,会在安装界面有提醒程序用到哪些权限，再决定是否安装
- 用户可以在应用程序管理界面查看任意一个程序的权限申请情况.

**比如微信会索要超级多的权限(即使有些我觉得会暴露我的隐私),但是不得不用，又会同意**
其实android团队想到了这个问题:
- 用户不需要在安装的时候同意所有权限，只需要用到这个功能的时候再同意，不同意就不用这个功能，但可以使用其他功能
  
**但是所有的权限都要用户来一个个设定，那太多了，用户体验极差**
所以权限分成了三类:
- 普通权限: 不会威胁到用户的安全和隐私，系统会自动授权这部分权限,比如:`BOOT_COMPLETED`就是普通权限
- 危险权限: 可能会威胁到用户的安全和隐私,比如:用户的地理位置,这就必须要用户自己授权了
- 特殊权限: 使用很少，暂且不提

**Android10.0为止所有的危险权限**
[![b5YK6H.png](https://s1.ax1x.com/2022/03/11/b5YK6H.png)](https://imgtu.com/i/b5YK6H)
表格中的每个危险权限都属于会属于一个权限组，但我们用户授权一个权限后，一整个权限组都会被授权
**但是不能按照权限组来设定逻辑，因为Android团队随时会改动权限组**

### 程序运行时申请权限
**示例-CALL_PHONE**
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/makeCall"
        android:text="打电话给10086"/>

</LinearLayout>
```
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
       ···
        makeCall.setOnClickListener {
            try {
                val intent=Intent(Intent.ACTION_CALL)
                //这个动作是打电话，打电话是需要申请权限的
                //Intent.ACTION_DIAL,是打开拨号界面，不需要声明权限
                intent.data= Uri.parse("tel:10086")
                startActivity(intent)
            }catch (e:SecurityException){
                e.printStackTrace()
            }
        }
    }
}
```
```xml
权限申请
<uses-permission android:name="android.permission.CALL_PHONE"/>
```
但是我们发现是会报错的,这是因为权限被禁止导致的，因为android6.0及以上系统使用危险权限时必须进行权限处理

```kotlin
可以修改成

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeCall.setOnClickListener {
            //ContextCompat.checkSelfPermission(上下文,权限名称)!=PackageManager.PERMISSION_GRANTED
            //就是不授权
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
                //如果没有授权
                //就请求授权ActivityCompat.requestPermissions(上下文, arrayOf(权限名称), 请求码)
            } else {
                call()
            }
        }
    }

    //ActivityCompat.requestPermissions()调用完了之后，在最终会回调到onRequestPermissionsResult()方法中
    //授权的结果会分装到 grantResults参数中
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                //如果用户没选择授权(比如点击back)，就会默认授权,或者直接授权,就会打电话
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call()
                } else {
                    //直接选择了拒绝授权
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}
```

## 访问其他程序中的数据
`ContentProvider`的用法一般有两种:
- 给现有的ContentProvider读取和操作相应程序中的数据
- 创建自己的ContentProvider，给程序的数据提供外部访问接口

如果一个程序通过ContentProvider对其数据提供了外部访问接口，那么任何其他的应用程序都可以对这部分数据进行访问。比如通讯录，短信等等。

### `ContentProvider`的基本用法
- 对于一个应用程序来说，如果想要访问ContentProvider中共享的数据，就一定要借助`ContentResolver`类，可以通过`Context`中的getContentResolver()方法获取该类的实例
- `ContentResolver`中提供了一系列方法对数据增删改查
  - `insert()`增
  - `delete()`删
  - `update()`改
  - `query()`查
- `ContentResolver`中的增删改查都是不接受表参数的，而是用`Uri`参数替代
  - `Uri`参数被称为内容Uri
  - 内容`Uri`为ContentProvider中的数据建立了一个唯一的标识符，他主要有两个部分组成
    - `authority`是用于对不同的应用程序做区分的，一般为了避免冲突，会采用应用包名的方式命名，比如某个应用的包名是`com.bo.app`，那么该应用对应的`authority`就可以命名成`com.bo.app.provider`
    - `path`是对于同一个应用程序中不同的表做区分的，通常添加在`authority`后面,比如数据库中有两张表`table1`和`table2`,那么内容Uri就是`com.bo.app.provider.table1`和`com.bo.app.provider.table2`
  - 在得到内容`Uri`后我们需要将其解析成`Uri`对象才能使用
  - ```kotlin
    val cursor=contentReslover.query(
        uri,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )
    ```
    ![bIaLhq.png](https://s1.ax1x.com/2022/03/11/bIaLhq.png)

**增删改查**
- 查:在`query()`查询完了之后返回的是一个`Cursor`对象(似曾`SQLite`相识),从`Cursor`对象读取出数据来
  ```kotlin
    while(cursor.moveToNext()){
        val column1=cursor.getString(cursor.getColumnIndex("column1"))
        val column2=cursor.getString(cursor.getColumnIndex("column2"))
    }
    cursor.close()
  ```
- 增:
  ```kotlin
    val values=contentValuesOf("column1" to "text","column2" to 1)
    contentProvider.insert(uri,values)//(要往那里添加,添加啥)
  ```
- 改:
  ```kotlin
    val values=contentValuesOf("column2" to "")//找到第一列把他清空
    contentProvider.update(uri,values,"column1=? and column2=?",arrayOf("text","1"))
  ```
- 删
  ```kotlin
    contentProvider.delete(uri,"column1 = ?",arrayOf("text"))
  ```

### 实践出真知-读取系统联系人
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/contactsView"/>
</LinearLayout>
```
```kotlin
class MainActivity : AppCompatActivity() {
    private val contactsList=ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>//适配器

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //适配器:这里用了一个android内置的一个list布局
        adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,contactsList)
        contactsView.adapter=adapter
        //READ_CONTACTS读取联系人的权限
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS),1)
        }else{
            loadContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1->{
                if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    loadContacts()
                }else{
                    Toast.makeText(this,"你拒绝了权限申请",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun loadContacts(){
        //这个地方的contentResolver其实就是this.getContentResolver()
        //首先这个函数在类里调用，所以就天然的有一个上下文Context就是this,然后getContentResolver()被kotlin编译器优化成contentResolver
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,null,null,null)?.apply {
            while (moveToNext()){
                val name=getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNum=getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add("${name}\n${phoneNum}")
            }
            adapter.notifyDataSetChanged()//刷新ListView
            close()//用完就关
        }

    }
}
```
```xml
//添加权限
<uses-permission android:name="android.permission.READ_CONTACTS"/>
```

## 创建自己的`ContentPrivider`
致命3连问:
- 哪些提供外部访问接口的应用程序都是如何实现这种功能?
- 如何保证数据安全?
- 隐私数据会不会泄露?

### 创建`ContentProvider`的步骤
需要新建一个类去继承`ContentProvider`去实现才能实现程序共享数据的功能
```kotlin
class MyProvider: ContentProvider(){
    override fun onCreate(): Boolean {
        TODO("Not yet implemented")
    }
    
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }
    
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(rui: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }
}
```
##### 有6个方法全部都要重写
- `onCreate():Boolean`:初始化`ContentProvider`的时候调用,通常在这里完成对数据库的创建和升级等操作,返回`true`表示`ContentProvider`初始化成功
-  ```kotlin
    query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor   查询
   ```
    - `uri`:对应的表路径
    - `projection`: 查询哪些列
    - `selection`和`selectionArgs`:约束查询那些行
    - `sortOrde
    - 返回值是`Cursor`类型，查询的数据存在里面
- ```kotlin
    update(
        uri: Uri, 
        values: ContentValues?, 
        selection: String?, 
        selectionArgs: Array<out String>?
    ): Int 
  ```
    - 返回值是Int，表示受影响的行数
    - 参数含义见query
- ```kotlin
  delete(
      uri: Uri, 
      selection: String?, 
      selectionArgs: Array<out String>?
      ): Int 删除
  ```
  - 返回值是Int，表示受影响的行数
  - 参数含义见query
- ```kotlin
  insert(
      uri: Uri, 
      values: ContentValues?
      ): Uri? 添加
  ```
  - 返回值是Uri类型，表示这条新纪录
  - 参数含义见query
- getType(uri: Uri): String?根据传入的内容Uri返回对应的MIME类型(多用途互联网邮件扩展类型：要善用百度，不懂就查)

##### Uri的用法
- 标准的内容Uri写法:`content://com.bo.app.provider/table1`
  这个是表明我们想要访问的是`com.bo.app`应用的`table1`表的数据

- 标准的内容Uri写法:`content://com.bo.app.provider/table/1`
  这个是表明我们想要访问的是`com.bo.app`应用的`table1`表中`id`是1的那行的数据

- 内容URI的格式主要只有两种
  - 以路径结尾就是查询对应表所有的数据
  - 以`id`结尾就是查询表中相应`id`的行的数据
- 通配符来匹配两种格式
  - `*`:匹配任意长度的任意字符.比如匹配任意表: `content://com.bo.app.provider/*`
  - `#`:匹配任意长度的数字.比如`table`表中任意一行:`content://com.bo.app.provider/table/#`

**我们再借助`UriMatcher这个类就可以轻松实现匹配内容URI的功能`**
`UriMatcher`中提供了一个addURI(authority,path,自定义的代码)方法
调用`UriMatcher`的`match()`方法时就可以将一个Uri对象传入,返回值是某个能够匹配这个`Uri`对象所对应的自定义代码，利用这个代码，我们就可以判断出调用方期望访问的是那张表中的数据了.
*讲的不太通俗，代码看看*
```kotlin
class MyProvider: ContentProvider(){

    private val table1Dir=0//这个表示访问table1中所有的数据
    private val table1Item=1//表示访问table1中的单条数据
    private val table2Dir=2//这个表示访问table2中所有的数据
    private val table2Item=3//表示访问table2中的单条数据

    private val uriMatcher=UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI("com.bo.app.provider","table1",table1Dir)
        uriMatcher.addURI("com.bo.app.provider","table1/#",table1Item)
        uriMatcher.addURI("com.bo.app.provider","table2",table2Dir)
        uriMatcher.addURI("com.bo.app.provider","table2/#",table2Item)
    }
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        when(uriMatcher.match(uri)){//这样就可以通过uriMatcher.match(uri)知道调用方期望查询到那个数据了
            table1Dir->{
                //这个表示访问table1中所有的数据
            }
            table1Item->{
                //表示访问table1中的单条数据
            }
            table2Dir->{
                //这个表示访问table2中所有的数据
            }
            table2Item->{
                //表示访问table2中的单条数据
            }
        }
        return null
    }
    ···
}
```
其他几个方法同理

##### `getType()`方法
他是所有`ContentProvider`都必须要提供的一个方法,用于获取`Uri`对象的MIME类型,一个内容`URI`所对应的`MIME`字符串主要由三部分组成
- 必须以`vnd`开头
- 两种情况
  - 如果内容URI以路径结尾，则后接`android.cursor.dir/`;
  - 如果内容URI以id结尾,则后接`android.cursor.item/`;
- 最后接上`vnd.<authority>.<path>`

**比如**
- `content://com.bo.app.provider/table1`对应的MIME类型就是`vnd.android.cursor.dir/vnd..com.bo.app.provider.table1`
- `content://com.bo.app.provider/table/1`对应的MIME类型就是`vnd.android.cursor.item/vnd.com.bo.app.provider.table1`
**现在可以来实现一下`getType()`中的逻辑了**
```kotlin
class MyProvider: ContentProvider(){
    ···
    override fun getType(uri: Uri): String? {
        return when(uriMatcher.match(uri)){
            table1Dir->"vnd.android.cursor.dir/vnd..com.bo.app.provider.table1"
            table1Item->"vnd.android.cursor.item/vnd.com.bo.app.provider.table1"
            table2Dir->"vnd.android.cursor.dir/vnd..com.bo.app.provider.table1"
            table2Item->"vnd.android.cursor.item/vnd.com.bo.app.provider.table2"
            else -> null
        }
    }
}
```

## 实现跨程序数据共享
在上一章持久化的SQLite中训练
```kotlin
首先womenxuyaobaMyDatabaseHelper中的Toast去掉，因为跨程序访问时我们是不能使用的Toast.
```
**详情见项目(哭了，只有添加数据和查看数据成功了，更新和删除都失败了)**
**想看看成功的就去看看作者的代码吧，呜呜呜，我实在是找不出来错误了**
**十年后------------我找到错误了，原来是更新和删除的id获取错了，我真笨**