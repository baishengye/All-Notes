public class Leaf extends Component{//叶节点
    public Leaf(String name){
        super(name);
    }

    @Override
    public void Add(Component component) {
        System.out.println("Cannot add a leaf");
    }

    @Override
    public void Remove(Component component) {
        System.out.println("Cannot remove a leaf");
    }

    @Override
    public void Display(int depth) {
        for(int i=0;i<depth;i++){
            System.out.print('-');
        }
        System.out.println(this.name);
    }
}
