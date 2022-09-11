import org.junit.Test;

public class MyTest {
    @Test
    public void test(){
        Director director=new Director();
        Builder builder1=new ConcreteBuilder1();
        Builder builder2=new ConcreteBuilder2();

        director.Construct(builder1);
        Product product1=builder1.result();
        product1.Show();

        director.Construct(builder2);
        Product product2=builder2.result();
        product2.Show();

    }
}
