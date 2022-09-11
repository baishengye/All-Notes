public class ConcreteBuilder1 extends Builder{
    private Product product=new Product();

    @Override
    public void BuildPartA() {
        product.Add("部件A");
    }

    @Override
    public void BuildPartB() {
        product.Add("部件B");
    }

    @Override
    public void BuildPartC() {
        product.Add("部件C");
    }

    @Override
    public Product result() {
        return product;
    }
}
