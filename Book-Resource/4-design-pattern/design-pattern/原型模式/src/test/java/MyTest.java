import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MyTest {
    @Test
    public void Test() throws CloneNotSupportedException {
        //创建对象person1
        Person person1 = new Person();
        //初始化对象
        person1.setName("章北海");
        person1.setAge(20);
        List<String> friends = new ArrayList<String>();
        friends.add("罗辑");
        friends.add("希恩斯");
        person1.setFriends(friends);
        //person2是浅层克隆
        Person person2 = person1.shallowClone();
        //person3是深层克隆
        Person person3 = person1.deepClone();
        //获取浅层克隆的friends的list对象
        List<String> person2_friends = person2.getFriends();
        //向引用对象中添加值
        person2_friends.add("shallow");
        person2.setFriends(person2_friends);
        //获取深层克隆的friends的list对象
        List<String> person3_friends = person3.getFriends();
        //向引用对象中添加值
        person3_friends.add("deep");
        person3.setFriends(person3_friends);

        System.out.println("原型："+person1);
        System.out.println("浅层克隆："+person2);
        System.out.println("深层克隆："+person3);
    }
}
