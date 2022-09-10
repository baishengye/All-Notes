package com.bo.designpatternsdemo;

import android.util.Log;

import com.google.android.material.tabs.TabLayout;

/***
 * 简单工厂创建的是产品
 */
public  class SimpleFactory {
    static String LENOVO="LENOVO";
    static String HP="HP";
    static String ASUS="ASUS";

    public  Computer createComputer(String type){
        Computer computer=null;

        if (LENOVO.equals(type)) {
            computer = new LenovoComputer();
        } else if (HP.equals(type)) {
            computer = new HpComputer();
        } else if (ASUS.equals(type)) {
            computer = new AsusComputer();
        }

        return computer;
    }
}



/**
 * 抽象产品类
 */
abstract class Computer{
    /**
     * 产品的抽象方法，有具体的产品类实现
     */
    public abstract void start();
}

/**
 * 具体产品类
 */
class LenovoComputer extends Computer{
    private  final String TAG = "Computer";

    @Override
    public void start() {
        Log.d(TAG,"联想计算机启动");
    }
}

class HpComputer extends Computer{
    private final String TAG = "Computer";

    @Override
    public void start() {
        Log.d(TAG,"惠普计算机启动");
    }
}

class AsusComputer extends Computer{
    private final String TAG = "Computer";

    @Override
    public void start() {
        Log.d(TAG,"华硕计算机启动");
    }
}



