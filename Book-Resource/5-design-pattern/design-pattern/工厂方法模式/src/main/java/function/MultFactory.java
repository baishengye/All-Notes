package function;

import factory.Mult;
import factory.Operation;

public class MultFactory implements OperationFactory{
    @Override
    public Operation createOperation() {
        return new Mult();
    }
}
