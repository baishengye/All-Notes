package function;

import factory.Operation;
import factory.Sub;

public class SubFactory implements OperationFactory{
    @Override
    public Operation createOperation() {
        return new Sub();
    }
}
