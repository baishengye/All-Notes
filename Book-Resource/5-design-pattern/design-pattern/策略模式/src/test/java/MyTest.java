import org.junit.Test;

public class MyTest {
    @Test
    public void Test(){
        CashContext context=new CashContext(ContextFactory.createContext("rebate"));
        System.out.println(context.result(400));
    }
}
