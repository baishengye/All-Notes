import org.junit.Test;

public class MyTest {
    @Test
    public void test(){
        ConcreteAggregate aggregate=new ConcreteAggregate();

        aggregate.setItem(0,"大鸟");
        aggregate.setItem(1,"小菜");
        aggregate.setItem(2,"行李");
        aggregate.setItem(3,"老外");
        aggregate.setItem(4,"公司内部员工");
        aggregate.setItem(5,"小偷");

        Iterator iterator=new ConcreteIterator(aggregate);
        Object item=iterator.First();
        while (!iterator.IsDone()){
            System.out.println(iterator.CurrentItem()+"请买票!");
            iterator.Next();
        }
    }
}
