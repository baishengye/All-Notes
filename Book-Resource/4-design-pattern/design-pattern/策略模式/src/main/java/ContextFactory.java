public class ContextFactory {
    public static CashSuper createContext(String type) {
        CashSuper cashSuper=null;
        switch (type){
            case "normal":cashSuper=new CashNormal();break;
            case "300=>100":cashSuper=new CashReturn(300,100);break;
            case "rebate":cashSuper=new CashRebate(0.8);/*可以改成用户输入*/break;
            default:try{
                throw new Exception("字符输入错误");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return cashSuper;
    }
}
