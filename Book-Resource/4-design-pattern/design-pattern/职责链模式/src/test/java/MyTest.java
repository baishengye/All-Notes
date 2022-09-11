import org.junit.Test;

public class MyTest {
    @Test
    public void test(){
        Handler h1 = new ConcreteHandler1();
        Handler h2 = new ConcreteHandler2();
        Handler h3 = new ConcreteHandler3();
        h1.setSuccessor(h2);
        h2.setSuccessor(h3);

        int[] requests = { 5,15,25,30 };

        for (int request : requests)
        {
            h1.HandleRequest(request);
        }

    }
}
