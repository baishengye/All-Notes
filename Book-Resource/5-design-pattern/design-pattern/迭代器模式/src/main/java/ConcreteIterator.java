public class ConcreteIterator extends Iterator{
    private ConcreteAggregate aggregate;
    private int current=0;

    public ConcreteIterator(ConcreteAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public Object First() {
        return aggregate.getItem(0);
    }

    @Override
    public Object Next() {
        Object object=null;
        current++;
        if(current< aggregate.Count()){
            object=aggregate.getItem(current);
        }
        return object;
    }

    @Override
    public Boolean IsDone() {
        return current>=aggregate.Count()?true:false;
    }

    @Override
    public Object CurrentItem() {
        return aggregate.getItem(current);
    }
}
