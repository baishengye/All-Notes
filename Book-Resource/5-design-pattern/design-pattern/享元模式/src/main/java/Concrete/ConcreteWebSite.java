package Concrete;

import Abstract.WebSite;
import Pojo.User;

public class ConcreteWebSite extends WebSite {
    private String name="";

    public ConcreteWebSite(String name) {
        this.name = name;
    }

    @Override
    public void Use(User user) {
        System.out.println("网站分类："+this.name+" 用户: "+user.getName());
    }
}
