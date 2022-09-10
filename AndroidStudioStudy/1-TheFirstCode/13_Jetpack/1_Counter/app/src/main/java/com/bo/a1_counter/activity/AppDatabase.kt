package com.bo.a1_counter.activity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(version = 3,entities = [User::class,Book::class])
//数据库注解(版本号,实体类(可以有多个实体类,用逗号隔开))
abstract class AppDatabase: RoomDatabase(){
//AppDatabase必须是继承自RoomDatabase的抽象类
    abstract fun userDao():UserDao
    //获取userDao的函数也是声明抽象函数，具体实现有Room在底层自动完成

    abstract fun bookDao():BookDao

    companion object{

        private val MIGRATION_1_2= object : Migration(1,2) {
            //Migration(1,2)表示从数据库版本1升级到2就执行这个匿名类将数据库升级
            //MIGRATION_1_2命名比较讲究表示从1升级到2
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("" +//还是要用SQL语句来操作
                        "create table Book (" +
                            "id integer primary key autoincrement not null," +
                            "name text not null," +
                            "pages integer not null)")
            }
        }

        private val MIGRATION_2_3=object :Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table Book add column author text not null default 'Unknown'")
            }
        }
        //伴生类+单例-》静态方法用单例模式获取App的数据库实例,因为原则上全局应该只有一个App的数据库实例
        private var instance:AppDatabase?=null

        @Synchronized
        fun getDatabase(context: Context):AppDatabase{
            //如果AppDatabase实例本来就有就不用再创建了，直接用
            instance?.let {
                return it
            }

            /*否则就要创建一个AppDatabase实例*/
            return Room.databaseBuilder(
                context.applicationContext,//这个参数必须要用applicationContext，不然有内存泄露的风险
                AppDatabase::class.java,//指定创建的AppDatabase的类型
                "app_database"//指定数据库的名称
                )//.allowMainThreadQueries()//只有在测试的时候可以用这句代码，允许在主线程中进行查询数据库操作
                .fallbackToDestructiveMigration()//摧毁重建
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
                .apply {
                    instance=this
            }
        }
    }
}