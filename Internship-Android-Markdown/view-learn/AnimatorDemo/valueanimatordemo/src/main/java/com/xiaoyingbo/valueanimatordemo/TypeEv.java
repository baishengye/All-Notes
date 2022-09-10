package com.xiaoyingbo.valueanimatordemo;

import android.animation.TypeEvaluator;

public class TypeEv implements TypeEvaluator<Tran> {
    @Override
    public Tran evaluate(float fraction, Tran startValue, Tran endValue) {
        Tran tran = new Tran();
        tran.setX((endValue.getX()-startValue.getX())*fraction+ startValue.getX());
        tran.setY((endValue.getY()-startValue.getY())*fraction+ startValue.getY());
        return tran;
    }
}
