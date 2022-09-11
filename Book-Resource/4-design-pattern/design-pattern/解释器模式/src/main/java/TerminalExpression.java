public class TerminalExpression extends AbstractExpression{//终结符表达式、解释出的内容

    @Override
    public void Interpret(Context context) {
        System.out.println("终端解释器");
    }
}
