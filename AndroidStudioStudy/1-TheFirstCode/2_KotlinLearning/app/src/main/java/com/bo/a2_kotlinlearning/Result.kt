package com.bo.a2_kotlinlearning

interface Result
class Success(val msg:String):Result
class Failure(val error:String):Result

fun getResultMsg(result: Result)=when(result){
    is Success->result.msg
    is Failure->result.error
    else->throw IllegalAccessException()
    /*这个地方当result是成功的时候返回成功
            当result是失败的时候返回失败
            但是由于when语句里面必须要写一个else,所以我们不得不抛出一个异常来糊弄kotlin编译器
      但是如果我们新增了一个Unkown类而实现的Result接口，但是忘记了在when里面添加对应的条件
      那么就会走到else里面，然后抛出异常程序崩溃*/
}
