package Concrete;

import Abstract.Visitor;

public class ConcreteVisitor1 extends Visitor {
    @Override
    public void VisitConcreteElementA(ConcreteElementA concreteElementA) {
        System.out.println(concreteElementA.getClass().getName()+"被"+this.getClass().getName()+"访问");
    }

    @Override
    public void VisitConcreteElementB(ConcreteElementB concreteElementB) {
        System.out.println(concreteElementB.getClass().getName()+"被"+this.getClass().getName()+"访问");
    }
}
