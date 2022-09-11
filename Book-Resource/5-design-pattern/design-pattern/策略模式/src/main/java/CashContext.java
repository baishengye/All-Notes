public class CashContext {
    CashSuper cashSuper=null;

    public CashContext(CashSuper cashSuper) {
        this.cashSuper = cashSuper;
    }

    public double result(double money){
        return cashSuper.acceptCash(money);
    }
}
