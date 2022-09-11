import Abstract.Colleague;
import Abstract.Mediator;

public class ConcreteColleague1 extends Colleague {//具体同事类
    public ConcreteColleague1(Mediator mediator) {
        super(mediator);
    }

    public void Send(String message){
        mediator.Send(message,this);
    }

    public void Notify(String message){
//        System.out.println(this.getClass().getName()+"得到信息: "+message);
        System.out.println("同事1得到信息: "+message);
    }
}
