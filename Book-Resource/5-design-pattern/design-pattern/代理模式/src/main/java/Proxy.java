public class Proxy implements Subject{

    ReallySubject reallySubject=null;

    @Override
    public void tell() {
        if(reallySubject==null){
            reallySubject=new ReallySubject();
        }
        reallySubject.tell();
    }
}
