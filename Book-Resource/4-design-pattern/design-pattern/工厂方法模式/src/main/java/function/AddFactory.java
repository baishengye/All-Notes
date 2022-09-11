package function;

import factory.Add;
import factory.Operation;

public class AddFactory implements OperationFactory{
    @Override
    public Operation createOperation() {
        return new Add();
    }
}
