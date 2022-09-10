package com.xiaoyingbo.idle.impl;

import android.app.Application;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.xiaoyingbo.idle.IUserInterface;
import com.xiaoyingbo.idle.User;

public class IUserImpl extends IUserInterface.Stub {
    User user = null;

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void setUser(User user) throws RemoteException {
        this.user = user;
        Log.d("aidl-demo", "AIDLService setUser:" + user.toString()+" "+ Application.getProcessName());
    }

    @Override
    public User getUser() throws RemoteException {
        return user;
    }

}
