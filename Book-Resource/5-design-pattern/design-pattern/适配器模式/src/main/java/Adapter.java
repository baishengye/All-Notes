public class Adapter extends Target{
    private Adaptee adatee=new Adaptee();

    @Override
    public void Request() {
        adatee.SpecificRequest();
    }
}
