import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Cloneable{
    //姓名
    private String name;
    //年龄
    private int age;
    //朋友
    private List<String> friends;

    //浅层克隆
    public Person shallowClone() throws CloneNotSupportedException {
       return (Person) super.clone();
    }
    //深层克隆
    public Person deepClone() throws CloneNotSupportedException {
        Person person = (Person) super.clone();
        List<String> newFriends = new ArrayList<String>();
        for(String friend : this.getFriends()) {
            newFriends.add(friend);
        }
        person.setFriends(newFriends);
        return person;
    }
}
