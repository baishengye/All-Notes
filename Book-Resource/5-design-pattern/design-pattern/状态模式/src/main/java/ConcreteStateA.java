public class ConcreteStateA implements State{//状态A
    @Override
    public void Handle(Context context) {
        //下一个状态是B
         context.setState(new ConcreteStateB());
    }
}
