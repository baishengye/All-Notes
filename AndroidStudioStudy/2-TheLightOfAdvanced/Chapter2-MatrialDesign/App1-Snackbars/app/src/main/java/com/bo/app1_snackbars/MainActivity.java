package com.bo.app1_snackbars;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bo.app1_snackbars.activity.BaseActivity;
import com.bo.app1_snackbars.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends BaseActivity {
    ActivityMainBinding activityMainBinding;
    private Button btSb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    @Override
    protected void initViews() {
        super.initViews();

        btSb = activityMainBinding.btSb;
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btSb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackBars();
            }
        });
    }

    private void showSnackBars() {
        Snackbar.make(activityMainBinding.getRoot(),"标题",Snackbar.LENGTH_SHORT)
                .setAction("点击事件", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getThisActivity(),"土司",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}