package com.bo.designpatternsdemo;

import android.util.Log;

/**
 * 外观模式
 */
public class FacadeTest {
    private static final String TAG = "FacadeTest";

    /**
     * 比如：张无忌看作一个系统，其实他还分为3个子系统
     */

    /**
     * 子系统:招式
     */
    public class ZhaoShi{
        public void taiJiQuan(){
            Log.d(TAG,"使用招式太极拳");
        }
        public void qiShangQuan(){
            Log.d(TAG,"使用招式七伤拳");
        }
        public void shengHuoling(){
            Log.d(TAG,"使用招式圣火令");
        }
    }

    /**
     * 子系统:内功
     */
    public class NeiGong{
        public void qianKunDaNuoYi(){
            Log.d(TAG,"使用内功乾坤大挪移");
        }
        public void jiuYangShenGong(){
            Log.d(TAG,"使用内功九阳神功");
        }
    }

    /**
     * 子系统：经脉
     */
    public class JingMai{
        public void jingMai(){
            Log.d(TAG,"打通任督二脉");
        }
    }

    /**
     * 外观类：张无忌
     */
    public class ZhangWuJi{
        private JingMai jingMai;
        private ZhaoShi zhaoShi;
        private NeiGong neiGong;

        public ZhangWuJi() {
            jingMai=new JingMai();
            zhaoShi=new ZhaoShi();
            neiGong=new NeiGong();
        }

        /**
         * 使用乾坤大挪移
         */
        public void qianKunDanuoYi(){
            //打通任督二脉
            jingMai.jingMai();
            //使用内功
            neiGong.qianKunDaNuoYi();
        }

        /**
         * 使用太极拳
         */
        public void taiJiQuan(){
            //打通任督二脉
            jingMai.jingMai();
            //使用内功
            neiGong.jiuYangShenGong();
            //太极拳
            zhaoShi.taiJiQuan();
        }
    }

    public void test(){
        new ZhangWuJi().taiJiQuan();
    }
}
