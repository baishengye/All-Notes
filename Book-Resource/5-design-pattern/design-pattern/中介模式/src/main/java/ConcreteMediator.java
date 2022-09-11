import Abstract.Colleague;
import Abstract.Mediator;

public class ConcreteMediator extends Mediator {//具体的发送消息的方法
    private ConcreteColleague1 colleague1;
    private ConcreteColleague2 colleague2;

    public void setColleague1(ConcreteColleague1 colleague1) {
        this.colleague1 = colleague1;
    }

    public void setColleague2(ConcreteColleague2 colleague2) {
        this.colleague2 = colleague2;
    }

    @Override
    public void Send(String message, Colleague colleague) {
        if(colleague==colleague1){
            colleague2.Notify(message);
        }else{
            colleague1.Notify(message);
        }
    }
}
