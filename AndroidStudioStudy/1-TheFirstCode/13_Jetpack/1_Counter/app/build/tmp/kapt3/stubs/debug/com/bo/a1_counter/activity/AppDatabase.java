package com.bo.a1_counter.activity;

import java.lang.System;

@androidx.room.Database(version = 3, entities = {com.bo.a1_counter.activity.User.class, com.bo.a1_counter.activity.Book.class})
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lcom/bo/a1_counter/activity/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "bookDao", "Lcom/bo/a1_counter/activity/BookDao;", "userDao", "Lcom/bo/a1_counter/activity/UserDao;", "Companion", "app_debug"})
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final com.bo.a1_counter.activity.AppDatabase.Companion Companion = null;
    private static final androidx.room.migration.Migration MIGRATION_1_2 = null;
    private static final androidx.room.migration.Migration MIGRATION_2_3 = null;
    private static com.bo.a1_counter.activity.AppDatabase instance;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bo.a1_counter.activity.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.bo.a1_counter.activity.BookDao bookDao();
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/bo/a1_counter/activity/AppDatabase$Companion;", "", "()V", "MIGRATION_1_2", "Landroidx/room/migration/Migration;", "MIGRATION_2_3", "instance", "Lcom/bo/a1_counter/activity/AppDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @kotlin.jvm.Synchronized()
        public final synchronized com.bo.a1_counter.activity.AppDatabase getDatabase(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}