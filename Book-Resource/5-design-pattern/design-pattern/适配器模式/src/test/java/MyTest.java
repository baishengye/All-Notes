import org.junit.Test;

public class MyTest {
    @Test
    public void test(){
        Target target=new Adapter();
        target.Request();
    }
}
