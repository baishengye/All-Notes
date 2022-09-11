import Abstract.Element;
import Abstract.Visitor;

import java.util.ArrayList;
import java.util.List;

public class ObjectStructure {

    public List<Element> elements=new ArrayList<Element>();

    public void Attach(Element element){
        elements.add(element);
    }

    public void Detach(Element element){
        elements.remove(element);
    }

    public void Accept(Visitor visitor){
        for (Element element:elements){
            element.Accept(visitor);
        }
    }









}
