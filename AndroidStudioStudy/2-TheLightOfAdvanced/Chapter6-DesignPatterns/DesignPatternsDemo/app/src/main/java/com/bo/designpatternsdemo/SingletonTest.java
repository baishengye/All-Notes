package com.bo.designpatternsdemo;

import java.io.ObjectStreamException;

/**
 * 单例测试
 */
public class SingletonTest {
}

/**
 * 饿汉:线程安全
 */
class Singleton1{
    private static Singleton1 instance=new Singleton1();
    public static Singleton1 getInstance() {
        return instance;
    }
}

/**
 * 懒汉：线程不安全
 */
class Singleton2{
    private static Singleton2 instance;

    public static Singleton2 getInstance() {
        if(instance==null){
            instance=new Singleton2();
        }
        return instance;
    }
}

/**
 * 懒汉:线程安全
 */
class Singleton3{
    private static Singleton3 instance;

    public static synchronized Singleton3 getInstance(){
        if(instance==null){
            instance=new Singleton3();
        }
        return instance;
    }
}

/**
 * 懒汉:双检锁
 */
class Singleton4{
    private static Singleton4 instance;

    public static Singleton4 getInstance(){
        if(instance==null){
            synchronized (Singleton4.class){
                if(instance==null){
                    instance=new Singleton4();
                }
            }
        }
        return instance;
    }
}

/**
 * 静态内部类
 */
class Singleton5{
    private static Singleton5 instance;

    static class GetSingleton{
        private static final Singleton5 instance=new Singleton5();
    }
}

/**
 * 枚举类
 */
enum  Singleton6{
    INSTANCE;
}
