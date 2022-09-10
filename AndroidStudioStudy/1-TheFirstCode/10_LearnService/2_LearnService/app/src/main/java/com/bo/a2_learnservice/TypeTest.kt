package com.bo.a2_learnservice

import android.content.Context
import android.content.Intent

/*
inline fun <reified T>getGenericType()=T::class.java

fun main(){
    val result1= getGenericType<String>()
    println(result1)
}*/

/*val intent=Intent(context,MainActivity::class.java)
context.startAcivity(intent)*/

/*inline fun <reified T> startActivity(context: Context){
    val intent=Intent(context,T::class.java)
    context.startActivity(intent)
}*/

/*inline fun <reified T> startActivity(context: Context,block:Intent.()->Unit){
    val intent=Intent(context,T::class.java)
    intent.block()
    context.startActivity(intent)
}

startActivity<MainActivity>(context){
    putExtra("添加信息")
}*/





/*open class Person(val name:String,val age:Int)
class Teacher(name:String,age:Int):Person(name,age)
class Student(name:String,age:Int):Person(name,age)

//SimpleData是一个泛型类，他的内部封装了一个泛型data字段
class SimpleData<T>{
    private var data:T?=null

    fun set(t:T){
        data=t
    }

    fun get():T?=data
}

//下面我们假设如果编程语言允许向某个接收SimpleData<Person>参数的方法传入SimpleData<Student>实例的话
fun main(){
    val student=Student("Tom",19)
    val data=SimpleData<Student>()
    data.set(student)

    handleSimpleData(data)//实际这里是会报错的，假设这里能编译通过
    val studentData=data.get()
    println(studentData)
}

fun handleSimpleData(data: SimpleData<Person>){
    val teacher=Teacher("Jom",39)
    data.set(teacher)
}

*//*handleSimpleData(data)如果能够正确运行的话，那么当前的data中既有student也有teacher
但是开始初始化的时候已经指定了SimpleData中的泛型是Student，所以get返回值应该是一个student，但是data里面却有一个teacher
这时就会发生类型转换异常了*/

/*open class Person(val name:String,val age:Int)
class Teacher(name:String,age:Int):Person(name,age)
class Student(name:String,age:Int):Person(name,age)

//SimpleData是一个泛型类，他的内部封装了一个泛型data字段
class SimpleData<out T>(private val data:T?){
//在T的前面加上out关键字，就表明T只能在out位置出现而不能早in位置出现，而且泛型类里面的元素是只读的(为了保证安全)
    fun get():T?=data
}
fun main(){
    val student=Student("Tom",19)
    val data=SimpleData<Student>(student)

    handleSimpleData(data)//实际这里是会报错的，假设这里能编译通过
    val studentData=data.get()
    println(studentData)
}

fun handleSimpleData(data: SimpleData<Person>){
    println(data.get())
}*/

/*public interface List<out E>:Collection<E>{
    override val size: Int

    override fun contains(element: @UnsafeVariance E): Boolean

    override fun isEmpty(): Boolean

    override fun iterator(): Iterator<E>

    public operator fun get(index:Int)
}*/

interface Transformer<in T>{
    fun transform(t:T):String//T类型的参数参数会在经过转化变成String类型
}
open class Person(val name:String,val age:Int)
class Teacher(name:String,age:Int):Person(name,age)
class Student(name:String,age:Int):Person(name,age)

fun main(){
    val trans=object:Transformer<Person>{
        override fun transform(t: Person): String {
            return "${t.name}今年${t.age}"
        }
    }
    handleTransformer(trans)
//会报错这行代码,是因为Transformer<Person>并不是Transformer<Student>子类型,又是类型转换异常
}

fun handleTransformer(trans:Transformer<Student>){
    val student=Student("Tom",19)
    trans.transform(student)
}
