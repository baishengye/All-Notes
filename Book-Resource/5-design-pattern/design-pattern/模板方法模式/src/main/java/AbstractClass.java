public abstract class AbstractClass {
    public abstract void Print01();
    public abstract void Pring02();

    public void TemplateMethod(){
        Print01();
        Pring02();
        System.out.println("!");
    }
}
