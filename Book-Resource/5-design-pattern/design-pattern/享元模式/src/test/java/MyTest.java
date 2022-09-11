import Abstract.WebSite;
import Pojo.User;
import org.junit.Test;

import java.io.Console;

public class MyTest {
    @Test
    public void Test(){
        WebSiteFactory f = new WebSiteFactory();

        WebSite fx = f.getWebSiteCategory("产品展示");
        fx.Use(new User("小菜"));

        WebSite fy = f.getWebSiteCategory("产品展示");
        fy.Use(new User("大鸟"));

        WebSite fz = f.getWebSiteCategory("产品展示");
        fz.Use(new User("娇娇"));

        WebSite fl = f.getWebSiteCategory("博客");
        fl.Use(new User("老顽童"));

        WebSite fm = f.getWebSiteCategory("博客");
        fm.Use(new User("桃谷六仙"));

        WebSite fn = f.getWebSiteCategory("博客");
        fn.Use(new User("南海鳄神"));
    }
}
