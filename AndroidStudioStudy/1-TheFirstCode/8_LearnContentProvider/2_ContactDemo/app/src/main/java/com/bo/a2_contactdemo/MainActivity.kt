package com.bo.a2_contactdemo

import android.Manifest
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.ContactsContract
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

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

    override fun onCreate(): Boolean {
        TODO("Not yet implemented")
    }



    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        return when(uriMatcher.match(uri)){
            table1Dir->"vnd.android.cursor.dir/vnd..com.bo.app.provider.table1"
            table1Item->"vnd.android.cursor.item/vnd.com.bo.app.provider.table1"
            table2Dir->"vnd.android.cursor.dir/vnd..com.bo.app.provider.table1"
            table2Item->"vnd.android.cursor.item/vnd.com.bo.app.provider.table2"
            else -> null
        }
    }

    override fun insert(rui: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }
}