package com.bo.app2_cardview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        initViews();
        initDatum();
        initListeners();
    }

    protected void initViews(){

    }

    protected void initDatum(){

    }

    protected void initListeners(){

    }

    protected BaseActivity getMainActivity(){
        return this;
    }
}
