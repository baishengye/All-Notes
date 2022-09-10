package com.bo.a1_context

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/*
data class Person(var name:String="",var age:Int=0) : Serializable{
}*/

/*
data class Person(var name:String="",var age:Int=0):Parcelable{//实现Parcelable接口

    override fun describeContents(): Int {//
        return 0;//序列化返回0就完事了
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeString(name)//把Person中的字段写到parcel中
        parcel?.writeInt(age)
    }

    companion object CREATOR:Parcelable.Creator<Person>{
        override fun createFromParcel(parcel: Parcel?): Person {
            //从parcel中把person的各个字段读出来，从parcel读出来的顺序要和写进去的顺序一样
            val person=Person()
            person.name=parcel?.readString()?:""
            person.age=parcel?.readInt()?:0
            return person
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)//创建一个数组来存放实际对象
        }
    }
}
*/

@Parcelize
data class Person(var name:String="",var age:Int=0):Parcelable {//实现Parcelable接口
}
