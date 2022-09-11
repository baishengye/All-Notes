public class ConcreteStateB implements State{//状态B
    @Override
    public void Handle(Context context) {
        //下一个状态是A
        context.setState(new ConcreteStateA());
    }
}
