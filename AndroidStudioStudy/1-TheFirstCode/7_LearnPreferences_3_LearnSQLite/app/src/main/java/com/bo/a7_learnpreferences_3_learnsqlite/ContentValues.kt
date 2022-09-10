package com.bo.a7_learnpreferences_3_learnsqlite

import android.content.ContentValues

/*vararg是声明一个可变参数列表,说明pairs中可能有1...n个Pair*/
fun cv0f(vararg pairs:Pair<String,Any?>)=ContentValues().apply {//apply函数的返回值就是他的调用对象本身，所以可以用=替代返回
    for (pair in pairs){
        val key=pair.first
        when(val value=pair.second){
            is Int->put(key, value)//由于kotlin的smart cast功能，当Any进入这个分支后就会自动转型成Int
            is Long->put(key, value)
            is Short->put(key, value)
            is String->put(key, value)
            is Float->put(key, value)
            is Double->put(key, value)
            is Boolean->put(key, value)
            is Byte->put(key, value)
            is ByteArray->put(key, value)
            null->putNull(key)
        }
    }
}