---
title: Kotlin 笔记
author: Bob
top: true
toc: true
mathjax: true
categories: Android学习
tags:
  - 第一行代码
  - Kotlin 笔记
abbrlink: 
summary: 《第一行代码》的Kotlin 学习笔记
date: 2022-02-12 00:00:00
---

# Kotlin 笔记
#### 第一个Kotlin程序

    fun main() {
        println("hello world !!!")   // 输出 hello world
    }


#### Kotlin中的数据类型
    Byte                字节型

    Short               短整型
    Int                 整型
    Long                长整型

    Float               单精度浮点型
    Double              双精度浮点型

    Boolean             布尔型

    Char                字符型


#### Kotlin中的变量
    关键字:
        变量: 关键字: var
        常量: 关键字: val (能用val绝不用var)

    val num: Int = 10       整型
    val age = 120           类型推导

#### Kotlin中的函数

    函数的写法1:
    fun 方法名(参数名: 参数类型, 参数名: 参数类型): 返回值类型{
        return 返回值;
    }

    函数的写法2:
    fun 方法名(参数名: 参数类型, 参数名: 参数类型){
        return 返回值;
    }

    函数的写法3:
    fun 方法名(参数名: 参数类型, 参数名: 参数类型): 返回值类型 = 表达式

    函数的写法4:
    fun 方法名(参数名: 参数类型, 参数名: 参数类型) = 表达式


    // 写法1
    fun getLargerNum(num1: Int, num2: Int): Int{
        return max(num1, num2)
    }
    // 写法2
    fun getLargerNum(num1: Int, num2: Int){
        return max(num1, num2)
    }
    // 写法3
    fun getLargerNum(num1: Int, num2: Int): Int = max(num1, num2)

    // 写法4
    fun getLargerNum(num1: Int, num2: Int) = max(num1, num2)



### Kotlin中的逻辑控制
#### 条件语句
    Kotlin中的条件语句主要有两种: if条件语句, when条件语句

    1. if条件语句:
    特性:
        1. 有返回值
        2. 和java中的if一样

    举例:
        // 普通的if写法
        fun getLargerNum(num1: Int, num2: Int): Int {
            var value = 0
            if (num1 > num2) {
                value = num1
            } else {
                value = num2
            }
            return value
        }

        // 有返回值的if写法1
        fun getLargerNum2(num1: Int, num2: Int): Int {
            val value = if (num1 > num2) {
                num1
            } else {
                num2
            }
            return value
        }

        // 有返回值的if写法2
        fun getLargerNum3(num1: Int, num2: Int): Int {
            return if (num1 > num2) {
                num1
            } else {
                num2
            }
        }

        // 有返回值的if写法3
        fun getLargerNum4(num1: Int, num2: Int): Int = if (num1 > num2) {
            num1
        } else {
            num2
        }

        // 有返回值的if写法4
        fun getLargerNum5(num1: Int, num2: Int) = if (num1 > num2) {
            num1
        } else {
            num2
        }

        // 有返回值的if写法5
        fun getLargerNum6(num1: Int, num2: Int) = if (num1 > num2) num1 else num2


    1. when条件语句:
    就是java中的switch语句, when语句必须掌握, 常用功能
    特性:
        1. 有返回值
        2. 没有break结束
        3. 任意类型参数
    举例:
        // 查询成绩
        fun getSource(name: String) = if (name == "tom") {
            100
        } else if (name == "jok") {
            90
        } else if (name == "bob") {
            10
        } else {
            0
        }

        // when 精确匹配
        fun getSource2(name: String) = when (name) {
            "tom" -> 100
            "jok" -> 90
            "bob" -> 10
            else -> 0
        }

        // when 类型匹配
        fun getSource3(name: Number) = when (name) {
            is Int -> println("num is Int")
            is Double -> println("num is Double")
            else -> println("num not is a Number")
        }

        // when 不带参数的用法
        fun getSource4(name: String) = when {
            name.startsWith("t") -> 80
            name == "tom" -> 100
            name == "jok" -> 90
            name == "bob" -> 10
            else -> 0
        }


#### 区间的概念
    闭区间: val range = 0..10      // 0-10的闭区间, 数学表达方式 [0,10]
    左闭右开区间: val range = 0 until 10  // 0-9的区间, 数学表达方式 [0,10)

    #### 循环语句
    while语句和for语句
    1. while语句: 和java没区别
    2. for语句:
    特性:
        1. 去除for-i
        2. 增强了for-in: 加强版for-each

    用法:
    1. 遍历区间
    for (i in 0..10) {
       println(i)
    }

    2. 遍历 左闭右开区间的遍历
    for (i in 0 until 10) {
        print(" " + i)  //
    }

    3. 遍历递增数值
    // step 1 相当于 i=i++
    // step 2 相当于 i=i+2
    // step 3 相当于 i=i+3
    for (i in 0 until 10 step 2) {
        print(" " + i)  // 0 2 4 6 8
    }

    4. 降序遍历
    for (i in 10 downTo 0) {
        print(" " + i)  // 10 9 8 7 6 5 4 3 2 1 0
    }

    for (i in 10 downTo 0 step 2) {
        print(" " + i)  // 10 8 6 4 2 0
    }

    5. 遍历数组和集合

    6. 如果for-in无法实现, 请使用while循环


    举例:
    // 遍历区间
    for (i in 0..10) {
        println(i)
    }


#### 类与对象
    // 人类
    class Person {
        var name = ""
        var age = 0
        fun eat() {
            println(name + " is eating, He is " + age + " years old.")
        }
    }

    // 调用创建的Person类
    val p = Person()
    p.name = "geaosu"
    p.age = 26
    p.eat()


#### 继承
    特点:
        1. Kotlin 默认所有非抽象类都是不可以被继承的.
        2. 抽象类可以被继承, 抽象类本身是无法创建实例的, 需要子类继承才能创建实例;
        3. kotlin中的抽象类和java中的抽象类并无区别;
        4. 让一个类能被继承, 需要在class前面加上open关键字;

    // 人类
    open class Person {
        var name = ""
        var age = 0
        fun eat() {
            println(name + " is eating, He is " + age + " years old.")
        }
    }

    // 学生类
    class Student :Person(){    // 该类型有构造函数, 必须在这里初始化
        var son = ""
        var grade = 0
    }

    // 调用
    val s = Student()
    s.name = "geaosu"
    s.age = 20
    s.son = "2020"
    s.grade = 3
    s.eat()


#### 主构造函数 和 次构造函数
    主构造函数:
    1.特点:
        1. 每个类都会有一个默认的不带参数的主构造函数, 可以显示的指明参数;
        2. 没有函数体;
        3. 提供了一个init函数, 需要在构造函数中做的事情放在init函数中即可;

    2. 代码演示

    ------>> 没有继承

    // 主构造函数, 没有函数体, 默认不带参数的构造函数
    class Test012() {
        // 提供init函数做初始化操作
        init {

        }
    }

    // 主构造函数, 显示的指明参数列表,
    注意: 如果在主构造函数的参数前面加上var或者val, 该参数会成为该类的成员变量,
    如果不加var或者val, 则该参数只在主构造中有效;
    class Test013(name: String, age: Int) {
        init {
            print("名字: " + name)
            print("   年龄: " + age)
        }
    }

    // 创建实例时调用了init方法打印出了内容
    val p = Test013("geaosu", 20)   // 名字: geaosu   年龄: 20

    ------>> 有继承

    // 父类-主构造没有参数
    class Person(){
        var name = ""
        var age = 0
        fun eat{
            print(name+" 在吃饭, 他(她)今年 "+age+" 岁了")
        }
    }

    // 父类-主构造有参数
    class Person(name: String, age:Int){
       init{

       }
        fun eat{
            print(name+" 在吃饭, 他(她)今年 "+age+" 岁了")
        }
    }

    // 主构造没有参数
    class Test015(): Person(){

    }

    // 主构造有参数
    class Test016(name:String, age:Int) : Person(name, age){

    }



    次构造函数:
    class Student {
        // 没有参数的次构造函数
        constructor(){
            println("没有参数的次构造函数")
        }
        // 有1个参数的次构造函数
        constructor(name: String) : this(){
            println("有1个参数的次构造函数")
        }
        // 有2个参数的次构造函数
        constructor(name: String, age: Int) : this(name){
            println("有2个参数的次构造函数")
        }
    }



#### 接口
    关键字: interface
    实现关键字: :号
    特性: Kotlin允许对接口中定义的函数进行默认实现

    // 定义一个接口
    interface Test017_Study {
        fun readBook()      // 看书
        fun doHomeWrok()    // 写作业
    }

    // 对接口中定义的函数进行默认实现
    注意:
        当接口中的某个方法有默认实现时,不会强迫子类去重写,
        子类可自由选择是否需要实现, 不实现则使用默认实现;

    interface Test017_Study {
        fun readBook()      // 看书
        fun doHomeWrok()    // 写作业
        // 对接口中定义的函数进行默认实现
        fun playFootBall(){
            println("踢足球, 踢进世界杯")
        }
    }


    class Students() : Study {
        override fun readBook() {
            println("我在看书")
        }

        override fun doHomeWrok() {
            println("我在写作业")
        }
    }



#### 函数的可见性修饰符
    java中的修饰符:
        public      : 所有类可见
        private     : 当前类内部可见
        protected   : 当前类, 子类, 同一个包路径下的类可见
        default     : 默认

    Kotlin中的修饰符:
        public      : 默认, 所有类可见
        private     : 当前类内部可见
        protected   : 当前类, 子类可见
        internal    : 只对同一模块中的类可见

    相同的内容: privat 只对当前类可见
    不相同的内容:
    public: 所有类可见, Kotlin默认
    protected: java当前类, 子类, 同包下可见, kotlin当前类, 子类可见
    default: java默认
    internal: kotlin同一模块可见


    看下面表格:
    ______________________________________________________________________________
    |   修饰符       |      java                                   kotlin         |
    |----------------------------------------------------------------------------|
    |   public      |    所有类可见                          |   所有类可见        |
    |   private     |    当前类内部可见                       |  当前类内部可见     |
    |   protected   |    当前类, 子类, 同一个包路径下的类可见   |  当前类, 子类可见   |
    |   default     |    默认                                |         无         |
    |   internal    |    无                                  |  同一模块可见       |
    |____________________________________________________________________________|




#### 数据类
    // 手机数据类
    data class PhoneBean(val name: String, val price: Double)

    注意:
        1. 当在一个类前面写上data关键字, 就说明这是一个数据类;
        2. 当有data关键字时, kotlin会根据主构造函数中的参数将equals(), hashCode(), toString()等方法自动生成;
        3. 当一个类内部没有任何代码时, 可以将尾部的大括号去掉;

    创建实例测试 - 数据相同
        val p1 = Test018_PhoneBean("坚果pro3", 3899.0)
        val p2 = Test018_PhoneBean("坚果pro3", 3899.0)
        println("p1 = " + p1)// p1 = Test018_PhoneBean(name=坚果pro3, price=3899.0)
        println("p1 和 p2 是不是同一个对象? " + (p1 == p2))// p1 和 p2 是不是同一个对象? true

    创建实例测试 - 数据不同
        val p1 = Test018_PhoneBean("坚果pro3", 3899.0)
        val p2 = Test018_PhoneBean("坚果pro3", 2899.0)
        println("p1 = " + p1)// p1 = Test018_PhoneBean(name=坚果pro3, price=3899.0)
        println("p1 和 p2 是不是同一个对象? " + (p1 == p2))// p1 和 p2 是不是同一个对象? false






#### 单例类
    注意:
        1. 将class关键字替换成object关键字
        2. 调用方式和java中的静态方法的调用方式相同, 类名.方法名


    /**
     * 图片管理器 - 单利模式
     */
    object ImgManager {
        fun setUrl(url: String) {
            println("图片地址: " + url)
        }

        fun into() {
            println("图片加载成功")
        }
    }

    fun main(){
        Test019_ImgManager.setUrl("www.geaosu.com/me/logo.png")     // 图片地址: www.geaosu.com/me/logo.png
        Test019_ImgManager.into()   // 图片加载成功
    }



### Lambda编程

#### 集合的创建与遍历

    List集合的创建方式:

        方式1: 普通方法
            // kotlin 创建集合的最普通的方法
            val arrList = ArrayList<String>()
            arrList.add("苹果")
            arrList.add("香蕉")
            arrList.add("梨")
            arrList.add("板栗")
            arrList.add("橙子")
            arrList.add("番茄")
            arrList.add("核桃")
            arrList.add("枣子")
            arrList.add("柿子")

            // 使用for-in遍历集合
            for (item in arrList) {
                print(item + " ")// 苹果 香蕉 梨 板栗 橙子 番茄 核桃 枣子 柿子
            }

        方式2: Kotlin提供的快速创建集合函数: listOf()

            // kotlin提供的listof方法快速创建集合
            val list = listOf<String>("苹果", "香蕉", "梨", "板栗", "橙子", "番茄", "核桃", "枣子", "柿子")
            // <String> 可以省略
            val list = listOf("苹果", "香蕉", "梨", "板栗", "橙子", "番茄", "核桃", "枣子", "柿子")
            for (item in list){
                print(item + " ")// 苹果 香蕉 梨 板栗 橙子 番茄 核桃 枣子 柿子
            }

            val list2 = listOf(1, 2, 3, 4, 5, 6)
            for (item in list2) {
                print(" " + item)// 1 2 3 4 5 6
            }

        方式3: Kotlin提供的快速创建集合函数: 使用mutableListOf()

            val list = mutableListOf("苹果", "香蕉", "梨")
            list.add("番茄")
            list.remove("香蕉")
            for (item in list) {
                print(" " + item)// 苹果 梨 番茄
            }

        注意:
            1. listOf()方法创建的集合是不可变的, 只能用于读取, 不能进行添加, 修改, 删除;
            2. 需要创建可变集合时, 使用mutableListOf()函数

        List集合中的常用函数:
            1. map()
            2. filter()
            3. any()
            4. all()

    Set集合的创建方式:
        注意: Set集合底层使用hash映射机制来存放数据的, 因此集合中的元素无法保证有序, 这是和List集合最大的不同之处;

        和List集合的创建方式相同, 这里只展示函数名称
        setOf(): 创建不可变集合
        mutableSetOf(): 创建可变集合



    Map集合的创建方式:
        注意: Map是一种键值对形式的数据结构, 用法上和list和set集合有较大的差别;

        方式1: 类似java中的写法
            val map = HashMap<String, Int>()
            map.put("香蕉", 1)
            map.put("苹果", 2)
            map.put("梨", 3)

            for (item in map) {
                print(" " + item)// 香蕉=1 苹果=2 梨=3
            }

        方式2: kotlin中推荐使用一种类似于数组下标的语法结构;
        kotlin中不建议使用put和get方法来对map进行添加和读取数据的操作,
            val map = hashMapOf<String, Int>()
            // 添加数据
            map["苹果"] = 1
            map["香蕉"] = 2
            map["梨"] = 3
            for (item in map) {
                print(" " + item)// 苹果=1 香蕉=2 梨=3
            }

            // 读取数据
            val num1 = map["苹果"]
            val num2 = map["香蕉"]
            print(num1)// 1
            print(num2)// 2


        方式3: Kotlin提供的简化函数: mapOf()
            val map = mapOf("苹果" to 1,"梨" to 2, "番茄" to 3)
            // 注意: to 不是关键字, 而是infix函数
            for (item in map){
                print(" " + item)// 苹果=1 梨=2 番茄=3
            }


        方式4: Kotlin提供的简化函数: mutableMapOf()
            val map = mutableMapOf("苹果" to 1, "梨" to 2, "番茄" to 3)
            map["梨"] = 12 // 修改数据
            map["西红寺"] = 20 // 增加数据
            map.remove("苹果") // 删除数据
            for (item in map) {
                print(" " + item)// 苹果=1 梨=12 番茄=3 西红寺=20
            }

    Map集合的遍历方式:
        val map = mutableMapOf("苹果" to 1, "香蕉" to 2, "梨" to 2, "番茄" to 4)
        for ((key, value) in map) {
            print("key = " + key + "  value = " + value + "\n")
            // key = 苹果  value = 1
            // key = 香蕉  value = 2
            // key = 梨  value = 2
            // key = 番茄  value = 4
        }





#### 集合的函数式API ---> 正式入门Lambda编程
    注意: 函数式api有很多, 重点需要学习函数式API的语法结构, 也就是Lambda表达式的语法结构;

    // 需求: 找到一些名词中名称最长和最短的那个名词
    // 一般实现
    val list = listOf("孙悟空", "唐僧", "齐天大圣", "美猴王", "凯")
    var maxLengthName = ""
    var minLengthName = "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
    for (item in list) {
        if (item.length > maxLengthName.length) {
            maxLengthName = item
        }
        if (item.length<minLengthName.length){
            minLengthName = item
        }
    }

    println("名称最长的那个名词是: " + maxLengthName)
    println("名称最短的那个名词是: " + minLengthName)

    // kotlin 函数式api实现
    val list = listOf("孙悟空", "唐僧", "齐天大圣", "美猴王", "凯")
    val maxLengthName = list.maxBy { it.length }
    val minLengthName = list.minBy { it.length }
    println("名称最长的那个名词是: " + maxLengthName)
    println("名称最短的那个名词是: " + minLengthName)



    Lambda表达式:
        定义: Lambda就是一小段可以作为参数传递的代码;
        解释: 正常情况下, 向某个函数传参时只能传入变量, 而借助Lambda却允许传入一小段代码, (一小段代码没有做限制, 也可以是很多)
        语法结构: {参数1: 参数类型, 参数2: 参数类型 -> 函数体}

        注意: 很多时候, 并不需要使用Lambda表达式的完整语法, 而是有很多种简化的写法;
        简化写法:
            这里演示一下简化过程

    Lambda表达式简化写法的演变过程
        // 先定义一个集合
        val list = listOf("猴子", "猪八戒", "娜可露露", "李元芳", "凯")
        // 1. 完整的表达式写法: {参数1: 参数类型, 参数2: 参数类型 -> 函数体}
        val lambdaParams = { itemName: String -> itemName.length }
        val maxLengthName1 = list.maxBy(lambdaParams)
        // 2. 不需要专门定义一个lambda参数, 可以直接将lambda表达式传入函数中;
        val maxLengthName2 = list.maxBy({ itemName: String -> itemName.length })
        // 3. Kotlin规定, 当Lambda参数是函数的最后一个参数时, 可以将Lambda参数移到函数的括号外面;
        val maxLengthName3 = list.maxBy() { itemName: String -> itemName.length }
        // 4. 如果Lambda参数是函数唯一一个参数的话, 可以将函数的括号省略;
        val maxLengthName4 = list.maxBy { itemName: String -> itemName.length }
        // 5. 由于kotlin拥有出色的类型推导机制, Lambda表达式中的参数列表其实在大多数情况下可以省略参数类型;
        val maxLengthName5 = list.maxBy { itemName -> itemName.length }
        // 6. 当Lambda表达式的参数列表中只有一个参数的时候, 也可以不必声明参数名, 而是使用it关键字来代替;
        val maxLengthName6 = list.maxBy { it -> it.length }


    list集合中的map函数
        // list集合中常用map函数式api的使用
        // map()函数用于将集合中的每个元素都映射成一个另外的值, 映射的规则在lambda表达式中指定, 最终生成一个新的集合;
        val list = listOf("Apple", "Orange", "Pear")
        // val newList = list.map { it.toLowerCase() }// 将名称全部转成小写
        val newList = list.map { it.toUpperCase() }// 将名称全部转成大写
        for (item in newList) {
            print(" " + item)// APPLE ORANGE PEAR
        }


    list集合中的filter函数
        // filter()函数用来过滤集合中的数据, 可以单独使用, 也可以配合map函数使用;

        // 单独使用
        val list = listOf("猴子", "嫦娥", "常山赵子龙", "娜可露露", "凯", "李元芳")
        val newList = list.filter { it.length >= 3 }
        for (item in newList) {
            print(" " + item)// 常山赵子龙 娜可露露 李元芳
        }

        // 配合map函数使用
        val list = listOf("houzi", "change", "changshanzhaozilong", "nakelulu", "kai", "liyuanfang")
        val newList = list.filter { it.length >= 9 }.map { it.toUpperCase() }
        for (item in newList) {
            print(" " + item)// CHANGSHANZHAOZILONG LIYUANFANG
        }


    list集合中的any函数
        作用: 判断集合中是否至少存在一个元素满足指定条件;
    list集合中的all函数
        作用: 判断集合中是否所有元素都满足指定条件;

    val list = listOf("凯", "猴子", "李元芳", "娜可露露", "常山赵子龙")
    val any = list.any { it.length <= 3 }// 至少有一个元素长度小于等于3
    val all = list.all { it.length <= 3 }// 所有的元素长度是不是小于等于3
    println(any)// true
    println(all)// false

#### 在Kotlin中调用java函数式api
    注意:
        1. 条件限制: 如果在kotlin中调用一个java方法, 并且该方法接收一个 "java单抽象方法接口" 参数, 就可以使用函数式api;
        2. java单抽象方法接口指的是接口中只有一个待实现的方法, 如果接口中有多个待实现方法, 则无法使用函数式api;

    案例演示:
        // 先用java写一个 "java单抽象方法接口" 的例子
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程执行了");
            }
        }).start();

        // 将以上代码用kotlin实现
        Thread(object : Runnable {
            override fun run() {
                println("线程执行了")
            }
        }).start()

        // 对以上kotlin代码进行简化
        Thread(Runnable { println("线程执行了") }).start()

        // 对以上kotlin代码继续简化
        // 如果一个java方法的参数列表中不存在一个以上java单抽象方法接口参数, 还可以将接口名进行省略
        Thread({ println("线程执行了") }).start()

        // 最终简化
        // lambda表达式是方法的最后一个参数时, 可以将lambda表达式移到方法括号外面;
        // 如果lambda表达式还是方法的唯一一个参数, 还可以将方法的括号省略掉;
        Thread { println("线程执行了") }.start()


### Kotlin中的匿名类
    注意:
        1. 由于kotlin完全舍弃了new关键字, 因此创建匿名类实例时改用object关键字;
        2.





### 空指针检查
#### 可空类型系统
    String 表示不可控的类型
    String? 表示可为空的类型

    当类型加上?符号时, 调用该类的方法时需要加上if判空语句
    fun doSomeThing(str: String?) {
        if (str != null) {
            val length = str.length
            val toUpperCase = str.toUpperCase()
        }
    }



#### Kotlin空指针检查辅助工具
    1.操作符:
        ?.操作符: 当对象不为空时正常调用方法, 当对象为空时什么都不做
        ?:操作符: 如果对象不等于空就返回对象, 否则返回指定内容

    2. 工具
        !!非空断言工具: 告诉kotlin通过编译, 如果出现问题, 直接抛出异常, 慎用!!! 慎用!!! 慎用!!!

    3.函数:
        let辅助工具: let辅助工具是一个函数; 将原始调用对象作为参数传递到Lambda表达式中;

    代码演示
    // 1. ?.操作符: 当对象不为空时正常调用方法, 当对象为空时什么都不做
    // ?  str可以为空
    fun doSomeThing(str: String?) {
        // ?.  str如果为空什么都不做, str如果不为空则调用length属性
        val length = str?.length
        val toUpperCase = str?.toUpperCase()
    }


    // 2. ?:操作符
    // 普通操作
    fun doSomeThing(str: String?): Int {
        if (str != null) {
            return str.length
        }
        return 0
    }

    // 3. ?:操作符 和 ?.操作符 一起使用
    fun doSomeThing(str: String?) = str?.length ?: 0


    // 4. !!非空断言工具
        val s = Students()
        // !!工具会告诉kotlin通过编译, 如果出现问题, 直接抛出异常, 慎用!!! 慎用!!! 慎用!!!
        s!!.readBooks()


    // 5. let辅助工具

        // 学生类
        class Students {
            fun readBooks(){
                println("读书")
            }
        }


        // let辅助工具的使用格式
        val s = Students()
        s.let { s ->
            // 业务逻辑
            s.readBooks()
        }


        // let辅助工具的简化形式
        val s = Students()
        s.let { it.readBooks() }


    // 6. let函数 配合 ?.操作符 检测空指针

        // 允许可空语句 ---> 有点啰嗦
        fun study(s: Test028_Students?) {
            s?.readBooks()
        }

        // 标准if语句
        fun study2(s: Test028_Students) {
            if (s != null) {
                s.readBooks()
            }
        }

        // let函数 配合 ?.操作符 进行优化
        fun study3(s: Test028_Students) {
            // s?.let { s -> s.readBooks() }
            s?.let { it.readBooks() }
        }


    // 7. let函数可以处理全局变量的判空问题, if不行





### Kotlin中的几个小技巧
#### 1.字符串内嵌表达式
    作用: 直接将表达式写在字符串里面;
    语法: "hello, ${obj.name}. nice to meet you!"

    举例:
        // 小明类
        class XiaoMing {
            val name: String = "小明"
            val age: Int = 20
            fun getInfo() {
                // 字符串内嵌表达式
                println("姓名: ${name} \n年龄: ${age}")
            }

            // ${} 表达式中如果只有一个变量的时候, {}可以省略掉
            fun getInfo2() {
                println("姓名: $name \n年龄: $age")
            }
        }

        // 调用
        val xiaoMing = XiaoMing()
        xiaoMing.getInfo()

        // 注意, 这里不能省略{}, 否则无法正常输出内容, 会输出地址值: 输出内容如下:
        // hello, com.geaosu.wanandroid.kotlin.learn.Test029_XiaoMing@45ee12a7.name. nice to meet you!
        // val msg = "hello $xiaoMing.name . nice to meet you!"

        val msg = "hello ${xiaoMing.name} . nice to meet you!"
        println(msg)// hello 小明 . nice to meet you!


    总结:
        1.语法: ${变量, 变量}
        2.如果表达式中只有一个变量, {}可以省略不写, 变成: $变量




#### 2.函数的参数默认值
    msg: kotlin中次构造函数很少使用, 因为kotlin提供了给函数设定参数默认值的功能, 它在很大程度上能够替代次构造函数的作用
    语法: fun 方法(参数名: 参数类型 = 默认值){}

    举例:
        // 定义函数
        fun readBooks1(name: String, age: Int) {}
        fun readBooks2(name: String = "小明", age: Int) {}
        fun readBooks3(name: String = "小明", age: Int = 20) {}
        fun readBooks4(name: String = "小明", age: Int = 20, msg: String) {}

        // 调用函数
        readBooks1("小明", 21)
        readBooks2(age = 22)
        readBooks3()
        readBooks4(msg = "三好学生")

    总结:
        1.只编写一个主构造函数, 给参数设定默认值, 通过随意组合来对类进行实例化;


#### 目前遇见的所有函数
    1. max(a, b): 求最大值
    2. == : 比较两个字符串是否相等, 相当于equals方法
    3. Boolean startsWith(String): 判断某个字符串是否以某个字符开头


## 语法糖
### 其一
比如
```java
public class Book{
    private int Pages;
    public get;
    public set;
}

kotlin在调用这种结构的java方法时，有一种更加简便的方法访问Pages

val book=Book()
book.Pages=500
val bookPages=book.Pages

其实这里是在背后自动将上述代码转化成了set和get
```

### 高级语法糖----`infix函数`
之前我们在创造键值对的时候用的是 `A to B`,那么`to`是不是一个关键字呢?
答案:不是.其实`A to B`的完全写法是`A.to(B)`
这里就是`infix`函数的特性了，他会把编程语言函数调用的语法规则调整一下
**看看例子**
```kotlin
if("Hello world".startsWith("Hello"))
        println(true)
StringA.startsWith(StringB)//A是不是以B开头的

我们可以用infix函数来简化startsWith的操作

infix fun String.beginWith(str: String)=startsWith(str)
//这样beginWith()就变成了一个infix()函数
//用法就可以像下面一样

fun main(){
    if("Hello world" beginWith "Hello")
        println(true)
}
```
- 所以`infix`可以把小数点,括号等计算机相关的语法去掉，使代码可读性更强
- 但是他也是有要求的
  - infix函数不能使顶层方法，它必须是某个类的成员函数，可以用扩展函数的方式添加到类中
  - infix必须接收并且只能接收一个参数(参数类型没有限制)

**康康to的源码**
```kotlin
public infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)
其实就是一个泛型定义的函数
```

## 标准函数和静态方法
### 标准函数
```kotlin
with():两个参数
    第一个:任意类型的对象
    第二个:一个lambda表达式
    with函数会在lambda表达式中提供第一个参数对象的上下文
    并使用lambda表达式中最后一行作为返回值返回
    形如:with(obj){"value"}
    
    示例:
    val list= listOf("a","b","c","d","e")
    val builder=StringBuffer()
    for (x in list)
            builder.append("$x ")
    builder.append("f ")
    val result=builder.toString()
    println(result)

    可以用with()函数简化成

    val list= listOf("a","b","c","d","e")
    val result= with(StringBuffer()){
        for (x in list)
            append("$x ")
        append("f ")
        toString()
    }
    println(result)//result:String
```
```kotlin
run():是不能直接调用的,而是一定要调用某个对象的run函数才行
一个参数
    只能接收一个lambda参数，并且在lambda表达式中提供调用对象的上下文
    包括也会使用lambda表达式中的最后一行作为返回值返回
    形如:var result=obj.run{"value"}

    示例:
    val list= listOf("a","b","c","d","e")
    val builder=StringBuffer()
    for (x in list)
            builder.append("$x ")
    builder.append("f ")
    val result=builder.toString()
    println(result)

    可用run函数简化成

    val list= listOf("a","b","c","d","e")
    val result= StringBuffer().run{
        for (x in list)
            append("$x ")
        append("f ")
        toString()
    }
    println(result)//result:String
```
```kotlin
apply():是不能直接调用的,而是一定要调用某个对象的run函数才行
    只能接收一个lambda参数，并且在lambda表达式中提供调用对象的上下文
    但是无法指定返回值，只能返回调用对象本身
    形如:var result=obj.apply{}

    示例:
    val list= listOf("a","b","c","d","e")
    val builder=StringBuffer()
    for (x in list)
            builder.append("$x ")
    builder.append("f ")
    val result=builder.toString()
    println(result)

    可用run函数简化成

    val list= listOf("a","b","c","d","e")
    val result= StringBuffer().apply{
        for (x in list)
            append("$x ")
        append("f ")
    }
    println(result.toString)//result:StringBuffer
```

### 定义静态方法
```kotlin
kotlin中极度弱化了静态方法这个概念，但是他也提供了另一种更加好用的方式
工具类这种功能，kotlin中非常推荐使用单例类来实现
示例:
    object Util{
        fun doAction(){
            println("do action")
        }
    }

但是单例类会让类里面的方法全部变成静态的,那我们只需要类里面有一个静态方法，可以这样写:
class Util{
    fun doAction(){
        println("do action")
    }
    
    companion object{
        fun doAction2(){
            println("do action2")
        }
    }
}
但是其实doAction2()并不是一个静态方法,companion object关键字会在Util类的内部创建一个伴生类
而doAction2()实际上是定义在这个伴生类里面的实例方法，
但是由于kotlin会保证Util类始终只会存在一个伴生类对象
因此调用Util.doAction2()实际上是调用了Util类中伴生对象的doAction2()方法
```
```kotlin
其实以上的都不是真正的静态方法,因此如果你在java代码中以静态方法的形式去调用的话，会发现这些方法根本不存在
kotlin确实提供了两种定义真正静态方法的实现方式:注解和顶层方法

1.
注解：加上@JvmStatic 注解后kotlin编译器就会把这些方法编译成真正的静态方法
实例:
class Util{
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
@JvmStatic 注解只能加在单例类或者companion object上面，加在其他地方会报错
如今doAction2()已经完全是一个静态方法了，既可以在kotlin中调用也可以在java中调用


2.
顶层方法(有点像c++中的全局函数,但又有所不同): 指那种没有定义在任何类中的方法，比如main()函数,kotlin会把所有顶层方法都编译成静态方法
比如:
如果我创建一个Hello.kt文件,在里面写一个方法
fun sayHello(){
    println("Hello")
}
这就是一个顶层方法了
在kotlin代码中调用的话极其简单，在任何位置都能直接调用，不用管路径和创建实例
在java代码中是无法直接调用的，以上面的方法为例,sayHello方法是在Hello.kt中,
kotlin编译器会自动创建一个叫做HelloKt的Java类，sayHello()方法是以静态方法的的形式定义在HelloKt类里面
因此java中调用要写HelloKt.sayHello()调用
```

## 延迟初始化
```kotlin
看这个

class MainActivity : AppCompatActivity() {
    
    private var adapter:MsgAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        ···
        adapter=MsgAdapter(msgList)
        ···
        send.setOnClickListener {
            ···
            adapter?.notifyItemInserted(msgList.size-1)//有消息
            ···
        }

    }
    ···
}

会发现我们在使用age的时候会判很多次空，即使我们很确定age不可能是空的

但是我们也有解决方法

使用延迟初始化(关键字: lateinit)他会告诉kotlin编译器,晚些时候一定会初始化的，这样就不用一开始把他赋值为null
优化完:
class MainActivity : AppCompatActivity() {
    
    private lateinit var adapter:MsgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        ···
        adapter=MsgAdapter(msgList)
        ···
        send.setOnClickListener {
            ···
            adapter.notifyItemInserted(msgList.size-1)//有消息的时候就刷新recycleView中的显示
            ···
        }

    }
    ···
}

但是这样不太安全，所以可以的话还是要检查一下他是否已经初始化了
override fun onCreate(savedInstanceState: Bundle?) {
    ···
    if(!::adapter.isInitialized)
        adapter=MsgAdapter(msgList)
    ···
    send.setOnClickListener {
        ···
        adapter.notifyItemInserted(msgList.size-1)//有消息的时候就刷新recycleView中的显示
        ···
    }
}

运用了::adapter.isInitialized，如果初始化了就返回true

```

## 密封类
```kotlin
interface Result
class Success(val msg:String):Result
class Failure(val error:String):Result

fun getResultMsg(result: Result)=when(result){
    is Success->result.msg
    is Failure->result.error
    else->throw IllegalAccessException()
    /*
    这个地方当result是成功的时候返回成功 
            当result是失败的时候返回失败
            但是由于when语句里面必须要写一个else,所以我们不得不抛出一个异常来糊弄编译器
      但是如果我们新增了一个Unkown类而实现的Result接口，但是忘记了在when里面添加对应的条件
      那么就会走到else里面，然后抛出异常程序崩溃
      */
}
```
```kotlin
我们可以使用密封类来解决这个问题，关键字是sealed class
我们可以改写成
sealed class Result
class Success(val msg:String):Result
class Failure(val error:String):Result

fun getResultMsg(result: Result)=when(result){
    is Success->result.msg
    is Failure->result.error
}
为什么这里可以不写else？
因为密封类要求当在when语句中传入一个密封类作为条件的时候
kotlin编译器会要求自动检查密封类有哪些子类
并且强制要求你把所有子类对应的条件全部处理
```

## 扩展函数
```kotlin
扩展函数是什么?
扩展函数表示在即使不修改某个类的源码的情况下，仍然可以打开这个类，向这个类添加新的函数

比如我们想向String类里面添加一个方法，难道我们要先找到String.kt的源码，然后在里面加吗?
很显然不是，扩展函数就可以帮我们实现.

扩展函数的语法结构:
fun ClassName.methodName(param1:Int,param2:Int):Int{
    return 0
}
这样就可以加扩展到对应的类里面的

比如:
fun String.lettersCount():Int{
    var count=0
    for(char int this){//由于扩展函数会自动拥有对应类的实例的上下文,所以这个函数就不需要接受一个字符串参数，而是直接遍历this即可
        if(char.isLetter())
            count++
    return count
    }
}
```

## 字符串重载
```kotlin
kotlin允许重载所有的运算符甚至其他的关键字，来扩展这些运算符和关键字的用法

重载运算符 :opeartor

比如:而且可以重载多次
class Money(var v:Int){
    operator fun plus(m:Money): Int {
        return v+m.v
    }

    operator fun plus(m:Int)=v*m
}
```
[![bgLdTP.png](https://s1.ax1x.com/2022/03/08/bgLdTP.png)](https://imgtu.com/i/bgLdTP)

## 高阶函数详解
### 定义高阶函数
**高阶函数和lambda表达式密不可分**
```kotlin
定义:  如果一个函数接收另一个函数作为参数,或者返回值的类型是另一个函数，那么这个函数就被称为高阶函数

定义函数类型的语法规则:
(String,Int)->Unit
含义:
    ->的左边部分是用来声明该函数接收什么参数,多个参数用,隔开,如果没有参数就写空括号就行
    ->右边部分用来声明函数的返回值是什么类型,Unit就是无返回参数,相当于c++中的void

将上述函数类型写在一个函数的参数列表，或者返回值声明部分就是高阶函数了
比如:

fun example(func:(String,Int)->Unit){
    func("Hello",2)
}

具体运行的例子:

fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
    return operation(num1, num2)
}

fun plus(num1: Int,num2: Int):Int{
    return num1+num2
}

fun minus(num1: Int,num2: Int):Int{
    return num1-num2
}

fun main(){
    val num1=100
    val num2=200
    println("num1+num2=${num1AndNum2(num1,num2,::plus)}")
    println("num1-num2=${num1AndNum2(num1,num2,::minus)}")
    /*比如:
        ::plus是一种函数引用式的写法,表示将plus()作为参数传递给num1AndNum2()函数
    nums1AndNum2()函数中使用了传入的函数类型参数来决定具体的运算逻辑*/
}

其实kotlin还支持lambda表达式,匿名函数,成员引用来使用

比如以上用lambda表达式实现的话就是:

fun main(){
    val num1=100
    val num2=200
    println("num1+num2=${num1AndNum2(num1,num2){n1,n2->n1+n2}}")
    println("num1-num2=${num1AndNum2(num1,num2){n1,n2->n1-n2}}")
}

当需要连续调用同一个对象的多个方法的时候apply函数就有大用场了
比如:
/*定义了一个StringBuilder的扩展函数build()
这个函数接收一个函数类型的声明，并且返回值也是StringBuilder*/
fun StringBuilder.build(block:StringBuilder.()->Unit):StringBuilder{
    block()
    /*block可以理解成就是那个函数类型参数的参数名*/
    return this
}

fun main(){
    val list= listOf<String>("我","爱","吃","苹果")
    val result=StringBuilder().build {
        for (element in list)
            append(element)
        toString()
    }
    /*可以看到build函数的效果和apply()很像，只不过build()只能作用于StringBuilder,而apply可以作用于所有类
    这涉及泛型*/
    println(result)
}
```
## 内联函数
我们再使用lambda表达式的时候其实jvm都会创建一个新的匿名类实例，在里面的invoke()中实现具体逻辑。这样会造成额外的内存和性能开销
**内联函数就会解决lambda表达式造成的开销**
```kotlin
比如:

inline fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
    return operation(num1, num2)
}

fun plus(num1: Int,num2: Int):Int{
    return num1+num2
}

fun minus(num1: Int,num2: Int):Int{
    return num1-num2
}

fun main(){
    val num1=100
    val num2=200
    println("num1+num2=${num1AndNum2(num1,num2){n1,n2->n1+n2}}")
    println("num1-num2=${num1AndNum2(num1,num2){n1,n2->n1-n2}}")
}
```
那背后是什么原理呢?可以用图解解释.

Kotlin编译器会将Lambda表达式中的代码替换到函数类型参数调用的地方呢
[![bfDaJP.png](https://s1.ax1x.com/2022/03/10/bfDaJP.png)](https://imgtu.com/i/bfDaJP)

接下来将内联函数中的全部代码替换到函数的调用的地方
[![bfDcon.png](https://s1.ax1x.com/2022/03/10/bfDcon.png)](https://imgtu.com/i/bfDcon)

最终代码就被替换成了
[![bfDIL4.png](https://s1.ax1x.com/2022/03/10/bfDIL4.png)](https://imgtu.com/i/bfDIL4)

### noinline和crossinline
如果一个高阶函数接收了两个或者两个以上的函数类型的参数,这是我们给函数加上了inline关键字,那么kotlin编译器就会自动将所有引用的lambda表达式全部进行内联,**但是我们只想内联一个怎么办?**
#### 这时就要用到noinline
```kotlin
语法规则:
inline inlineTest(block1:()->Unit,noinline block2:()->Unit)
```
我们讲到了inline可谓是好处多多,但是为什么还要有noinline来取消呢?
- 因为内联的函数类型参数在编译的时候会进行代码替换，因此他没有真正的参数属性，
- 非内联的函数类型参数可以自由的传递给其他任何函数，因为他是一个真实的参数，
- 而内联的函数类型参数只允许拎一个内联函数，这就是他最大的局限性
```kotlin
比如:

fun printString(str:String,block:(String)->Unit){
    println("开始打印")
    block(str)
    println("结束打印")
}

fun main(){
    println("主函数开始")
    var str=""
    printString(str){s->
        println("开始lambda表达式")
        if(s.isEmpty()) return@printString
        /*lambda表达式式不允许直接使用return关键字,这里return@printString表示局部返回，返回到main中(和continue有点异曲同工之妙)*/
        println(s)
        println("lambda表达式结束")
    }
    println("主函数结束")
}

结果是:

主函数开始
开始打印
开始lambda表达式
结束打印
主函数结束
```
```kotlin
如果写成内联函数:

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

结果是:
主函数开始
开始打印
开始lambda表达式
```
```kotlin
其实把main中的调用内联函数替换成kotlin编译器内联后的代码就好理解了

fun main(){
    println("主函数开始")
    var str=""
    
    println("开始打印")
    println("开始lambda表达式")
    if(s.isEmpty()) return
    println(s)
    println("lambda表达式结束")
    println("结束打印")

    println("主函数结束")
}
一看便知,直接return 出主函数了
```
**绝大多数高阶函数是可以直接声明成内联函数的，但也有例外
```kotlin
比如:
inline fun runRunnable(block:()->Unit){
    val runnable=Runnable{
        block()
    }
    return runnable.run()
}
就会报错,但是其实再没加inline的时候是可以正确运行的
```
**原因**
- 首先在runRunnable()函数中，我们创建了一个Runnable对象
- 然后在Runnable的lambda表达式中传入的函数类型参数
- 而lambda表达式在编译的时候会被转化成匿名类的实现方式
- 也就是说以上代码实际上是在匿名类中调用了传入的函数类型参数
- 内联函数所引用的Lambda表达式允许使用return
- 但是由于我们是在匿名类中调用的函数类型参数，此时是不可能进行外层调用函数返回的
- 最多只能对匿名类中的函数调用进行返回
**也就是说如何过我们在高阶函数中创建了另外的lambda或者匿名类的实现，并且在这些实现中调用了函数类型参数，此时再将高阶函数声明成内联函数，就会报错**

### 但是这中情况下就真的无法使用内联函数吗?可以用crossinline关键字解决
```kotlin
inline fun runRunnable(crossinline block()->Unit){
    val runnable=Runnable{
        block()
    }
    return runnable.run()
}
```
crossinline关键字就像一个契约,它用于保证在内联函数的lambda表达式中一定不会使用return关键字,这样冲突就不会出现了

## 高阶函数应用
### 简化SharedPreferences用法
```kotlin
fun SharedPreferences.open(block:SharedPreferences.Editor.()->Unit){
    val editor=edit()//open拥有了SharedPreferences的上下文,就可以直接调用edit()
    editor.block()
    editor.apply()//提交数据
}
```
### 简化ContentValues用法
**额外知识点:**
kotlin 允许 `A to B`的语法结构快速创建Pair键值对
```kotlin
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
```

## 泛型和委托

### 泛型的基本使用(其实类似于c++中的模板)

1. **泛型有两种定义方式**
   定义一个泛型类
   ```kotlin
    class MyTypelate<T> {
        fun method(p0:T):T{
            return p0
        }
    }

    fun main(){
        val myTypelate=MyTypelate<Int>()
        println(myTypelate.method(123))
    }
  ```
  定义一个泛型方法
  ```kotlin
    class MyTypelate{
    fun<T> method(p0:T):T{
        return p0
    }
    }
    fun main(){
        val myTypelate=MyTypelate()
        println(myTypelate.method<Int>(123))
    }
  ```

2. **如果想要给T加一个限制,可以这样写**
```kotlin
class MyTypelate{
    fun<T:Number> method(p0:T):T{
        return p0
    }
}
这里就是指明T只能是Number类型的(比如Int,Double等等)

其实所有泛型都是可以指定成可空类型的，因为不手动指定上界的是皇后。泛型默认的上界就是Any?可空,如果想要不能空，就指定成Any就行了
```

## 类委托和委托属性
**委托是一种设计模式**
他的基本理念就是操作对象自己不回去处理某段逻辑，而是把工作委托给另一个辅助对象去处理

### 类委托
```kotlin
class MySet<T>(val helperSet:HashSet<T>):Set<T> {
    override val size: Int
        get() = helperSet.size

    override fun contains(element: T): Boolean {
        return helperSet.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return helperSet.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return helperSet.isEmpty()
    }

    override fun iterator(): Iterator<T> {
        return helperSet.iterator()
    }
}
//MySet的构造函数中接受了一个HashSet参数,这就相当于一个辅助对象,
//然后在所有的方法中我们都没有自己实现，而是委托给了HashSet，这就是一种委托模式了
```
但是如果我们需要实现的接口中的参数有几千个怎么办？一个个敲吗?java中好像只能如此,但是kotlin中用了类委托机制
```kotlin
class MySet<T>(val helperSet:HashSet<T>):Set<T> by helperSet{
}
这一行解决和上面的效果一毛一样
如果想要重新实现那个方法写上去即可
```

### 委托属性
1. 委托属性的语法结构
   ```kotlin
    class MyClass{
        val p by Delegate()
    }
   ```
    by关键字连接了左边的p属性和右边的Belegate()实例，这就意味着p属性的具体实现委托给了Delegate()类去完成
    - 比如当调用p属性的时候就会调用Delegate()类的getValue()方法
    - 当给p赋值的时候其实是给Delegate()类的setValue()方法
2. 具体实现
   ```kotlin
    class Delegate{
        private var propValue:Any?=null
        //第一个参数是说明Delegate类的委托功能只能在MyClass中使用
        //第二个参数是KProperty<*>是kotlin中的一个属性操作类
        //<*>这种写法表示我不知道或者不关心泛型的具体类型,类似于java中的<?>用法
        operator fun getValue(myClass: MyClass,prop:KProperty<*>):Any?{
            return propValue
        }

        //第三个参数是具体赋值给委托属性的值，这个参数类型必须和getValue()的返回值一样
        operator fun setValue(myClass: MyClass,prop: KProperty<*>,value:Any?){
            propValue=value
        }
    }

    class MyClass{
        val p by Delegate()
    }
   ```

## 泛型的高级特性

### 对泛型进行实化

#### 背景
jdk1.5之前java没有泛型功能，当时诸如List这样的数据结构可以存储任意类型的数据，取出来的时候也必须要手动向下转型(麻烦,危险),比如我们在List中存储了Integer和String类型的参数，取出来的时候要把他们转型成同一类型，然后就会报类型转换异常
#### 类型擦除机制
jdk1.5开始引入了泛型功能。
**类型擦除机制是怎么实现的呢?**
- 泛型对于类型的约束只在编译期间存在，运行的时候仍然按照jdk1.5之前的机制运行，JVM是识别不出来我们在代码中指定的泛型类型的。比如我们创建一个List<String>的集合，虽然在编译时期只能向集合中添加字符串类型的元素，但是在运行期间JVM并不能知道他本来只打算包含那个类型的元素，JVM只能识别出这个集合是个List
- 如今所有基于JVM的语言的泛型功能都是通过类型擦除机制实现的，也包括kotlin
- 但是不同的是kotlin提供了内联函数的概念,内联函数的代码会在编译期间自动替换到调用他的地方，这样就不存在什么泛型被类型擦除的问题了，因为代码在编译的时候会直接使用实际的类型来替代内联函数中的泛型声明
[![bXsFne.png](https://s1.ax1x.com/2022/03/14/bXsFne.png)](https://imgtu.com/i/bXsFne)

**具体要怎么写才能把泛型实化?**
- 首先该函数必须是一个内联函数，也就是要用inline关键字来修饰该函数
- 其次在声明泛型的地方必须加上reified关键字来表示该泛型要进行实化
```kotlin
inline fun <reified T> getGenericType(){
}
```
**可以实现一个获取泛型实际类型的功能**
```kotlin
inline fun <reified T>getGenericType()=T::class.java

fun main(){
    val result1= getGenericType<String>()
    println(result1)
}
会把泛型的实际类型打印出来
```

### 泛型实化的应用
```kotlin
val intent=Intent(context,MainActivity::class.java)
context.startAcivity(intent)

每次都写一个MainActivity::class.java不太方便
我们可以泛型实化利用泛型实化写成这样

inline fun <reified T> startActivity(context: Context){
    val intent=Intent(context,T::class.java)
    context.startActivity(intent)
}

startActivity<MainActivity>(context)

但是这样写就几乎用不到Intent里面的一些参数了，所以我们可以使用高级函数来优化

inline fun <reified T> startActivity(context: Context,block:Intent.()->Unit){
    val intent=Intent(context,T::class.java)
    intent.block()
    context.startActivity(intent)
}

startActivity<MainActivity>(context){
    putExtra("添加信息")
}
```

### 泛型的协变
**重要的约定**
- 在泛型类或者泛型接口的方法中，他的参数列表是接收数据的地方，叫做in位置
- 他的返回值是输出数据的地方，因此叫做out位置
[![bjawUP.png](https://s1.ax1x.com/2022/03/15/bjawUP.png)](https://imgtu.com/i/bjawUP)

**一个例子**
```kotlin
open class Person(val name:String,val age:Int)
class Teacher(name:String,age:Int):Person(name,age)
class Student(name:String,age:Int):Person(name,age)

我们很容易看出Student是Person的子类，如果某个方法需要传入List<Person>的话我们能不能传入List<Student>呢?
答案是java中是不能的，因为List<Student>不能够成为List<Person>的子类,否则可能存在类型转换的问题
```
**这里再举一个例子说明**
```kotlin
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

/*handleSimpleData(data)如果能够正确运行的话，那么当前的data中既有student也有teacher
但是开始初始化的时候已经指定了SimpleData中的泛型是Student，所以get返回值应该是一个student，但是data里面却有一个teacher
这时就会发生类型转换异常了*/
```

#### 泛型协变的定义
如果定义了一个`MyClass<T>`的泛型类,其中`A是B`的子类型，同时`MyClass<A>`也是`MyClass<B>`的子类型，那么我们就可以称`MyClass`在`T`这个泛型上是协变的
```kotlin
open class Person(val name:String,val age:Int)
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
}
```
**接下来我们来看看List的简化版源码**
```kotlin
public interface List<out E>:Collection<E>{
    override val size: Int

    override fun contains(element: @UnsafeVariance E): Boolean 

    override fun isEmpty(): Boolean 

    override fun iterator(): Iterator<E> 
    
    public operator fun get(index:Int)
}
```
`List`在`E`之前加上了`out`，说明`List`在泛型E上是可以协变的，
但`contains的in里面出现了泛型``E`，这就会有类型转换的安全隐患
但是其实`contains只`是为了比较字符，并不会改变`List`里面的元素，所以我们会加上一个`@UnsafeVariance`来说明允许E出现在这个`in位`置


### 泛型的逆变
#### 定义
有`MyClass<T>`的泛型类，其中`A是B`的子类型，同时`MyClass<B>又是MyClass<A>`的子类型，那么我们就称`MyClass`是在T这个泛型上是逆变的
[![bjrtKS.png](https://s1.ax1x.com/2022/03/15/bjrtKS.png)](https://imgtu.com/i/bjrtKS)
**看看例子好学习**
```kotlin
interface Transformer<T>{
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
```
这里就要用到逆变了
```kotlin
interface Transformer<in T>{
    fun transform(t:T):String//T类型的参数参数会在经过转化变成String类型
}

泛型T前面加上一个T，就指明了T只能出现在in位置

同样的也是可以用@UnsafeVariance来把T放在out位置，但是这样就又容易引起类型转换异常了
```
**哎呀，逆变和协变还是有点不理解**


## 使用协程编写高效的并发程序
可以把协程理解成轻量级的线程，使用协程可以仅在编程语言的层面就能实现不同协程之间的变换，从而提高并发编程的运行效率
**比如**
```kotlin
fun foo(){
    print(1)
    print(2)
    print(3)
}

fun bar(){
    print(4)
    print(5)
    print(6)
}
```
在没有开启协程的情况下，理论上先后调用foo()和bar()得到的结果应该是123456，而如果使用了协程，在协程A中调用foo(),在协程B中调用bar()，，虽然他们运行在同一个线程当中，但是在运行foo()时随时都会被挂起，转而去执行bar()，相反一样，最总的输出结果就不确定了。
协程允许我们在单线程中模拟多线编程的效果

### 协程的基本用法
Kotlin并没有把协程纳入标准库的API中
```kotlin
需要导包
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3'
//纯kotlin程序的包
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3'
//Android程序的包
```
- `GlobalScope.launch()`
  ```kotlin
    fun main(){
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
    }
  ```
- `runBlock()`
  ```kotlin
    fun main(){
        runBlocking{
            //runBlocking也会创建一个协程的作用域
            //但是他能保证协程作用域内的所有代码和子协程没有全部执行完的时候就会一直阻塞当前线程
            //但是他一般只在测试的时候用，因为它会引起性能上的问题
            println("程序在协程里面执行")

            delay(1500)

            println("程序运行完毕")
        }

        //想要协程中的代码运行完，就大概把协程阻塞一段时间，确保协程运行完了就行
        Thread.sleep(1000)
        //但是这样写只是让线程沉睡了1秒钟，要是协程在这一秒钟里面没有运行完，也会被强制中断
    }
  ```
- `launch()`
  ```kotlin
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
  ```
  但是当launch里面逻辑复杂的时候,就需要把逻辑写在单独的方法里面了，这是就有问题了，launch是由作用域要求的,那么再函数中就无法使用delay()了，**kotlin为我们提供了一个suspend关键字来解决这个问题**
  ```kotlin
    suspend fun printWord(){
        println("0")
        delay(1000)
    }
    suspend只能把一个函数声明成挂起函数，是无法给他提供作用域的，比如再printWord()中是无法使用launch的，这个问题可以用coroutineScope()函数解决
  ```
- `coroutineScope()`
  ```kotlin
    suspend fun printDot() = coroutineScope {// coroutineScope也是一个挂起函数，因此可以在任何其他挂起函数中调用
        //他的特点时会继承外部的协程作用域并且创建一个子作用域，借助这个特点就可以给任意挂起函数添加协程作用域了
        launch { 
            println(".")
            delay(1000)
        }
    }
  ```
  ```kotlin
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
  ```

##### 更多的作用域构建器
runBlocking由于会阻塞线程，因此只建议再测试环境下使用。而GlobalScope.launch由于每次创建的都是顶层函数，一般也不建议使用，除非创建者本身就是想创建一个顶层协程。

下面例举是的实际项目中比较常用的写法：
```kotlin
fun test6() {
    val job = Job()//创建一个Job对象
    val scope = CoroutineScope(job)//把job对象传到CoroutineScope函数中，会返回一个CoroutineScope对象
    scope.launch {

    }
    job.cancel()
}
```
这里先创建了一个`CoroutineScope`对象，有了这个对象之后就随时可以调用它的`launch`函数来创建协程。

这样创建的协程都会被关联到这个job对象中，取消的时候直接调用`job.cancel()`即可。

由于这个函数的返回值永远是一个Job对象,所以这种方式获取不到协程的执行结果，如何获取协程的执行结果呢，那就需要借助**async函数**来实现。
```kotlin
fun main() {
    runBlocking {
        // 调用await()方法来获取async函数的执行结果
        val result = async {
            //async函数必须要在协程作用域中使用，他会创建一个新的子协程并且返回一个Deferred对象，如果想要得到执行结果，调用await()函数即可
            5 + 5
        }.await()
        println(result)
    }
}
```
我们来查看输出结果

当调用await()方法时，如果代码块中的代码还没执行完，那么await()方法会将当前协程阻塞住，直到可以获取async函数的执行结果。

我们编写代码加以证实：
```kotlin
fun main() {
    runBlocking {
        // 串行执行示例
        val start = System.currentTimeMillis()
        val result1 = async {
            delay(1000)
            5 + 5
        }.await()
        val result2 = async {
            delay(1000)
            4 + 6
        }.await()
        println("result is ${result1 + result2}")
        val end = System.currentTimeMillis()
        println("cost ${end - start} ms.")
    }
}
```
运行后注意观察执行时间。

我们看到运行耗时是2105ms，说明这两个async函数确实是串行的关系，前一个执行完了后一个才能执行。

换一种写法就能让async函数同时执行，实现并行效果。
```kotlin
fun main() {
    runBlocking {
        val start = System.currentTimeMillis()
        val deferred1 = async {
            delay(1000)
            5 + 5
        }
        val deferred2 = async {
            delay(1000)
            4 + 6
        }
        println("result is ${deferred1.await() + deferred2.await()}")//先把所有结果计算完再await()，因为await()会阻塞当前协程
        val end = System.currentTimeMillis()
        println("cost ${end - start} ms.")
    }
}
```
执行函数，我们再来注意执行耗时。

只有1094ms，很显然是并行执行的结果。

- 最后来学习一个比较特殊的作用域构建器：**withContext()函数**。
```kotlin
fun test10() {
    runBlocking {
        //线程参数有三种
        // Dispatchers.Default: 低并发线程策略，如计算密集型任务
        // Dispatchers.IO: 高并发线程策略，当执行的代码大多数时间是在阻塞和等待中，如网络请求
        // Dispatchers.Main: 更高的并发量，不会开启子线程，而是在Android主线程中运行，这个值只能在android项目中使用
        val result = withContext(context = Dispatchers.Default) {
            5 + 5
        }
        println(result)
    }
}
```
withContext的最后一行代码的执行结果会被作为函数的返回值返回。

几种线程策略已经在上述代码中给出解释。其实其他所有的函数都是可以指定这样一个线程参与的，只不过**withContext()函数**是强制要求指定的，而其他的函数则是可选的。

### 使用协程简化回调的写法
**suspendCoroutine能将传统回调机制的写法大幅简化**
- suspendCoroutine()函数必须要在协程作用域或者挂起函数中才能调用
- 接收一个lambda表达式，主要作用就是将当前的协程挂起，然后在一个不同的线程中执行lambda表达式中代码
- lambda表达式参数列表会传入一个Continuation参数，调用他的resume方法或者resumeWithException()可以让协程恢复执行

```kotlin
suspend fun request(address: String): String {
    return suspendCoroutine { continuation ->
        HttpUtil.sendHttpRequest(address, object HttpCallbackListener {
            override fun onFinish(response: String) {
                continuation.resume(response)//恢复被挂起的协程，response成为suspendCoroutine函数的返回值
            }

            override fun onError(e: Exception) {
                continuation.resumeWithException(e)
            }
        })
    }
}

suspend fun getBaiduResponse() {
    try {
        val response = request("https://www.baidu.com")
        //当request()发起请求，这个协程就会挂起，直到获取数据或者报异常
    } catch (e: Exception) {
         //异常处理
    }
}
```

**suspendCoroutine简化Retrofit发起网络请求**
```kotlin
val appService = ServiceCreator.create<AppService>()
appService.getAppData().enqueue(object : Callback> {
    override fun onFailure(call: Call, t: Throwable) {
        // 在这里对异常情况进行处理
    }

    override fun onResponse(call: Call, response: Response>) {
            // 得到服务器返回的数据
    }
})
```
使用suspendCoroutine 函数，我们马上就能对上诉写法进行大幅度的简化。

由于不同的Service 接口返回的数据类型也不同，所以这次我们不能像刚才那样针对具体的类型进行编程了，而是要使用泛型的方式。定义一个await() 函数，代码如下所示：
```kotlin
suspend fun<T> Call<T>.await():T{
    return suspendCoroutine {
        enqueue(object :Callback{
            override fun onFailure(call: Call, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()
                if (body != null){
                    it.resume(body)
                }else{
                    it.resumeWithException(RuntimeException(" response body is null"))
                }
            }

        })
    }
}
```
首先await() 函数仍然是一个挂起函数，然后我们给它声明了一个泛型T，并将await() 函数定义成了Call 的扩展函数，这样所有返回值是Call 类型的Retrofit 网络请求接口就都可以直接调用await() 函数了。

接着，await() 函数中使用了suspendCoroutine 函数来挂起当前协程，并由于扩展函数的原因，我们现在拥有了Call 对象的上下文，那么这里就可以直接调用enqueue() 方法让Retrofit 发起网络请求。接下来，使用同样的方式对 Retrofit 响应的数据或网络请求失败的情况进行处理就可以了。另外还有一点需要注意，在onResponse() 回调当中，我们调用body() 方法解析出来的对象是可能为空的。如果为空的话，这里的做法是手动抛出一个异常，你也可以根据自己的逻辑进行更加合适的处理。

有了await() 函数之后，我们调用所有Retrofit 的Service 接口都会变得极其简单，比如刚才回调的功能就可以使用如下写法进行实现：
```kotlin
suspend fun getAppData(){
    try {
        val appList = ServiceCreator.create().getAppData().await()
        // 对服务器响应的数据进行处理
    }catch (e:Exception){
        // 对异常情况进行处理
    }
}
```
it 发起网络请求。接下来，使用同样的方式对 Retrofit 响应的数据或网络请求失败的情况进行处理就可以了。另外还有一点需要注意，在onResponse() 回调当中，我们调用body() 方法解析出来的对象是可能为空的。如果为空的话，这里的做法是手动抛出一个异常，你也可以根据自己的逻辑进行更加合适的处理。

有了await() 函数之后，我们调用所有Retrofit 的Service 接口都会变得极其简单，比如刚才回调的功能就可以使用如下写法进行实现：
```kotlin
suspend fun getAppData(){
    try {
        val appList = ServiceCreator.create().getAppData().await()
        // 对服务器响应的数据进行处理
    }catch (e:Exception){
        // 对异常情况进行处理
    }
}
```

### 泛型上界使用测试
```kotlin
fun <T:Comparable<T>>minnT(vararg nums: T):T{
    var minNum=nums[0]
    for (num in nums){
        if (num<minNum){
            minNum=num
        }
    }
    return minNum
}
```
```kotlin
封装Toast实验
fun <T>T.showToast(context:Context,duration: Int=Toast.LENGTH_SHORT){
    Toast.makeText(context,this.toString(),duration).show()
}
```
```kotlin
简化Snackbar用法
fun <T:String>View.showSnackbar(text:String, action:T?=null,duration: Int=Snackbar.LENGTH_SHORT,
                      block: (() -> Unit)? =null){
    val snackbar=Snackbar.make(this,text,duration)
    if(action!=null&&block!=null){
        snackbar.setAction(action.toString()){
            block()
        }
    }
    snackbar.show()
}
```

## 使用DSL构建专有的语法结构
**DSL的全称是领域特定语言**，他是编程语言赋予开发者的一种特殊能力,通过他我们能编写出一些看似脱离其原始语言结构的代码，从而构建出一种转悠的语法结构
**比如**infix函数就属于DSL

### 学习高阶函数来实现DSL
**导包**
```xml
dependencies {
    ···
    implementation 'com.squareup.retrofit2:retrofit:2.6.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.1'
}
```
**kotlin模拟groovy导包**
```kotlin
class Dependency{
    val libraries=ArrayList<String>()
    //libraries中添加依赖库

    fun implementation(lib:String){
        libraries.add(lib)
    }
}

fun dependencies(block: Dependency.()->Unit):List<String>{
    val dependency=Dependency()
    dependency.block()
    return dependency.libraries
}

fun main(){
    val libs=dependencies {
        implementation("com.example.de1")
        implementation("com.example.de2")
    }

    for (lib in libs)
        println(lib)
}
```
**模拟动态生成表格所对应的HTML代码**
```html
<table>
    <tr>
        <td>Apple</td>
        <td>Grape</td>
        <td>Orange</td>
    </tr>
    <tr>
        <td>Pear</td>
        <td>Banana</td>
        <td>Watermelon</td>
    </tr>
</table>
```
```kotlin
class Td {
    var content=""
    fun html()="\n\t\t<td>$content</td>"
    /*<td>是一个单元格的意思*/
}

class Tr{
    private val children=ArrayList<Td>()

    fun td(block:Td.()->String){
        val td=Td()
        td.content=td.block()
        children.add(td)
    }

    fun html():String{
        val builder=StringBuilder()
        builder.append("\n\t<tr>")
        for (childTag in children)
            builder.append(childTag.html())
        builder.append("\n\t</tr>")
        return builder.toString()
    }
}

class Table{
    private val children=ArrayList<Tr>()

    fun tr(block: Tr.() -> Unit){
        val tr=Tr()
        tr.block()
        children.add(tr)
    }

    fun html():String{
        val builder=StringBuilder()
        builder.append("<table>")
        for (childTag in children)
            builder.append(childTag.html())
        builder.append("\n</table>")
        return builder.toString()
    }
}

fun table(block: Table.() -> Unit):String{
    val table=Table()
    table.block()
    return table.html()
}

fun main() {
    val html = table {//调用table的时候会自动创建一个Table,然后代码块会拥有Table的上下文
        tr {
            td { "Apple" }
            td { "Grape" }
            td { "Orange" }
        }
        tr {
            td { "Pear" }
            td { "Banana" }
            td { "Watermelon" }
        }
    }
    println(html)
}
```

## 嵌套类和内部类
1.嵌套类(Nested Classes)
类可以嵌套在其他类中,不能访问外部类成员:
```kotlin
    class Outer {
        private val bar: Int = 1
        class Nest {
            //嵌套类不能访问外部类成员,相当于java的static 静态内部类
            fun foo() = 2
        }
    }
    fun main(args: Array<String>) {
        //创建嵌套类Nest对象,不需要外部类Outer对象
        println(Outer.Nest().foo()) //输出2
    }
```
2.内部类(Inner classes)
类标记为inner,可以访问外部类成员:
```kotlin
    class Outer {
        private val bar: Int = 1
        inner class Inner {
            //内部类可以访问外部类成员,可看作外部类对象的一个成员
            fun foo() = bar
        }
    }
    fun main(args: Array<String>) {
        //创建内部类Inner对象,需要外部类Outer对象
        val outer = Outer()
        println(outer.Inner().foo()) //输出1
    }
```
3.匿名内部类(Anonymous inner classes)
用对象表达式,创建匿名内部类的实例:
```kotlin
    window.addMouseListener(
        object: MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                ...
            }

            override fun mouseEntered(e: MouseEvent){
                ...
            }
        }
    )
```