package com.example.viewworktheorydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("tv", String.valueOf(tv.getWidth()));
                    }
                });
            }
        });

        CircleView cv = findViewById(R.id.cv);
        cv.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator sizeAnimator = ObjectAnimator.ofObject(cv, "size",
                        new TypeEvaluator<Size>() {
                            @Override
                            public Size evaluate(float fraction, Size startValue, Size endValue) {
                                Size size = new Size(0, 0);
                                size.setWidth((int) ((endValue.getWidth() - startValue.getWidth()) * fraction + startValue.getWidth()));
                                size.setHeight((int) ((endValue.getHeight() - startValue.getHeight()) * fraction + startValue.getHeight()));
                                return size;
                            }
                        }, new Size(cv.getWidth(), cv.getHeight()),new Size(200, 700), new Size(600, 300));
                sizeAnimator.setDuration(3000);
                sizeAnimator.setRepeatCount(1000);
                sizeAnimator.setRepeatMode(ObjectAnimator.REVERSE);
            }
        });


        cv.setOnClickListener(v -> cv.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator sizeAnimator = ObjectAnimator.ofObject(cv, "size",
                        new TypeEvaluator<Size>() {
                            @Override
                            public Size evaluate(float fraction, Size startValue, Size endValue) {
                                Size size = new Size(0, 0);
                                size.setWidth((int) ((endValue.getWidth() - startValue.getWidth()) * fraction + startValue.getWidth()));
                                size.setHeight((int) ((endValue.getHeight() - startValue.getHeight()) * fraction + startValue.getHeight()));
                                return size;
                            }
                        }, new Size(cv.getWidth(), cv.getHeight()),new Size(200, 700), new Size(600, 300));
                sizeAnimator.setDuration(3000);
                sizeAnimator.setRepeatCount(1000);
                sizeAnimator.setRepeatMode(ObjectAnimator.REVERSE);
                sizeAnimator.start();
            }
        }));
    }
}