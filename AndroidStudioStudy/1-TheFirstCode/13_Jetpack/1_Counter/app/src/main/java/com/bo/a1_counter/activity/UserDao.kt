package com.bo.a1_counter.activity

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insertUser(user:User):Long//插入返回主键

    @Update
    fun updateUser(user: User)

    @Query("select * from User")
    fun loadAllUsers():List<User>

    @Query("select * from User where age>:age")
    fun loadUsersOrderThan(age:Int):List<User>

    @Delete
    fun deleteUser(user: User)

    @Query("delete from User where lastName=:lastName")
    //当要通过传入非实体参数增删改数据的时候必须要写SQL语句，而且只能用@Query注解
    fun deleteUserByLastName(lastName:String):Int
}