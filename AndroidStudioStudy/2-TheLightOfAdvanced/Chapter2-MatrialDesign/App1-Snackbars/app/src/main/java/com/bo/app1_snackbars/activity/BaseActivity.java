package com.bo.app1_snackbars.activity;

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

    protected void initListeners() {

    }

    protected void initDatum() {

    }

    protected void initViews() {

    }

    protected BaseActivity getThisActivity(){
        return this;
    }
}
