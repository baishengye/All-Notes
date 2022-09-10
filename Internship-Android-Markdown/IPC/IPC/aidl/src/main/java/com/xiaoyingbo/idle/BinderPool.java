package com.xiaoyingbo.idle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xiaoyingbo.idle.impl.IBookImpl;
import com.xiaoyingbo.idle.impl.IUserImpl;

import java.util.concurrent.CountDownLatch;

public class BinderPool {
    private static final String TAG = "aidl-demo";

    public static final int BINDER_NONE = -1;
    public static final int BINDER_BOOK = 0;
    public static final int BINDER_USER = 1;

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPool sInstance;
    private CountDownLatch mConnectBinderPoolCountDownLatch;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }
    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }
    private synchronized void connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext,AIDLService.class);
        mContext.bindService(service,mBinderPoolConnection,
                Context.BIND_AUTO_CREATE);
        try {
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }
    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {}
        @Override
        public void onServiceConnected(ComponentName name,IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }
    };
    private final IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.
            DeathRecipient() {
        @Override
        public void binderDied() {
            Log.w(TAG,"binder died.");
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient,
                    0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };
    public static class BinderPoolImpl extends IBinderPool.Stub {
        public BinderPoolImpl() {
            super();
        }
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_BOOK: {
                    binder = new IBookImpl();
                    break;
                }
                case BINDER_USER: {
                    binder = new IUserImpl();
                    break;
                }
                default:
                    break;
            }
            return binder;
        }
    }


}
