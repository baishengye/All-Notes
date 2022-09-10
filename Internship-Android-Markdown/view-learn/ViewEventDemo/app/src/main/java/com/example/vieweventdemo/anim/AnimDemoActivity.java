package com.example.vieweventdemo.anim;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.vieweventdemo.R;
import com.example.vieweventdemo.databinding.ActivityAnimDemoBinding;

public class AnimDemoActivity extends AppCompatActivity {
    private static final String TAG = "AnimDemoActivity";
    ActivityAnimDemoBinding binding;

    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 30;
    private static final int DELAYED_TIME = 33;
    private int mCount = 0;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SCROLL_TO: {
                    mCount++;
                    if (mCount <= FRAME_COUNT) {
                        float fraction = mCount / (float) FRAME_COUNT;
                        int scrollX = (int) (fraction * 100);
                        binding.btnViewAnim.scrollTo(-scrollX,0);
                        mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO,
                                DELAYED_TIME);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAnimDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnViewAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick");
                //binding.btnViewAnim.startAnimation(AnimationUtils.loadAnimation(getThisContext(), R.anim.translate));
                /*ObjectAnimator.ofFloat(binding.btnViewAnim,"translationX",0,100).setDuration
                        (100).start();
                ObjectAnimator.ofFloat(binding.btnViewAnim,"translationX",100,0).setDuration
                        (100).start();*/
                /*ViewGroup.MarginLayoutParams layoutParams =  (ViewGroup.MarginLayoutParams)binding.btnViewAnim.getLayoutParams();
                layoutParams.leftMargin+=100;
                binding.btnViewAnim.requestLayout();*/
                /*final int startX = 0;
                final int deltaX = 100;
                ValueAnimator animator = ValueAnimator.ofInt(0,1).setDuration(1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        float fraction = animator.getAnimatedFraction();
                        binding.btnViewAnim.scrollTo(startX + (int) (deltaX * fraction),0);
                    }
                });
                animator.start();*/
                mHandler.sendEmptyMessage(MESSAGE_SCROLL_TO);
            }
        });
    }

    private Context getThisContext(){
        return this;
    }
}