import org.junit.Test;

public class MyTest {
    @Test
    public void test01(){
        Singletion singletion01=Singletion.getInstance();
        Singletion singletion02=Singletion.getInstance();
        Singletion singletion03=Singletion.getInstance();

    }

    @Test
    public void test02(){
        StaticSingletion singletion01=StaticSingletion.getInstance();
        StaticSingletion singletion02=StaticSingletion.getInstance();
        StaticSingletion singletion03=StaticSingletion.getInstance();
    }
}
