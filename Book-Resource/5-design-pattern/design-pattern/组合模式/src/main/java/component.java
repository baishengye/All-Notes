abstract class Component {//根节点接口
    protected String name;

    public Component(String name) {
        this.name = name;
    }

    public abstract void Add(Component component);
    public abstract void Remove(Component component);
    public abstract void Display(int depth);
}
