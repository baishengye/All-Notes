package com.bo.a7_learnpreferences_3_learnsqlite

import kotlin.reflect.KProperty

class Later<T>(private val block: ()->T) {
    var value:Any?=null

    operator fun getValue(any:Any?,prop:KProperty<*>):T{
        if(value==null){
            //如果value是空的，就会把值进行缓存
            value=block
        }

        return value as T//返回值
    }
}

//把Later类被顶层方法调用，那么所有类就都可以调用他了
fun <T> later(block: () -> T) = Later(block)


