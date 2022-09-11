import com.sun.javaws.IconUtil;

public class Singletion {
    private static Singletion instance;

    public Singletion(){
        System.out.println("只生成一次");
    }

    public static Singletion getInstance(){
        if(instance==null){
            synchronized (Singletion.class){
                if(instance==null){
                    instance=new Singletion();
                }
            }
        }
        return instance;
    }
}
