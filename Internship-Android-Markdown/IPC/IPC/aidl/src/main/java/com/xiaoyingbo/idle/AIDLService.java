package com.xiaoyingbo.idle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.xiaoyingbo.idle.impl.IBinderPoolImpl;

public class AIDLService extends Service {

    private final IBinderPoolImpl binderPool=new IBinderPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //返回你的接口
        return binderPool;
    }
}