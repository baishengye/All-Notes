package com.bo.a1_counter.activity;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H\'\u00a8\u0006\b"}, d2 = {"Lcom/bo/a1_counter/activity/BookDao;", "", "insertBook", "", "book", "Lcom/bo/a1_counter/activity/Book;", "loadAllBooks", "", "app_debug"})
public abstract interface BookDao {
    
    @androidx.room.Insert()
    public abstract long insertBook(@org.jetbrains.annotations.NotNull()
    com.bo.a1_counter.activity.Book book);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "select * from Book")
    public abstract java.util.List<com.bo.a1_counter.activity.Book> loadAllBooks();
}