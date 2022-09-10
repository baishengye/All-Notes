package com.bo.a7_learnpreferences_3_learnsqlite

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import java.time.LocalDate

class DatabaseProvider : ContentProvider() {

    private val bookDir=0
    private val bookItem=1
    private val categoryDir=2
    private val categoryItem=3
    private val authority="com.bo.a7_learnpreferences_3_learnsqlite.provider"
    private var dbHelper: MyDatabaseHelper? =null

    //by lazy:懒加载,代码块里的代码一开始不会执行,只有当uriMatcher变量首次被调用的时候才会执行,并且把最后一行代码的返回值返回给uriMatcher
    private val uriMatcher by lazy {
        val matcher=UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(authority,"book",bookDir)
        matcher.addURI(authority,"book/#",bookItem)
        matcher.addURI(authority,"category",categoryDir)
        matcher.addURI(authority,"category/#",categoryItem)


        matcher
    }//Uri对象的getPathSegments()方法会把内容URI权限之后的部分以‘/’符号分割,第一段就是路径,第二段就是id了

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return dbHelper?.let {
            val db=it.writableDatabase
            val deleteRows=when(uriMatcher.match(uri)){
                bookDir->db.delete("Book",selection,selectionArgs)
                bookItem->{
                    val bookId=uri.pathSegments[1]
                    db.delete("Book","id=?", arrayOf(bookId))
                }
                categoryDir->db.delete("Category",selection,selectionArgs)
                categoryItem->{
                    val categoryId=uri.pathSegments[1]
                    db.delete("category","id=?", arrayOf(categoryId))
                }
                else->0
            }
            deleteRows
        }?:0
    }

    override fun getType(uri: Uri): String? {
        return when(uriMatcher.match(uri)){
            bookDir->"vnd.android.cursor.dir/vnd.com.bo.a7_learnpreferences_3_learnsqlite.provider.provider.book"
            bookItem->"vnd.android.cursor.item/vnd.com.bo.a7_learnpreferences_3_learnsqlite.provider.provider.book"
            bookDir->"vnd.android.cursor.dir/vnd.com.bo.a7_learnpreferences_3_learnsqlite.provider.provider.category"
            bookItem->"vnd.android.cursor.item/vnd.com.bo.a7_learnpreferences_3_learnsqlite.provider.provider.category"
            else->null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return dbHelper?.let {
            val db=it.writableDatabase
            val uriReturn=when(uriMatcher.match(uri)){
                bookDir,bookItem->{
                    val newBookId=db.insert("Book",null,values)
                    Uri.parse("content://$authority/book/$newBookId")
                }
                categoryDir,categoryItem->{
                    val newCategoryId=db.insert("Category",null,values)
                    Uri.parse("content://$authority/book/$newCategoryId")
                }
                else->null
            }
            uriReturn
        }
    }

    override fun onCreate(): Boolean {
        return context?.let {
            dbHelper=MyDatabaseHelper(it,"BookStore.db",2)
            true
        }?:false
    }

    override fun query(
        uri: Uri, projection: Array<String>? ,selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return dbHelper?.let {
            val db=it.readableDatabase
            val cursor=when(uriMatcher.match(uri)){
                bookDir->db.query("Book",projection,selection,selectionArgs,null,null,sortOrder)
                bookItem->{
                    val bookId=uri.pathSegments[1]
                    db.query("Book",projection,"id=?", arrayOf(bookId),null,null,sortOrder)
                }
                categoryDir->db.query("Category",projection,selection,selectionArgs,null,null,sortOrder)
                bookItem->{
                    val categoryId=uri.pathSegments[1]
                    db.query("Category",projection,"id=?", arrayOf(categoryId),null,null,sortOrder)
                }
                else->null
            }
            cursor
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?) =
        dbHelper?.let {
            // 更新数据
            val db = it.writableDatabase
            val updatedRows = when (uriMatcher.match(uri)) {
                bookDir -> db.update("Book", values, selection, selectionArgs)
                bookItem -> {
                    val bookId = uri.pathSegments[1]
                    db.update("Book", values, "id = ?", arrayOf(bookId))
                }
                categoryDir -> db.update("Category", values, selection, selectionArgs)
                categoryItem -> {
                    val categoryId = uri.pathSegments[1]
                    db.update("Category", values, "id = ?", arrayOf(categoryId))
                }
                else -> 0
            }
            updatedRows
        } ?: 0
}