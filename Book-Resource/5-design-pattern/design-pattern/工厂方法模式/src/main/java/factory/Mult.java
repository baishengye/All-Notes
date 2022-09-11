package factory;

public class Mult extends Operation{
    @Override
    public double result() {
        return getNumberA()*getNumberB();
    }
}
