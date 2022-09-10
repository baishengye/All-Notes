package com.bo.a1_counter.activity;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\'J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\rH\'J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\r2\u0006\u0010\u000f\u001a\u00020\u0007H\'J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'\u00a8\u0006\u0011"}, d2 = {"Lcom/bo/a1_counter/activity/UserDao;", "", "deleteUser", "", "user", "Lcom/bo/a1_counter/activity/User;", "deleteUserByLastName", "", "lastName", "", "insertUser", "", "loadAllUsers", "", "loadUsersOrderThan", "age", "updateUser", "app_debug"})
public abstract interface UserDao {
    
    @androidx.room.Insert()
    public abstract long insertUser(@org.jetbrains.annotations.NotNull()
    com.bo.a1_counter.activity.User user);
    
    @androidx.room.Update()
    public abstract void updateUser(@org.jetbrains.annotations.NotNull()
    com.bo.a1_counter.activity.User user);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "select * from User")
    public abstract java.util.List<com.bo.a1_counter.activity.User> loadAllUsers();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "select * from User where age>:age")
    public abstract java.util.List<com.bo.a1_counter.activity.User> loadUsersOrderThan(int age);
    
    @androidx.room.Delete()
    public abstract void deleteUser(@org.jetbrains.annotations.NotNull()
    com.bo.a1_counter.activity.User user);
    
    @androidx.room.Query(value = "delete from User where lastName=:lastName")
    public abstract int deleteUserByLastName(@org.jetbrains.annotations.NotNull()
    java.lang.String lastName);
}