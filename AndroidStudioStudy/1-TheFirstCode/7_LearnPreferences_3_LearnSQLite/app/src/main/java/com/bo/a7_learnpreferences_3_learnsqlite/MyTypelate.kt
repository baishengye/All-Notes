package com.bo.a7_learnpreferences_3_learnsqlite

/*
class MyTypelate<T> {
    fun method(p0:T):T{
        return p0
    }
}

fun main(){
    val myTypelate=MyTypelate<Int>()
    println(myTypelate.method(123))
}*/
/*class MyTypelate{
    fun<T:Number> method(p0:T):T{
        return p0
    }
}
fun main(){
    val myTypelate=MyTypelate()
    println(myTypelate.method<Int>(123))
}*/

fun <T> T.build(block:T.()->Unit):T{
    block()
    return this
}
fun main(){}



