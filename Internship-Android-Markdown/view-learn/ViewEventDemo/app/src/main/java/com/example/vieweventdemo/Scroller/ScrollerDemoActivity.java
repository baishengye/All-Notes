package com.example.vieweventdemo.Scroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.vieweventdemo.R;
import com.example.vieweventdemo.Scroller.widget.ButtonGroup;

public class ScrollerDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_demo);

        Button btn = (Button) findViewById(R.id.btn);
        ButtonGroup layout = (ButtonGroup) findViewById(R.id.group);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.myScrollTo();
            }
        });
    }

}