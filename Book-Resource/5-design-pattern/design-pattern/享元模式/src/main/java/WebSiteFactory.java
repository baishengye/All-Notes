import Abstract.WebSite;
import Concrete.ConcreteWebSite;

import java.util.HashMap;
import java.util.Map;

public class WebSiteFactory {
    private Map<String, WebSite> flyWights=new HashMap<>();

    public WebSite getWebSiteCategory(String key){
        if(!flyWights.containsKey(key)){
            flyWights.put(key,new ConcreteWebSite(key));
            return flyWights.get(key);
        }
        return flyWights.get(key);
    }

    public int getWebSiteCount(){
        return flyWights.size();
    }
}
