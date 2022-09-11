import org.junit.Test;

public class MyTest {
    @Test
    public void test(){
        Originator originator=new Originator();
        originator.setState("on");
        originator.show();

        Caretaker caretaker=new Caretaker();
        caretaker.setMemento(originator.createMemento());

        originator.setState("Off");
        originator.show();

        originator.setMemento(caretaker.getMemento());
        originator.show();
    }
}
