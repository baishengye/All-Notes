import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MyTest {
    @Test
    public void test(){
        Context context=new Context();

        List<AbstractExpression> expressionList=new ArrayList<AbstractExpression>();

        expressionList.add(new TerminalExpression());
        expressionList.add(new NonterminalExpression());
        expressionList.add(new TerminalExpression());
        expressionList.add(new TerminalExpression());

        for(AbstractExpression abstractExpression:expressionList){
            abstractExpression.Interpret(context);
        }

    }
}
