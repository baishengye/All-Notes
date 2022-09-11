public class Originator {//发起人类(需要保存状态的类)
    private String State;

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public Memento createMemento(){//创建备忘录
        return new Memento(this.State);
    }

    public void setMemento(Memento memento){
        this.State=memento.getState();
    }

    public void show(){
        System.out.println("State="+this.State);
    }
}
