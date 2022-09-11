package Concrete;

import Abstract.Element;
import Abstract.Visitor;

public class ConcreteElementA extends Element {
    @Override
    public void Accept(Visitor visitor) {
        visitor.VisitConcreteElementA(this);
    }

    public void OperationA(){}
}
