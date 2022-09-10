package com.bo.testthread;

//=============================================================================================================================

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 */
class TestReentrantLock{
    static class Alipay{
        private final Condition condition;
        private ReentrantLock reentrantLock;//可重入锁
        private volatile double[] accounts;//accounts[i]:i这个账户的钱数

        /**
         * 初始化
         * @param n
         * @param money
         */
        public Alipay(int n,double money){
            accounts=new double[n];
            reentrantLock = new ReentrantLock();

            condition = reentrantLock.newCondition();//条件对象

            Arrays.fill(accounts, money);
        }

        public void transfer(int from,int to,int amount) throws InterruptedException{
            reentrantLock.lock();
            try {
                while(accounts[from]<amount){
                    condition.await();
                }

                accounts[from]-=amount;
                accounts[to]+=amount;
                condition.signalAll();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args){
        Alipay alipay = new Alipay(2, 10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    alipay.transfer(0,1,2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    alipay.transfer(1,0,2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

//=============================================================================================================================
/**
 * 重量级锁(同步函数)
 */
class TestSynchronizedFunc{
    static class Alipay{
        private volatile double[] accounts;//accounts[i]:i这个账户的钱数

        /**
         * 初始化
         * @param n
         * @param money
         */
        public Alipay(int n,double money){
            accounts=new double[n];

            Arrays.fill(accounts, money);
        }

        public synchronized void transfer(int from,int to,int amount) throws InterruptedException{
            while(accounts[from]<amount){
                wait();
            }
            accounts[from]-=amount;
            accounts[to]+=amount;
            notifyAll();
        }
    }

    public static void main(String[] args){
        Alipay alipay = new Alipay(2, 10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    alipay.transfer(0,1,2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    alipay.transfer(1,0,2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

//=============================================================================================================================
/**
 * 重量级锁(同步代码块)
 */
class TestSynchronizedObj{

    private static Object lock;

    static class Alipay{
        private volatile double[] accounts;//accounts[i]:i这个账户的钱数

        /**
         * 初始化
         * @param n
         * @param money
         */
        public Alipay(int n,double money){
            accounts=new double[n];
            lock = new Object();

            Arrays.fill(accounts, money);
        }

        public void transfer(int from,int to,int amount) throws InterruptedException{
            synchronized (lock){
                accounts[from]-=amount;
                accounts[to]+=amount;
            }
        }
    }

    public static void main(String[] args){
        Alipay alipay = new Alipay(2, 10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    alipay.transfer(0,1,2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    alipay.transfer(1,0,2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

//=============================================================================================================================
/**
 * 轻量级锁:有序，禁止指令重排，不原子
 */
class TestVolatile{
    public volatile int inc=0;

    public void increase(){
        inc++;
    }

    public static void main(String[] args) {
        TestVolatile testVolatile = new TestVolatile();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    testVolatile.increase();
                }
            }).start();
        }

        //如果子线程还有的话就主线程使用yield()函数让出cpu
        while(Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(testVolatile.inc);
    }
}

//=============================================================================================================================
/**
 * 不变式使用Volatile
 * 不能保证原子性
 */
class TestVolatileNumberRanger{
    public volatile int lower,upper;

    public int getLower() {
        return lower;
    }

    public void setLower(int lower) {
        if(lower>upper){
            throw new IllegalArgumentException("lower>upper");
        }
        this.lower = lower;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        if(upper<lower){
            throw new IllegalArgumentException("upper<lower");
        }
        this.upper = upper;
    }

    public static void main(String[] args) {

    }
}



