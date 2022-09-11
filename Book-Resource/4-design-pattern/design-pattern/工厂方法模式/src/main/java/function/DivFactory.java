package function;

import factory.Div;
import factory.Operation;

public class DivFactory implements OperationFactory{
    @Override
    public Operation createOperation() {
        return new Div();
    }
}
