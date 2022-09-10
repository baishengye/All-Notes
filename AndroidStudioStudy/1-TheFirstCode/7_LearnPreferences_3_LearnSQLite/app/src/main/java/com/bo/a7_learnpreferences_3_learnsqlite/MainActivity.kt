package com.bo.a7_learnpreferences_3_learnsqlite

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os
import android.system.Os.close
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    var bookId:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val p by later {
            Log.d("MainActivity","run code later block")
            "test later"
        }


        queryData.setOnClickListener {
            val uri=Uri.parse("content://com.bo.a7_learnpreferences_3_learnsqlite.provider/book")
            contentResolver.query(uri,null,null,null,null)?.apply {
                while (moveToNext()){
                    val name=getString(getColumnIndex("name"))
                    val author=getString(getColumnIndex("author"))
                    val pages=getInt(getColumnIndex("pages"))
                    val prices=getDouble(getColumnIndex("price"))
                    Log.d("MainActivity","bookId是 $bookId , $name 是 $author 的书,有 $pages 页, 售 $prices 元")
                }
                close()
            }
        }

        deleteData.setOnClickListener {
            bookId?.let {
                val uri=Uri.parse("content://com.bo.a7_learnpreferences_3_learnsqlite.provider/book/$it")
                contentResolver.delete(uri,"name=?", arrayOf("A Clash of Kings"))
            }
        }

        upDateData.setOnClickListener {
            bookId?.let {
                val uri=Uri.parse("content://com.bo.a7_learnpreferences_3_learnsqlite.provider/book/$it")
                val values= contentValuesOf("name" to "A Clash of Kings","pages" to 250,"price" to 24)
                contentResolver.update(uri,values,"name=?", arrayOf("A Clash of Kings"))
            }
        }

        addData.setOnClickListener {
            val uri= Uri.parse("content://com.bo.a7_learnpreferences_3_learnsqlite.provider/book")
            val values= contentValuesOf("name" to "A Clash of Kings","author" to "GM","pages" to 200,"price" to 20)
            val newUri=contentResolver.insert(uri,values)
            bookId=newUri?.pathSegments?.get(1)
        }
    }
}



/*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper=MyDatabaseHelper(this,"BookStore.db",1)
        createDatabase.setOnClickListener {
            dbHelper.writableDatabase

        }

        replaceData.setOnClickListener {
            val db=dbHelper.writableDatabase
            db.beginTransaction()//开启事务

            try {
                db.delete("Book",null,null)
                //throw NullPointerException()
                val values=ContentValues().apply{
                    put("name","龙族")
                    put("author","江南")
                    put("pages",543)
                    put("price",29.53)
                }
                db.insert("Book",null,values)
                db.setTransactionSuccessful()//事务已经成功执行
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                db.endTransaction()//结束事务
            }
        }

        queryData.setOnClickListener {
            val db=dbHelper.writableDatabase
            val cursor=db.query("Book",null,null,null,null,null,null,)
            if(cursor.moveToFirst()){
                do {
                    //遍历Cursor对象,读出数据打印
                    val name=cursor.getString(cursor.getColumnIndex("name"))
                    val author=cursor.getString(cursor.getColumnIndex("author"))
                    val pages=cursor.getString(cursor.getColumnIndex("pages"))
                    val price=cursor.getString(cursor.getColumnIndex("price"))

                    Log.d("MainActivity","${name}的作者是${author},共计${pages}页,${price}元")
                }while (cursor.moveToNext())
            }
            cursor.close()
        }

        deleteData.setOnClickListener {
            val db=dbHelper.writableDatabase
            db.delete("Book","name = ?", arrayOf("剑来"))
        }

        upDateData.setOnClickListener {
            val db=dbHelper.writableDatabase
            val values=ContentValues()
            //构建一个ContentValues对象,里面存储的是键值对
            values.put("price",199)
            values.put("name","剑来")
            values.put("author","陈太监")
            //我们添加了一个键值对(price:199)
            db.update("Book",values,"name = ? and author = ?", arrayOf("陈太监","pig"))
            //这个update()方法相当于SQL语句:
            // UPDATE `Book` SET `price` = 199 WHERE `name` = '剑来'
            //arrayOf()是创建一个数组，where和数组中元素一样的就更改
        }

        addData.setOnClickListener {
            val db=dbHelper.writableDatabase
            val values1=ContentValues().apply{
                //组装第一条数据
                put("name","陈太监")
                put("author","pig")
                put("pages",454)
                put("price",30.12)
            }
            db.insert("Book",null,values1)

            val values2=ContentValues().apply{
                //组装第一条数据
                put("name","三体")
                put("author","刘慈欣")
                put("pages",600)
                put("price",100)
            }
            db.insert("Book",null,values2)
            */
/*Toast.makeText(this,"点击了添加数据",Toast.LENGTH_LONG).show()*//*

        }
    }
}*/
