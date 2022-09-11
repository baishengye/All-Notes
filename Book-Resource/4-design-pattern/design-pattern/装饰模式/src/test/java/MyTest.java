import org.junit.Test;

public class MyTest {
    @Test
    public void Test(){
        Person person=new Person("bo");

        Tshirts tshirts=new Tshirts();
        BigTrouser bigTrouser=new BigTrouser();

        bigTrouser.Decorate(person);
        tshirts.Decorate(bigTrouser);

//        person.show();
//        bigTrouser.show();
        tshirts.show();
    }
}
