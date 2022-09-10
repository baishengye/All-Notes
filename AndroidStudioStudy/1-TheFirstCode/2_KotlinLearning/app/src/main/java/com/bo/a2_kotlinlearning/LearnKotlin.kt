package com.bo.a2_kotlinlearning

import kotlin.properties.Delegates

//==============================================
//基础
//fun main() {
//    println("Hello kotlin")

//    变量,kotlin中可以不写;不习惯的话写着也行
//    var p = 10//会自动推到数据类型
//    println(p)

//    显式声明数据类型
//    var p:Int = 10
//    println(p)

//    关键字val,var
//    val声明的数据无法更改
//    var声明的数据可以更改
//    var a=10
//    println(a*10)
//}

//==============================================
//函数-函数和方法两种叫法都可以，翻译不同罢了

//fun add(p1: Int, p2: Int): Int {
//    return p1 + p2
//}

//函数语法糖
//fun add(p1: Int, p2: Int): Int = p1 + p2

//p1+p2的结果是可以通过kotlin自动类型推断出来的
//fun add(p1: Int, p2: Int) = p1 + p2
//
//fun main() {
//    var a: Int = 10
//    var b: Int = 10
//    println(add(a, b))
//}

//==============================================
//逻辑控制
//fun main() {
////    var p1 = 10
////    if(p1>9)
////        println(">9")
////    else if (p1<8)
////        println("<8")
////    else
////        println("=7")
//
////    kotlin中的if与java有所不同
////    if是有一个返回值的
////    var a = 10
////    //if语句有想要保存返回值的时候必须是if...else...一起使用
////    var v=if (a==12)
////    {
////        a
////    }else{
////        a-1
////    }
//
//    //既然想要打印if的返回值，那么v就没有意义了其实，可以直接把if放到println()中
//    var a=10
////    println(if (a==10) a else a-1)
//    println(check(a))
//}
//
////把if的返回值运用在函数里面
//fun check(a:Int)=if (a==10) a else a-1


//==============================================
//when语句：和java中的switch语句相似
//fun get_score(name: String)=when(name){
//    "tom"->86
//    "jam"->78
//    "kit"->90
//    else -> 0//else就相当与switch中的defalut,但是else必须要写，不写就报错
//}

//如果when 里面只有一行判断就不需要写{}，比如以上,以下是复杂写法
//fun get_score(name: String)=when(name) {
//    "tom"->{var a=10
//    a}
//    else -> 0
//}
//
//fun main(){
//    println(get_score("tom"))
//}

//when还可以类型匹配,关键在于is关键字,相当于java中的instanceof
//fun check(a:Number)=when(a) {//Number声明是一个数字
//    is Int-> println("is Int")
//    is Double-> println("is Double")
//    else -> println("is Other")
//}
//可以把需要判断的字符写在{}里面,增加其他功能，比如
//fun get_score(name: String)=when {
//    name.startsWith("tom")->{var a=10//如果字符串是以"tom"开头的，那么都是10
//    a}
//    else -> 0
//}
//fun main(){
//    println(get_score("tom"))
//}

//==============================================
//循环语句
//kotlin中不再有for-i,而只有for-in
//fun main(){
//    var a=0..10//这样a就是一个[0,10]的区间
//    for (x in a)
//        print(" "+x.toString())
//}
//我们可以用until在创建一个左开右闭区间,step关键字用于跳过一些数
//fun main(){
//    var a=0 until 10//[0,10)
//    for (x in a step 2)//step 2相当于下标+2
//        print(" "+x.toString())
//}
//以上都是升序的,downTo是可以实现降序
//fun main(){
//    var a=0 until 10//[0,10)
//    for (x in 9 downTo 0)//从9到0的降序数组
//        print(" "+x.toString())
//    println()
//    for (x in a.reversed())//翻转
//        print(" "+x.toString())
//}

//==============================================
//简单面向对象
//class Person {
//    var name = ""
//    var age = 0
//    fun eat() {
//        println(name + " is eating, He is " + age + " years old.")
//    }
//}
//
//fun main() {
//    var p = Person()//kotlin舍弃了new
//    p.eat()
//}

//继承和构造
//open class Person {
//    var name = ""
//    var age = 0
//    fun eat() {
//        println(name + " is eating, He is " + age + " years old.")
//    }
//}
//class Student:Person(){//kotlin中任意一种非抽象类都是默认无法被继承的,如果想要其可以被继承的话，需要用open关键字声明
//    var sno=""//kotlin中必须要给var声明的变量赋一个初值,来帮助类型推断和申请内存
//    var grade=0
//}

//Person后面为什么要加()呢?
//解答:kotlin中的构造函数有两种:主构造函数和次构造函数
//每个类都会有一个不带参数的主构造函数,当然也可以显式指明参数
//但是这只是把参数传进来了,具体逻辑需要在init()函数写
//class Student(sno:String,grade:Int):Person(){//kotlin中任意一种非抽象类都是默认无法被继承的,如果想要其可以被继承的话，需要用open关键字声明
//    var sno="h"//kotlin中必须要给var声明的变量赋一个初值,来帮助类型推断和申请内存
//    var grade=0
//    init {
//        this.sno=sno
//        this.grade=grade
//    }
//}
//我们都知道无论是kotlin还是java中,子类都要构造的使用都要调用父类的构造函数,但是大多数情况下我们都不会去写init()函数,所以我们需要在继承的时候就直接
//去选好要调用父类的哪个构造函数,这就是Person()的由来
//声明有参构造后默认的无参构造也会消失
//
//open class Person() {
//    var name = ""
//    var age = 0
//    fun eat() {
//        println(name + " is eating, He is " + age + " years old.")
//    }
//}
//注意:如果在类的主构造函数中用val或var构造字段，那么这种字段就会成为类的属性
//fun main(){
//    var s=Student("hh",10)
//    s.eat()
//    println(s.sno+' '+s.grade)
//}

//类只能有一个主构造函数,但是可以有多个不同的次构造函数
//open class Person(  var name:String,var age:Int) {
//    fun eat() {
//        println(name + " is eating, He is " + age + " years old.")
//    }
//}
//class Student(var sno:String,var grade:Int):Person(sno,grade){//kotlin中任意一种非抽象类都是默认无法被继承的,如果想要其可以被继承的话，需要用open关键字声明
//    constructor():this("",1){}
//}

//kotlin允许只有次构造函数
//open class Person(var name: String,var age: Int){}
//class Student:Person{
//    //Student定义了次构造函数,就把默认主构造函数挤掉了,此时Student是没有构造函数的,所以他只能在此构造函数中
//    //用Super直接调用Person的构造函数,就不用在class后面选择Person的构造函数了
//    constructor(name:String,age:Int):super(name,age)
//}


//==============================================
//接口:kotlin与java一样只能继承一个类，但是可以继承多个接口
//interface Study{//接口中的函数不需要实现
//    fun readbook()
//    fun dowork(){
//        println("dowork")//接口内方法的默认实现,有了默认实现,在类里面就不会强制要求实现这个方法
//    }
//}
//
//class Student(var name:String,var grade:Int):Study{
//    override fun readbook() {
//        println(name+" readbook in "+grade)
//    }
//
//    override fun dowork() {
//        println(name+" dowork in "+grade)
//    }
//}
//
//fun doStudy(s:Study)//面向接口编程(多态):类里面的属性不会直接暴露给用户
//{
//    s.readbook()
//    s.dowork()
//}
//
//fun main(){
//    var stu=Student("Tom",3)
//    doStudy(stu);
//}

//数据类
//data class PhoneBean(val name: String, val price: Double){
//    //data关键字会根据主构造函数帮助我自动显现equals,hashCode,toString()方法
//}
//
//fun main(){
//    var cellphone1=PhoneBean("vivo",1212.1)
//    var cellphone2=PhoneBean("oppo",321.1)
//    println(cellphone1.toString())
//    println(cellphone2.toString())
//}

//单例模式(单例类)
//object ImgManager {//object关键字声明的就是一个单例类，天然符合单例模式
//    fun setUrl(url: String) {
//        println("图片地址: " + url)
//    }
//
//    fun into() {
//        println("图片加载成功")
//    }
//}
//
//fun main(){
//    var i=ImgManager
//    i.setUrl("www.geaosu.com/me/logo.png")     // 图片地址: www.geaosu.com/me/logo.png
//    i.into()   // 图片加载成功
//
//}

//==============================================
//lambda编程
//fun main(){
// 集合
//list
//    val arrList = ArrayList<String>()
//    arrList.add("苹果")
//    arrList.add("香蕉")
//    arrList.add("梨")
//    arrList.add("板栗")
//    arrList.add("橙子")
//    arrList.add("番茄")
//    arrList.add("核桃")
//    arrList.add("枣子")
//    arrList.add("柿子")
//    for (x in arrList){
//        print(x+' ')
//    }

//    listof直接构建
//    val arrList= listOf<String>("苹果", "香蕉", "梨", "板栗", "橙子", "番茄", "核桃", "枣子", "柿子")
//    //但是这样listof创建的是一个不可变的集合,也就是不能对集合增删改,mulableListof()可以,而且其实<String>数据类型是可以省略的，因为自动类型推断
//    var arrList2= mutableListOf("苹果", "香蕉", "梨", "板栗", "橙子", "番茄", "核桃", "枣子")
//    arrList2.add("冬瓜")

//    set
//set,和list功能基本一致都是集合,他有setof和mulableSetof,但是他的元素不是有序的,(根据hash映射存放数据)(底层是红黑树)
//    val s=setOf(1,2,1)//由于哈希的机制，set中是不会存在两个相同的元素的
//    for (x in s)
//        print(x.toString()+' ')

//    map:mapof,mulbaleMapof
//    val m= mutableMapOf("1" to 1,"2" to 2)//kotlin的键值对写法
//    m["1"]=2
//    m["2"]=1//与java不同而和c++相同的是,可以用key作为下标来访问value
//    for ((f,n) in m)//这里的访问键值对的方式简直和c++中如出一辙,十分好用,这里直接推断成了Pair
//        println(f+' '+n)
//    var p=Pair(1,2)//键值对
//}

//======================================================================================================
//fun main() {
// 一般实现
//    val list = listOf("孙悟空", "唐僧", "齐天大圣", "美猴王", "凯")
//    var maxLengthName = ""
//    var minLengthName = "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
//    for (item in list) {
//        if (item.length > maxLengthName.length) {
//            maxLengthName = item
//        }
//        if (item.length<minLengthName.length){
//            minLengthName = item
//        }
//    }
//
//    println("名称最长的那个名词是: " + maxLengthName)
//    println("名称最短的那个名词是: " + minLengthName)

// kotlin 函数式api实现
//    val list = listOf("孙悟空", "唐僧", "齐天大圣", "美猴王", "凯")
//    val maxLengthName = list.maxByOrNull{ it.length }
//    val minLengthName = list.minByOrNull{ it.length }
//    println("名称最长的那个名词是: " + maxLengthName)
//    println("名称最短的那个名词是: " + minLengthName)

//    lambda其实就是一小段可以作为参数传递的代码
//    lambda的语法结构:    {参数名1:参数类型,参数名2:参数类型:参数类型->函数体}
//    函数体内可以编写任意代码，不建议太长,并且函数体的最后一行代码会自动作为返回值返回
//    让我们一步一步化繁为简
//    val list = listOf("hhh", "hh", "h", "hhhh")

//    val lambda = { u: String -> u.length }
//    val M = list.maxByOrNull (lambda)
//    这里lambda第一个参数是list.maxBy()中需要比较的list中的元素,而函数返回的是list.maxBy()比较的条件
//    相当就是lambda定义好list.maxBy()比较所需要的信息(这里是元素和要比较的元素长度),然后list.maxBy()会根据lambda中的规定来遍历比较list中元素

//第一步简化
//    val M = list.maxByOrNull({ u: String -> u.length })

//kotlin规定如果lambda参数是函数的最后一个参数(这里lambda是list.maxBy()的最后一个参数),就可以把lambda移到函数外面
//    val M = list.maxByOrNull(){ u: String -> u.length }

//如果lambda是函数的唯一一个参数，就可以把括号省略
//    val M = list.maxByOrNull{ u: String -> u.length }

//由于kotlin优秀的自动类型推导，那么大多数情况下可以不声明变量类型
//    val M = list.maxByOrNull{ u -> u.length }

//    当lambda的参数列表中只有一个参数的时候，可以声明参数名字,而是统一用it关键字替代
//    val M = list.maxByOrNull{ it.length }
//    println(M)

//超级常用map()函数可以把集合中的每个元素映射成另一个值，那么我们就可以用lambda表达式来指定规则
//    val list= listOf("h","hh","hhh","hhhh")
//    val nlist=list.map{it.uppercase()}
//    for (u in nlist)
//        print(u+' ')


//超级常用filter()过滤集合中的数据,可以单独使用，也可以和map函数搭配使用
//比如我们想保留3个字母以内的字符串
//    val list= listOf("h","hh","hhh","hhhh")
//    val nlist=list.filter{it.length<3}.map { it.uppercase() }
//    for (u in nlist)
//        print(u+' ')

//all()判断集合中是否所有元素都满足条件
//any()判断集合中是否至少有一个元素满足条件
//    val list= listOf("h","hh","hhh","hhhh")
//    var alltest=list.all { it.length>3 }
//    var anytest=list.any { it.length>3 }
//    println("all length>3 "+alltest)
//    println("any length>3 "+anytest)
//
//}


//==============================================================
//    java函数式API使用
//    kotlin中使用java函数式API是有条件的:
//    如果我们在kotlin代码中调用了一个java方法，并且该方法接受一个java单抽象方法接口参数(单抽象方法接口:指接口中只有一个需要实现的方法)
//    比如java原生API中有一个最常见的单抽象方法接口-------Runnable,他只有一个方法run()
// 先用java写一个 "java单抽象方法接口" 的例子
//object Thread(new Runnable() {//这里是把Runnabale作为参数传给Thread
//    @Override
//    public void run() {
//        System.out.println("线程执行了");
//    }
//}).start();
// 将以上代码用kotlin实现
//fun main(){
//    Thread(object : Runnable {//kotlin中不再使用new，而是用object代替
//        override fun run() {
//            println("线程执行了")
//        }
//    }).start()
//}
//fun main(){
//    Thread(Runnable { println("线程执行了") }).start()//由于Runnable只有一个run函数需要实现,所以可以省略run不写
//}
//fun main(){
//    Thread({ println("线程执行了") }).start()
//如果方法里面不存在一个以上的接口参数，就可以把接口参数不写
//比如此处就是Thread方法里面只需要Runnable这一个接口参数,那么就不直接写出来
//    { println("线程执行了") }这个lambda表达式是Thread的最后一个参数，也是唯一的一个参数
//    那么就可以写成(最后一个参数，括号省略了)
//    Thread {  println("线程执行了") }.start()
//}


//==================================================================================================
//空指针检查异常
//java要对参数提前进行判空
//但是kotlin有优秀的辅助工具帮助我们
//比如
//fun dostudy(num:Int?)
//{
////    if(num != null)
//    var n=num?.plus(10)
//    println(n)
//}
//fun main(){
////    dostudy(null)//一般情况下(num:Int)不能传一个null作为参数
//    dostudy(null)//但是(num:Int?)就说明了num是一个可为空的Int类型参数,但是这样就有了空指针异常的危险,那么就需要判空了
//}

//?.是一个好用的工具
//a?:b就相当于c++中的a!=NULL?a:b
//a?.toSting()相当于kotlin中的if(a!=null) a.toString()

//!!非空断言工具:强行告诉编译器我不可能是空,快编译
//fun printf(str:String?)= str?:println(str.uppercase())//尽管这里str已经判空但是uppercase不知道所以他会提醒你这里不能这么写，有危险
//这是由就要用!!了
//fun printf(str:String?) = (str!!.uppercase()?:"")
//fun main(){ println(printf("wW"))}

//let函数:提供了API的编程接口,并将原始调用对象作为参数传递到Lambda表达式中
// 学生类
//class Students {
//    fun readBooks(){
//        println("读书")
//    }
//    fun eat(){
//        println("吃")
//    }
//}
// let辅助工具的使用格式
//fun main(){
//    val s = Students()
//    s?.let ({ s ->//通过let来调用s->s.readBooks()这个lambda表达式
//        // 业务逻辑
//        s.readBooks()
//        s.eat()
//    })
//}
//这里的逻辑:？.是表示s不是null的时候会调用let函数，而在此之前let函数会把会把s本身作为参数传给lambda表达式,那么此时s就肯定不是空了
//s不是空就可以调用任意函数了
//然后还可以简化，lambda表达式是最后一个参数,lambda表达式就可以写在()外面，然后括号不写
//fun main(){
//    val s = Students()
//    s?.let { s ->//通过let来调用s->s.readBooks()这个lambda表达式
//        // 业务逻辑
//        s.readBooks()
//        s.eat()
//    }
//}
//lambda表达式中只有s一个参数，就可以忽略不写
//fun main(){
//    val s = Students()
//    s?.let {
//        // 业务逻辑
//        it.readBooks()
//        it.eat()
//    }
//}

//class Students {
//    fun readBooks() {
//        println("读书")
//    }
//
//    fun eat() {
//        println("吃")
//    }
//}
//
//var s: Students? = null//全局变量
//fun main() {
////    由于全局变量随时有可能被其他线程修改,所以if是不能判断全局变量是否为空的,但是let可以做到
//    s?.let {
//        it.readBooks()
//        it.eat()
//    }
//}

//字符串内嵌表达式
//fun main(){
//    var a:Int=10
//    var b:Int=2
////    println("${a} 个人")//内嵌到字符串中
////    println("$a 个人$b 两碗饭")//当表达式只有一个变量的时候可以不用写{},$?与字符串之间要隔一个空格
//    println("${a+b}个人$b 两碗饭")//还是{}方便
//}

//函数的默认参数
//fun add(a:Int,b:Int=10)=a+b
//fun add(a:Int=10,b:Int,c:Int=10)=a+b+c//但是如果只把第一个和第三个参数作为默认参数怎么办?
//fun main(){
////    println(add(10))//b有了默认参数，调用是=时就可以不必须填写b的参数
//    println(add(b=10,c=12))//我们可以用显式调用函数的参数列表,把显式声明函数中的各个参数的值
//}

/*fun main(){
    *//*val list= listOf("a","b","c","d","e")
    val builder=StringBuffer()
    for (x in list)
            builder.append("$x ")
    builder.append("f ")
    val result=builder.toString()
    println(result)*//*

    *//*val list= listOf("a","b","c","d","e")
    val result= with(StringBuffer()){
        for (x in list)
            append("$x ")
        append("f ")
        toString()
    }
    println(result)*//*

    *//*val list= listOf("a","b","c","d","e")
    val result= StringBuffer().run{
        for (x in list)
            append("$x ")
        append("f ")
        toString()
    }
    println(result)*//*

    *//*val list= listOf("a","b","c","d","e")
    val result= StringBuffer().apply{
        for (x in list)
            append("$x ")
        append("f ")
    }
    println(result.toString())*//*


}*/

/*class Util{
    fun doAction(){
        println("do action")
    }

    companion object{
        @JvmStatic
        fun doAction2(){
            println("do action2")
        }
    }
}

fun main(){
    Util.doAction2()
}*/

/*class Money(var v:Int){
    operator fun plus(m:Money): Int {
        return v+m.v
    }

    operator fun plus(m:Int)=v*m
}

fun main(){
    println(Money(10)+ Money(10))
    println(Money(10)+10)
}*/
