package com.bo.a7_learnpreferences_3_learnsqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(private val context: Context, private val name:String, private val version:Int):
    SQLiteOpenHelper(context,name,null,version) {

    private val createBook=
            "create table Book ("+//创建Book表
            "id integer primary key autoincrement,"+//设置一个自增id作为主键
            "author text,"+//文本类型
            "price real,"+//浮点型
            "pages integer,"+//整型
            "name text,"+//blob是二进制类型
            "category_id integer)"


    private val createCategory=""+
        "create table Category(" +
            "id integer primary key autoincrement,"+
            "category_name text,"+
            "category_code integer)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createBook)//执行SQL语句
        db.execSQL(createCategory)
        /*Toast.makeText(context,"表创建成功",Toast.LENGTH_LONG).show()*/
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion<=1)//现在版本是2了安装过这个程序，数据库版本还在1的，要升级
            db.execSQL(createCategory)

        if(oldVersion<=2)//现在版本是3了安装过这个程序，数据库版本还在2以下的，要升级
            db.execSQL("alter table Book add column category_id integer")
    }
}