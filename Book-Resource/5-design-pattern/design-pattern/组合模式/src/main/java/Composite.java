import java.util.ArrayList;
import java.util.List;

public class Composite extends Component{//枝节点
    private List<Component> children=new ArrayList<Component>();

    public Composite(String name) {
        super(name);
    }

    @Override
    public void Add(Component component) {
        children.add(component);
    }

    @Override
    public void Remove(Component component) {
        children.remove(component);
    }

    @Override
    public void Display(int depth) {
        for(int i=0;i<depth;i++){
            System.out.print('-');
        }
        System.out.println(this.name);

        for (Component component:children){
            component.Display(depth+2);
        }
    }
}
