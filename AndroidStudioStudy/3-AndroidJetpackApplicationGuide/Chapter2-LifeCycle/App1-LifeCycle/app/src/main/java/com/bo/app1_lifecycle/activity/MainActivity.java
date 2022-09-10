package com.bo.app1_lifecycle.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bo.app1_lifecycle.Listener.MyLocationListener;
import com.bo.app1_lifecycle.R;

public class MainActivity extends AppCompatActivity {

    private MyLocationListener myLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLocationListener=new MyLocationListener(this, new MyLocationListener.OnLocationChangedListener() {
            @Override
            public void onChanged(double latitude, double longitude) {
                //todo 展示收到的位置信息
            }
        });

        //将观察者与被观察者绑定
        getLifecycle().addObserver(myLocationListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*//开始获取用户的地理位置
        startGetLocation();*/
    }

    @Override
    protected void onPause() {
        super.onPause();

        /*//停止获取用户的地理位置
        stopGetLocation();*/
    }

}