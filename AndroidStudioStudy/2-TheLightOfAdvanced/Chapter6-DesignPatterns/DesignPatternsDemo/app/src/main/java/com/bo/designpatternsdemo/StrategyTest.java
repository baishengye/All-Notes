package com.bo.designpatternsdemo;

import android.util.Log;

public class StrategyTest {
    private static final String TAG = "StrategyTest";
    //比如：张无忌对应不同的对手使用不同的策略

    /**
     * 抽象策略接口
     */
    public interface IStrategy{
        void fighting();
    }

    /**
     * 具体策略
     */
    public class LowStrategy implements IStrategy{
        @Override
        public void fighting() {
            Log.d(TAG,"较弱的对手，使用太极拳");
        }
    }

    public class MidStrategy implements IStrategy{
        @Override
        public void fighting() {
            Log.d(TAG,"中等的对手，使用七伤拳");
        }
    }

    public class HighStrategy implements IStrategy{
        @Override
        public void fighting() {
            Log.d(TAG,"较强的对手，使用圣火令");
        }
    }

    /**
     * 上下文角色
     */
    public class Context{
        private IStrategy strategy;

        public Context(IStrategy strategy) {
            this.strategy = strategy;
        }

        public void fighting(){
            strategy.fighting();
        }
    }

    public void test(){
        Context context=null;

        //遇到较弱对手
        context=new Context(new LowStrategy());
        context.fighting();

        //遇到中等对手
        context=new Context(new MidStrategy());
        context.fighting();

        //遇到较强对手
        context=new Context(new HighStrategy());
        context.fighting();
    }
}
