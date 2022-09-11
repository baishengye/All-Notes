package Implementor;

import Interface.Abstraction;

public class RefinedAbstraction extends Abstraction {
    @Override
    public void Operation() {
        implementor.Operation();
    }
}
