import factory.Operation;
import function.AddFactory;
import function.OperationFactory;
import org.junit.Test;

public class MyTest {
    @Test
    public void test(){
        OperationFactory factory=new AddFactory();
        Operation operation=factory.createOperation();
        operation.setNumberA(4);
        operation.setNumberB(5);
        System.out.println(operation.result() );
    }
}
