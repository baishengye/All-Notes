package Abstract;

import Abstract.Visitor;

public abstract class Element {
    public abstract void Accept(Visitor visitor);
}
