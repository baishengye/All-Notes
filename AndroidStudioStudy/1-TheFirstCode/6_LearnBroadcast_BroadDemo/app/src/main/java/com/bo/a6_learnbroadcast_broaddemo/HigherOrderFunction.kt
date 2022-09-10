package com.bo.a6_learnbroadcast_broaddemo

import java.lang.StringBuilder

/*inline fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
    return operation(num1, num2)
}

fun plus(num1: Int,num2: Int):Int{
    return num1+num2
}

fun minus(num1: Int,num2: Int):Int{
    return num1-num2
}*/

/*fun main(){
    val num1=100
    val num2=200
    *//*println("num1+num2=${num1AndNum2(num1,num2,::plus)}")
    println("num1-num2=${num1AndNum2(num1,num2,::minus)}")*//*
    *//*比如:
        ::plus是一种函数引用式的写法,表示将plus()作为参数传递给num1AndNum2()函数
    nums1AndNum2()函数中使用了传入的函数类型参数来决定具体的运算逻辑*//*

    println("num1+num2=${num1AndNum2(num1,num2){n1,n2->n1+n2}}")
    println("num1-num2=${num1AndNum2(num1,num2){n1,n2->n1-n2}}")
}*/


/*定义了一个StringBuilder的扩展函数build()
这个函数接收一个函数类型的声明，并且返回值也是StringBuilder*/
/*fun StringBuilder.build(block:StringBuilder.()->Unit):StringBuilder{
    block()
    *//*block可以理解成就是那个函数类型参数的参数名*//*
    return this
}

fun main(){
    val list= listOf<String>("我","爱","吃","苹果")
    val result=StringBuilder().build {
        for (element in list)
            append(element)
        toString()
    }
    *//*可以看到build函数的效果和apply()很像，只不过build()只能作用于StringBuilder,而apply可以作用于所有类
    这涉及泛型*//*
    println(result)
}*/




inline fun printString(str:String,block:(String)->Unit){
    println("开始打印")
    block(str)
    println("结束打印")
}

fun main(){
    println("主函数开始")
    var str=""
    printString(str){s->
        println("开始lambda表达式")
        if(s.isEmpty()) return
        println(s)
        println("lambda表达式结束")
    }
    println("主函数结束")
}

