public class ConcreteCommand extends Command{//具体命令类

    public ConcreteCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void Execute() {
       receiver.Action();
    }
}
