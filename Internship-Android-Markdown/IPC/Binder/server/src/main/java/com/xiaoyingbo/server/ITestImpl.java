package com.xiaoyingbo.server;

import android.os.RemoteException;
import android.util.Log;

import com.xiaoyingbo.binder.ITestInterface;

class ITestImpl extends ITestInterface.Stub{

    @Override
    public void test() throws RemoteException {
        Log.e("aidl","server："+ "接收信息");
    }
}