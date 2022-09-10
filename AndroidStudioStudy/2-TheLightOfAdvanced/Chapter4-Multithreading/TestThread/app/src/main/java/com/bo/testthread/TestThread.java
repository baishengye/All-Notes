package com.bo.testthread;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//=============================================================================================================================
/**
 * Thread
 */
class TestThread extends Thread{
    @Override
    public void run() {
        System.out.println("hello thread");
    }

    public static void main(String[] args) {
        new TestThread().start();
    }
}

//=============================================================================================================================
/**
 * Runnable
 */
class TestRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("Hello Runnable");
    }

    public static void main(String[] args) {
        new Thread(new TestRunnable()).start();
    }
}

//=============================================================================================================================
/**
 * Callable
 */
class TestCallable implements Callable {

    @Override
    public String call() throws Exception {
        return "Hello callable";
    }

    public static void main(String[] args) {
        TestCallable testCallable = new TestCallable();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future submit = executorService.submit(testCallable);
        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

//=============================================================================================================================
/**
 * 安全终止线程
 */
class TestStopThread{
    public static class TestRunnable implements Runnable{
        private long i;
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                i++;
                System.out.printf("i=%s  ",i);
            }
            System.out.println("\nstop");
        }
    }

    public static void main(String[] args) throws InterruptedException{
        TestRunnable testRunnable = new TestRunnable();
        Thread thread = new Thread(testRunnable,"testThread");
        thread.start();

        TimeUnit.MILLISECONDS.sleep(20);
        thread.interrupt();
    }
}

class TestBoolStopThread{
    public static class TestRunnable implements Runnable{
        private long i;
        private volatile boolean on = true;
        @Override
        public void run() {
            while(on){
                i++;
                System.out.printf("i=%s  ",i);
            }
            System.out.println("\nstop");
        }

        public void cancel(){
            on = false;
        }
    }

    public static void main(String[] args) throws InterruptedException{
        TestRunnable testRunnable = new TestRunnable();
        Thread thread = new Thread(testRunnable,"testThread");
        thread.start();

        TimeUnit.MILLISECONDS.sleep(20);
        testRunnable.cancel();
    }
}

