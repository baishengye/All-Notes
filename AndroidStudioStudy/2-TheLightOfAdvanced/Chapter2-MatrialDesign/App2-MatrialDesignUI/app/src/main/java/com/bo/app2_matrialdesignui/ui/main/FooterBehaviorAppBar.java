package com.bo.app2_matrialdesignui.ui.main;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class FooterBehaviorAppBar extends CoordinatorLayout.Behavior<View> {
    public FooterBehaviorAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    /**
     * 通过滑动的距离设置child的位置
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY = Math.abs(dependency.getY());
        Log.i("wangshu",translationY+"");
        child.setTranslationY(translationY);
        return true;
    }
}