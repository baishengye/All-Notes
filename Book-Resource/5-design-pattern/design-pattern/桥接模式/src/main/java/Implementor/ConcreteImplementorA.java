package Implementor;

import Interface.Implementor;

public class ConcreteImplementorA implements Implementor {
    @Override
    public void Operation() {
        System.out.println("具体实现A方法执行");
    }
}
