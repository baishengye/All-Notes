package com.xiaoyingbo.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AIDLService extends Service {

    private IBinder iBinder=new ITestImpl();
    public AIDLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
}