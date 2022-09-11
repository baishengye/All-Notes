import org.junit.Test;

public class MyTest {
    @Test
    public void test(){
        Command command=new ConcreteCommand(new Receiver());

        Invoker invoker=new Invoker();
        invoker.SetCommand(command);

        invoker.ExecuteCommand();
    }
}
