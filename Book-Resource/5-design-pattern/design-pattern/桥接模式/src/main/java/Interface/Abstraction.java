package Interface;

public abstract class Abstraction {
    protected Implementor implementor;

    public void SetImplementor(Implementor implementor){
        this.implementor=implementor;
    }

    public void Operation(){
        implementor.Operation();
    }
}
