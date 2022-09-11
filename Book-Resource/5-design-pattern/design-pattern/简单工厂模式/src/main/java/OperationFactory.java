public class OperationFactory {
    public static Operation createOperation(String opera){
        Operation operation = null;
        switch (opera){
            case "+": operation=new Add();break;
            case "-": operation=new Sub();break;
            case "*": operation=new Mult();break;
            case "/": operation=new Div();break;
            default:try{
                throw new Exception("字符输入错误");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return operation;
    }
}
