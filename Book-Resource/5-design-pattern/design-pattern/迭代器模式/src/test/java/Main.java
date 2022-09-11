import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    //java的内置迭代器
    public static void main(String arg[]){// 创建集合
         List<String> sites = new ArrayList<String>();
         sites.add("Google");
         sites.add("Runoob");
         sites.add("Taobao");
         sites.add("Zhihu");

         // 获取迭代器
        Iterator<String> it = sites.iterator();

        // 输出集合中的第一个元素
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}
