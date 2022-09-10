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






















