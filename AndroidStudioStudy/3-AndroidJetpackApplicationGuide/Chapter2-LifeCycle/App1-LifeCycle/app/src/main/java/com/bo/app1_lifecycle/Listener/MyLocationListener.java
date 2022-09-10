package com.bo.app1_lifecycle.Listener;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLocationListener implements LifecycleObserver {
    private static final String TAG = "MyLocationListener";

    public MyLocationListener(Activity activity, OnLocationChangedListener onLocationChangedListener) {
        initLocationManager();
    }

    private void initLocationManager() {

    }

    /**
     * activity执行onResume()时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startGetLocation(){
        Log.d(TAG,"startGetLocation");
    }

    /**
     * activity执行onPause()时调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopGetLocation(){
        Log.d(TAG,"stopGetLocation");
    }
    
    public interface OnLocationChangedListener{
        void onChanged(double latitude,double longitude);
    }
}

