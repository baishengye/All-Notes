package com.xiaoyingbo.idle.impl;

import static com.xiaoyingbo.idle.BinderPool.BINDER_BOOK;
import static com.xiaoyingbo.idle.BinderPool.BINDER_USER;

import android.app.Application;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.xiaoyingbo.idle.IBinderPool;

public class IBinderPoolImpl extends IBinderPool.Stub {
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public IBinder queryBinder(int binderId) {
        switch (binderId) {
            case BINDER_BOOK: {
                Log.d("aidl-demo", "IBinderPoolImpl 创建:IBookImpl " +Application.getProcessName());
                return new IBookImpl();
            }
            case BINDER_USER: {
                Log.d("aidl-demo", "IBinderPoolImpl 创建:IUserImpl " +Application.getProcessName());
                return new IUserImpl();
            }
        }
        return null;
    }
}