package Abstract;

public abstract class Colleague {//抽象同事类
    public Mediator mediator;

    public Colleague(Mediator mediator){
        this.mediator=mediator;
    }
}
