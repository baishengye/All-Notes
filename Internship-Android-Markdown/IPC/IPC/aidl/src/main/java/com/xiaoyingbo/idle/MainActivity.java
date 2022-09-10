package com.xiaoyingbo.idle;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.xiaoyingbo.idle.impl.IBookImpl;
import com.xiaoyingbo.idle.impl.IUserImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    IUserInterface iUserInterface ;
    IBookInterface iBookInterface;
    private boolean bound=true;
    private BinderPool binderPool;

    /*线程池*/
    private static ThreadPoolExecutor taskPool = (ThreadPoolExecutor)new ThreadPoolExecutor(16, 32
            , 200L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1000),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("线程"+String.valueOf(taskPool.getPoolSize())+"号");
                    return thread;
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_bind_service).setOnClickListener(this);
        findViewById(R.id.btn_setuser).setOnClickListener(this);
        findViewById(R.id.btn_getuser).setOnClickListener(this);
        findViewById(R.id.btn_setbook).setOnClickListener(this);
        findViewById(R.id.btn_getbook).setOnClickListener(this);

        taskPool.execute(new Runnable() {
            @Override
            public void run() {
                binderPool = BinderPool.getInstance(MainActivity.this);
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            bound = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind_service:
                bound=true;
                /*BinderPool.getInstance(this).connectBinderPoolService();*/
                taskPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        IBinder iUserBinder = binderPool.queryBinder(BinderPool.BINDER_USER);
                        iUserInterface= IUserImpl.asInterface(iUserBinder);
                        IBinder iBookBinder = binderPool.queryBinder(BinderPool.BINDER_BOOK);
                        iBookInterface= IBookImpl.asInterface(iBookBinder);
                    }
                });
                break;
            case R.id.btn_setuser:
                taskPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            iUserInterface.setUser(new User("user",15));
                            Log.e("aidl-demo","MainActivity:"+" "+ Application.getProcessName()+" "+Thread.currentThread().getName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_getuser:
                taskPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            User user = iUserInterface.getUser();
                            Log.e("aidl-demo","MainActivity:"+user.toString()+" "+ Application.getProcessName()+" "+Thread.currentThread().getName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_setbook:
                taskPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            iBookInterface.setBook(new Book("book"));
                            Log.e("aidl-demo","MainActivity:"+" "+ Application.getProcessName()+" "+Thread.currentThread().getName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_getbook:
                taskPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Book book = iBookInterface.getBook();
                            Log.e("aidl-demo","MainActivity:"+book.toString()+" "+ Application.getProcessName()+" "+ Thread.currentThread().getName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }

}