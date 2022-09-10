package com.bo.designpatternsdemo;

import android.util.Log;

/**
 * 装饰器模式
 */
public class DecoratorTest {

    private static final String TAG = "DecoratorTest";

    /**
     * 抽象组件：武侠类
     */
    public abstract class Swordsman{
        /**
         * 使用武功的抽象方法
         */
        public abstract void attackMagic();
    }

    /**
     * 组件具体实现类：杨过
     */
    public class YangGuo extends Swordsman{

        /**
         * 杨过开始只会抱抱女人
         */
        @Override
        public void attackMagic() {
            Log.d(TAG,"抱抱女人");
        }
    }

    /**
     * 抽象装饰器:师傅
     */
    public abstract class Master extends Swordsman{
        private Swordsman swordsman;

        public Master(Swordsman swordsman) {
            this.swordsman = swordsman;
        }

        @Override
        public void attackMagic() {
            swordsman.attackMagic();
        }
    }

    /**
     * 装饰器实现类：杨过的师傅洪七公
     */
    public class HongQiGong extends Master{
        public HongQiGong(Swordsman swordsman) {
            super(swordsman);
        }

        @Override
        public void attackMagic() {
            super.attackMagic();
            teachAttackMagic();
        }

        private void teachAttackMagic() {
            Log.d(TAG,"洪七公教授打狗棍法");
            Log.d(TAG,"杨过使用打狗棍法");
        }
    }

    /**
     * 装饰器实现类：杨过的师傅洪七公
     */
    public class OuYangFeng extends Master{
        public OuYangFeng(Swordsman swordsman) {
            super(swordsman);
        }

        @Override
        public void attackMagic() {
            super.attackMagic();
            teachAttackMagic();
        }

        private void teachAttackMagic() {
            Log.d(TAG,"欧阳峰教授蛤蟆功");
            Log.d(TAG,"杨过使用蛤蟆功");
        }
    }

    public void use(){
        //创建杨过
        YangGuo yangGuo = new YangGuo();
        //洪七公传授打狗棍,杨过学会了
        HongQiGong hongQiGong = new HongQiGong(yangGuo);
        //欧阳锋传授蛤蟆功,杨过学会了
        OuYangFeng ouYangFeng = new OuYangFeng(yangGuo);

        ouYangFeng.attackMagic();
    }
}
