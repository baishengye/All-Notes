public class Finery extends Person{
    protected Person component=null;

    //打扮
    public void Decorate(Person component){
        this.component=component;
    }

    @Override
    public void show() {
        if(component!=null){
            component.show();
        }
    }
}
