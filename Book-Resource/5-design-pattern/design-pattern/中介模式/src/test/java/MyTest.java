import org.junit.Test;

public class MyTest {
    @Test
    public void test(){
        ConcreteMediator mediator=new ConcreteMediator();

        ConcreteColleague1 colleague1=new ConcreteColleague1(mediator);
        ConcreteColleague2 colleague2=new ConcreteColleague2(mediator);

        mediator.setColleague1(colleague1);
        mediator.setColleague2(colleague2);

        colleague1.Send("吃过饭了吗?");
        colleague2.Send("没有呢，你打算请客?");
    }
}
