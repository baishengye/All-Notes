package com.bo.a2_kotlinlearning

import kotlinx.coroutines.*

/*fun main(){
        GlobalScope.launch {
            //GlobalScope.launch函数可以创建一个协程的作用域，这样lambda表达式就是在协程中运行的了
            //但是你会发现点击运行输出根本没有在控制台打印出来
            //这是因为GlobalScope.launch函数每次创建的都是一个顶层协程
            //这种携程当应用程序运行结束的时候会跟着一起结束
            //日志无法打印出来就是因为代码块中的代码还没来得及运行程序就结束了
            println("程序在协程里面执行")

            //delay()函数可以让当前的协程延迟指定时间再执行
            //delay()和sleep()不相同
            //delay()是非阻塞式的挂起函数，他只会挂起当前的协程，并不会影响其他的协程运行
            //Thread.sleep()是阻塞整个线程，这样运行再该线程下的所有协程都会被中断
            //delay()函数只能再当前作用域或者其他挂起函数中调用
            delay(1500)
            //会发现下面的这行日志没有被打印,因为上面打印完了就被挂起了1500，但是线程只会被阻塞1000
            //所以还没运行这行代码线程就结束了，协程也就被强制中断了
            println("程序运行完毕")
    }


    //想要协程中的代码运行完，就大概把协程阻塞一段时间，确保协程运行完了就行
    Thread.sleep(1000)

    //但是这样写只是让线程沉睡了1秒钟，要是协程在这一秒钟里面没有运行完，也会被强制中断
}*/

/*fun main(){
    runBlocking{
        //runBlocking也会创建一个协程的作用域
        //但是他能保证协程作用域内的所有代码和子协程没有全部执行完的时候就会一直阻塞当前线程
        //但是他一般只在测试的时候用，因为它会引起性能上的问题
        println("程序在协程里面执行")

        delay(1500)

        println("程序运行完毕")
    }
}*/
/*suspend fun printWord(){
    println("0")
    delay(1000)
}*/

/*
fun main(){
    val start=System.currentTimeMillis()
    runBlocking{
        for (i in 0..100000) {//创建10个子协程
            launch { //launch必须要在协程的作用域中才能被调用
                println("$i start")//而且创建的子协程受外层作用域影响，如果外层作用域结束了，那么子协程也会结束
                delay(1000)
                println("$i end")
            }
            launch {
                println("$i begin")
                delay(1000)
                println("$i finish")
            }
        }
    }
    val end=System.currentTimeMillis()
    println(end-start)//开启10万个协程打印只需要2420毫秒,要是10万个线程早就oom异常了
}
*/

suspend fun printDot() = coroutineScope {// coroutineScope也是一个挂起函数，因此可以在任何其他挂起函数中调用
    //他的特点时会继承外部的协程作用域并且创建一个子作用域，借助这个特点就可以给任意挂起函数添加协程作用域了
    launch {
        println(".")
        delay(1000)
    }
}

fun main(){
    runBlocking {
        //runBlocking会阻塞当前线程
        coroutineScope {
            /*coroutineScope可以保证自己的作用域内的代码和子协程全部执行万之前会一直阻塞当前的协程*/
            launch {
                for (i in 0..10){
                    println(i)
                    delay(1000)
                }
            }
        }
        println("coroutineScope函数结束")
    }
    println("runBlocking结束")
}
