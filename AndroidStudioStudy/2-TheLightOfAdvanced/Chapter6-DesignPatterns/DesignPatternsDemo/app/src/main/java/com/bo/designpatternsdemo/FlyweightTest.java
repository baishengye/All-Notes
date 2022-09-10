package com.bo.designpatternsdemo;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class FlyweightTest {
    private static final String TAG = "FlyweightTest";
    //比如在某宝上双十一时恐怖的订单量会生成许多商品对象，这样就很容易出现OOM

    /**
     * 抽象享元角色：商品接口
     */
    public interface IGoods{
        public void showGoodsPrice(String name);
    }

    /**
     * 具体享元角色
     */
    public class Goods implements IGoods{
        private String name;//名称
        private String version;//版本

        public Goods(String name) {
            this.name=name;
            this.version="default";
        }

        @Override
        public void showGoodsPrice(String version) {
            if(version.equals("32G")){
                Log.d(TAG,"价格时5199元");
            }else if (version.equals("128G")){
                Log.d(TAG,"价格时5999元");
            }
        }
    }

    /**
     * 享元工厂
     */
    public class GoodsFactory{
        private Map<String,Goods> pool=new HashMap<>();
        public Goods getGoods(String name){
            if(pool.containsKey(name)){
                Log.d(TAG,"使用缓存");
                return pool.get(name);
            }else{
                Goods goods = new Goods(name);
                pool.put(name,goods);
                Log.d(TAG,"创建商品,key："+name);
                return goods;
            }
        }
    }

    public void test(){
        GoodsFactory goodsFactory = new GoodsFactory();

        Goods iphone71 = goodsFactory.getGoods("iphone7");
        iphone71.showGoodsPrice("32G");

        Goods iphone72 = goodsFactory.getGoods("iphone7");
        iphone72.showGoodsPrice("32G");

        Goods iphone73 = goodsFactory.getGoods("iphone7");
        iphone73.showGoodsPrice("128G");
    }
}
