import java.util.ArrayList;
import java.util.List;

public class ConcreteAggregate extends Aggregate{
    private List<Object> items=new ArrayList<Object>();

    @Override
    public Iterator CreateIterator() {
        return new ConcreteIterator(this);
    }

    public int Count(){
        return items.size();
    }

    public Object getItem(int index){
        return items.get(index);
    }

    public void setItem(int index,Object object){
        items.add(index,object);
    }
}
