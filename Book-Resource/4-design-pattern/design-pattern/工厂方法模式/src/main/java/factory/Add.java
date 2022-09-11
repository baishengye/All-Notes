package factory;

public class Add extends Operation{
    @Override
    public double result() {
        return getNumberA()+getNumberB();
    }
}
