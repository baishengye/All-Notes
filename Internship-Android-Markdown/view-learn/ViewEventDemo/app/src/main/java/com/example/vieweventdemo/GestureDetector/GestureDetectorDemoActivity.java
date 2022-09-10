package com.example.vieweventdemo.GestureDetector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.example.vieweventdemo.R;

public class GestureDetectorDemoActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private VelocityTracker velocityTracker;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detector_demo);

        mGestureDetector = new GestureDetector(this,new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.d(TAG,"onDown");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d(TAG,"onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG,"onSingleTapUp");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d(TAG,"onScroll");
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG,"onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG,"onFling");
                return true;
            }
        });
        //解决长按屏幕后无法拖动的现象false
        //mGestureDetector.setIsLongpressEnabled(false);

        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d(TAG,"onSingleTapConfirmed");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d(TAG,"onDoubleTap");
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.d(TAG,"onDoubleTapEvent");
                return false;
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //手势测试
        return mGestureDetector.onTouchEvent(event);
    }
}