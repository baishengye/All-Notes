package Implementor;

import Interface.Implementor;

public class ConcreteImplementorB implements Implementor {
    @Override
    public void Operation() {
        System.out.println("具体实现B方法执行");
    }
}
