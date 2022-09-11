abstract class Command {//命令抽象类
    protected Receiver receiver;

    public Command(Receiver receiver) {
        this.receiver = receiver;
    }

    public abstract void Execute();
}
