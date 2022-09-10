package com.bo.app3_okhttp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onPostCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        initViews();
        initDatum();
        initListeners();
    }

    protected void initViews() {

    }

    protected void initDatum() {

    }

    protected void initListeners() {

    }

    protected BaseActivity getThisActivity(){
        return this;
    }
}
