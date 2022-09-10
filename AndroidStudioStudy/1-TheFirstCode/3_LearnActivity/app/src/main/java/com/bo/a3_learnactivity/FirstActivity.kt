package com.bo.a3_learnactivity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.first_layout.*
import kotlinx.android.synthetic.main.first_layout.button1



class FirstActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //就需要去到Activity中的onCreate取引入这个布局
        setContentView(R.layout.first_layout)//在界面中引入这种布局
        //        在这里我们是直接引入的一个布局，但是我们一般会传入布局文件的id
        //        流程是:
        //        在项目中添加的资源都会在R文件中生成一个相应的id
        //        比如这里调用R.layout.first_layout就得到了first_layout.xml的id，然后就把id传到了setContentView()中

//        Toast是Android中提供的一种提醒方式,程序中可以使用他将一些短小的信息通知给用户，这些信息会在一段时间后自动消失,和js中的alert()效果一样
//        需要定义一个弹出Toast的触发点,可以把按钮当作触发点
//        var button1: Button = findViewById(R.id.button1)//R.id.button1获取到button1的id
//        button1.setOnClickListener{
//            Toast.makeText(this,"你点击了按钮",Toast.LENGTH_SHORT).show()
//        }
//        findViewById()方法获取到对应id的泛型对象(继承自View)，所以kotlin是无法推断出他返回的到底是什么,所以需要显式定义
//        setOnClickListener()函数为按钮注册了一个监听器,监听到点击按钮，会执行监听器中的OnClick方法
//        makeText(context,text,时长)是一个静态方法，创建一个Toast，但是这需要三个参数
//        context: Toast需要的上下文，Activity本身是一个Context对象,所以此处传入this
//        text:文本内容
//        时长:Toast.LENGTH_SHORT(短时间),Toast.LENGTH_LONG(长时间)

//        关于findViewById()函数
//                findViewById()作用是获取布局文件中控件的实例，本次中只有1个，那如果有10个呢，那就需要10个findViewById()
//                所以有了ButterKnife之类的第三方库
//                但是我们引入过插件kotlin-android-extensions,这个插件会根据布局文件自动生成一个具有相同名称的变量，我们就可以直接在Activity中使用这个变量
//                所以以上代码可简化
//        button1.setOnClickListener{
//            Toast.makeText(this,"你点击了按钮",Toast.LENGTH_SHORT).show()
//        }

        //代码销毁Activity
        //finish()函数用来销毁Activity
//        button1.setOnClickListener{finish()}


//        button1.setOnClickListener{
//            var intent= Intent(this,SecondActivity::class.java)
//            startActivity(intent)
//        }
        //点击button1就会从FirstActivity中打开SecondActivity


//        button1.setOnClickListener{
//            var intent= Intent("com.bo.a3_learnactivity.ACTION_START")//action
//            //intent.addCategory("android.intent.category.DEFAULT")//category:其实"android.intent.category.DEFAULT"的可以不用写，因为Intent中默认的category就是这个
//            startActivity(intent)//这个函数调用的时候把默认的cagegory添加到intent中
//        }
        //这里Intent使用了另一种构造方法

//        button1.setOnClickListener{
//            var intent= Intent("com.bo.a3_learnactivity.ACTION_START")
//            intent.addCategory("com.bo.a3_learnactivity.MY_CATEGORY")
//            //这里intent除了默认的category还有我们自定义的category，我们添加了一个自定义的category，就要在AndroidManifest.xml中加上，不然响应不了
//            startActivity(intent)
//        }

//        button1.setOnClickListener{
//            var intent= Intent(Intent.ACTION_VIEW)
//            intent.data=Uri.parse("https://www.baidu.com")
//            //这里intent除了默认的category还有我们自定义的category，我们添加了一个自定义的category，就要在AndroidManifest.xml中加上，不然响应不了
//            startActivity(intent)
//        }
        //首先指明的Action是Intent.ACTION_VIEW,这是Android系统中的一个内置动作,其常量值是android.intent.action_VIEW.
        //然后通过Uri.parse()将一个网址字符串解析成Uri对象
        //再调用Intent的setData()方法将这个Uri对象传递出去，给到intent里面


//        button1.setOnClickListener{
//            var intent= Intent(Intent.ACTION_VIEW)
//            intent.data=Uri.parse("http://www.bilibili.com")
//            //这里intent除了默认的category还有我们自定义的category，我们添加了一个自定义的category，就要在AndroidManifest.xml中加上，不然响应不了
//            startActivity(intent)
//        }

        /*button1.setOnClickListener{
            var intent=Intent(Intent.ACTION_DIAL)//android系统内置动作
            intent.data=Uri.parse("tel:10086")//指明了协议是tel，号码时10086
            startActivity(intent)
        }*/

        /*button1.setOnClickListener {
            var intent=Intent(this,SecondActivity::class.java)
            intent.putExtra("extra_data","Hello SecondActivity")//添加数据到intent中
            startActivity(intent)
        }*/

        /*button1.setOnClickListener{
            var intent=Intent(this,SecondActivity::class.java)//上下文,响应Activity
            startActivityForResult(intent,1)//请求码只要是一个唯一值就行了，所以这里传的1
        }*/

        /*Log.d("FirstActivity",this.toString())
        button1.setOnClickListener {
            var intent=Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }*/

        /*Log.d("FirstActivity","Task id is $taskId")*/
        //打印当前返回栈的id，taskId实际上是调用的父类的getTaskId()方法
        /*button1.setOnClickListener {
            var intent=Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }*/

        /*假如SecondActivity需要用到两个重要的参数,在启动SecondActivity的时候就要用到
        那么我们会写*/
        /*button1.setOnClickListener {
            var intent=Intent(this,SecondActivity::class.java)
            intent.putExtra("param1","data1")
            intent.putExtra("param2","data2")
            startActivity(intent)
        }*/
        /*但是当我们和同事的工作分离是,同事所编写的是SecondActivity，我所编写的是FirstActivity
        但是我不知道SecondActivity要什么才能启动，但是如果同事写的SecondActivity中有一个类似静态方法封装了
        intent产生和添加信息，我就只需要调用这个方法，传参进入就行了
        */
        button1.setOnClickListener {
            SecondActivity.actionStart(this,"data1","data2")
        }
    }


    override fun onRestart() {
        super.onRestart()
        Log.d("FirstActivity","onRestart")
    }

    //由于startActivityForResult()方法来启动SecondActivity
    //所以SecondActivity被销毁之后会回调上一个Activity的onActivityResult()，所以在此需要重写FirstActivity的onActivityResult()方法
    //SecondActivity返回的数据在onActivityResult()中接收到
    /*onActivityResult有三个参数:
        requestCode: 启动Activity时传入的请求码
        resultCode: 我们返回数据时的处理结构:成功还是失败或者其他
        data: 携带数据的intent
    由于一个Activity可能会调用startActivityForResult()去启动很多个不同的Activity
    每个Activity返回的数据都会自动回调到onActivityResult()中，
    所以我们需要判断requestCode知道这个数据是从哪个Activit返回的
    然后再通过resultCode判断是否成功处理数据
    再对数据进行操作*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1->if(resultCode == RESULT_OK){
                var returnedData=data?.getStringExtra("return_data")
                Log.d("FirstActivity","$returnedData")
            }
        }
    }

    //想要创建Menu就要重写onCreateOptionsMenu函数
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        /*menuInflater在这儿里其实是调用了父类的getMenuInflater()方法,
        getMenuInflater()会得到一个menuInflater对象,在调用它的inflate(),就创建了一个菜单
        inflate()有两个参数:
            R.menu.main:指明要传入的资源文件，根据这个文件来创建菜单
            main:指明我们的菜单项将添加到哪个Menu对象中,这里直接使用onCreateOptionsMenu()传来的Menu
            return:false(不能显示),true:(可以显示)*/
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){//item.itemId获取Menu中Item的id，
            R.id.add_item->Toast.makeText(this,"add",Toast.LENGTH_SHORT).show()
            R.id.remove_item->Toast.makeText(this,"remove",Toast.LENGTH_SHORT).show()
        }
        //Menu和里面的菜单项都不需要用代码显式的去监听,Menu中的菜单项已经自动被监听了
        return true
    }
}