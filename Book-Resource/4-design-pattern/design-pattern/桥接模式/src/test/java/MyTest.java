import Implementor.ConcreteImplementorA;
import Implementor.ConcreteImplementorB;
import Implementor.RefinedAbstraction;
import Interface.Abstraction;
import org.junit.Test;

public class MyTest {
    @Test
    public void test(){
        Abstraction abstraction=new RefinedAbstraction();

        abstraction.SetImplementor(new ConcreteImplementorA());
        abstraction.Operation();

        abstraction.SetImplementor(new ConcreteImplementorB());
        abstraction.Operation();

    }


}
