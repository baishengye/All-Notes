package com.bo.a2_kotlinlearning

import kotlin.concurrent.thread

/*class MyThread:Thread(){//继承一个线程类来使用,但是这种方式耦合度太高了
    override fun run() {
        super.run()
        println("run MyThread by Thread")
    }
}
fun main(){
    MyThread.start()
*/

//一般会采用实现接口
/*
class MyThread:Runnable{
    override fun run() {
        println("run MyThread by Runnable")
    }
}
*/

fun main(){
/*val myThread=MyThread()
    Thread(myThread).start()*/


//如果不想专门写一个类去实现Runnable度的话可以使用lambda的方式

    Thread{
            println("run MyThread by Runnable")
    }.start()
}

/*fun main(){
    thread {
        println("run MyThread by Runnable")
    }
}*/
