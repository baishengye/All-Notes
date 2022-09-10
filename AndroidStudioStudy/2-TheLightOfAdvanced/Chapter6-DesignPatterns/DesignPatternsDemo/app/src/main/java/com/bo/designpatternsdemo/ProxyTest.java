package com.bo.designpatternsdemo;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理模式
 */
public class ProxyTest {

    private static final String TAG = "ProxyTest";

    /**
     * 抽象主题
     */
    interface IShop{
        void buy();
    }

    /**
     * 真实主题类
     */
    class Buyer implements IShop{
        @Override
        public void buy() {
            Log.d(TAG,"购买");
        }
    }

    /**
     * 静态代理类
     */
    class Proxy implements IShop{
        private IShop shop;

        public Proxy(IShop shop) {
            this.shop = shop;
        }

        @Override
        public void buy() {
            shop.buy();
        }
    }

    /**
     * 静态代理买
     */
    public void buy(){
        IShop shop = new Buyer();
        Proxy proxy = new Proxy(shop);
        proxy.buy();
    }

    /**
     * 动态代理
     */
    class DynamicProxy implements InvocationHandler {
        private Object obj;

        public DynamicProxy(Object obj){
            this.obj=obj;
        }

        /**
         * 在调用Proxy.buy()的时候会调用此方法
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object invoke = method.invoke(obj, args);
            if(method.getName().equals("buy")){
                Log.d(TAG,"动态代理买");
            }
            return invoke;
        }
    }

    /**
     * 动态代理买
     */
    public void buyDynamic(){
        IShop shop = new Buyer();

        //创建动态代理
        DynamicProxy dynamicProxy = new DynamicProxy(shop);

        ClassLoader classLoader = shop.getClass().getClassLoader();

        IShop proxy = (IShop) java.lang.reflect.Proxy.newProxyInstance(classLoader, new Class[]{IShop.class}, dynamicProxy);

        proxy.buy();
    }
}
