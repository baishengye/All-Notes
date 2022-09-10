package com.bo.app1_httpurl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bo.app1_httpurl.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    ActivityMainBinding activityMainBinding;
    private Button btGet;
    private Button btPost;
    private Button btUrlPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    @Override
    protected void initViews() {
        super.initViews();

        btGet = activityMainBinding.btGet;
        btPost = activityMainBinding.btPost;
        btUrlPost = activityMainBinding.btUrlPost;
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TestHttpClient.useHttpClientGet("http://www.baidu.com");
                    }
                }).start();
            }
        });

        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TestHttpClient.useHttpClientPost("http://ip.taobao.com/outGetIpInfo");
                    }
                }).start();
            }
        });

        btUrlPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TestHttpClient.useHttpClientPost("http://ip.taobao.com/outGetIpInfo");
                    }
                }).start();
            }
        });
    }
}