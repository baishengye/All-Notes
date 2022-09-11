package Concrete;

import Abstract.Element;
import Abstract.Visitor;

public class ConcreteElementB extends Element {
    @Override
    public void Accept(Visitor visitor) {
        visitor.VisitConcreteElementB(this);
    }

    public void OperationA(){}
}
