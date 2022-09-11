import org.junit.Test;

public class MyTest {
    @Test
    public void Test(){
        Context context=new Context(new ConcreteStateA());

        //状态转换
        context.Request();
        context.Request();
        context.Request();
        context.Request();
    }
}
