package com.bo.a2_kotlinlearning

import java.lang.Math.max

fun max2Int(a:Int,b:Int): Int {
    return if(a>b){
        a
    }else{
        b
    }
}

fun maxnInt(vararg nums:Int):Int{
    var maxNum:Int=nums[0]
    for (num in nums){
        maxNum=max(num,maxNum)
    }
    return maxNum
}

/*T : Comparable ，这里的Comparable是类型T的上界。
也就是说类型T代表的都是实现了Comparable接口的类。
这样等于告诉编译器它们都是事先了CompareTo方法。如果没有声明上界，就无法使用>操作*/
fun <T:Comparable<T>>maxnT(vararg nums:T):T{
    var maxNum:T=nums[0]
    for (num in nums){
        if(maxNum<num)
            maxNum=num
    }
    return maxNum
}

fun <T:Comparable<T>>minnT(vararg nums: T):T{
    var minNum=nums[0]
    for (num in nums){
        if (num<minNum){
            minNum=num
        }
    }
    return minNum
}

fun main(){
    println(minnT(1.0, 2.0,1.2,2.0,5.0,8.2,2.0,5.04,7.0,8.0,9.0,5.0,6.0,4.0,5.0,2.0, 3.0, 14.0, 5.0))
    println(maxnT(1.0,2.0,1.2,2.0,5.0,8.2,2.0,5.04,7.0,8.0,9.0,5.0,6.0,4.0,5.0,2.0, 3.0, 14.0, 5.0))
}