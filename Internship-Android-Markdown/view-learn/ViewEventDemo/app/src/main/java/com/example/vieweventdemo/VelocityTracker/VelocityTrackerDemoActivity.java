package com.example.vieweventdemo.VelocityTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.example.vieweventdemo.R;

public class VelocityTrackerDemoActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private VelocityTracker velocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detector_demo);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //速度测试
        int actionMasked = event.getActionMasked();
        if(actionMasked==MotionEvent.ACTION_DOWN){
            Log.d(TAG,"DOWN");
            velocityTracker =VelocityTracker.obtain();
        }else if(actionMasked==MotionEvent.ACTION_MOVE){
            velocityTracker.addMovement(event);
            velocityTracker.computeCurrentVelocity(1000);
            Log.d(TAG,"MOVE");
            Log.d(TAG, "X: "+ velocityTracker.getXVelocity());
            Log.d(TAG, "Y: "+ velocityTracker.getYVelocity());
        }else{
            Log.d(TAG,"UP|CANCEL");
            velocityTracker.clear();
            velocityTracker.recycle();
        }
        return super.onTouchEvent(event);
    }
}