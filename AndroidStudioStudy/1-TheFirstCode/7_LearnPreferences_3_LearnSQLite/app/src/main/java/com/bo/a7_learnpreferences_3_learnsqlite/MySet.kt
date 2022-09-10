package com.bo.a7_learnpreferences_3_learnsqlite

import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.chip.ChipDrawable
import kotlin.reflect.KProperty

/*class MySet<T>(val helperSet:HashSet<T>):Set<T> {
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
}*/
//MySet的构造函数中接受了一个HashSet参数,这就相当于一个辅助对象,
//然后在所有的方法中我们都没有自己实现，而是委托给了HashSet，这就是一种委托模式了

/*class MySet<T>(val helperSet:HashSet<T>):Set<T> by helperSet{
}*/

class Delegate{
    private var propValue:Any?=null
    operator fun getValue(myClass: MyClass,prop:KProperty<*>):Any?{
        return propValue
    }
    operator fun setValue(myClass: MyClass,prop: KProperty<*>,value:Any?){
        propValue=value
    }
}

class MyClass{
    val p by Delegate()
}
