public class CashReturn implements CashSuper{

    private double moneyCondition=0.0;//满300
    private double moneyReturn=0.0;//返100

    public CashReturn(double moneyCondition, double moneyReturn) {
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public double acceptCash(double money) {
        if(money>=moneyCondition){
            return money-moneyReturn;
        }
        return money;
    }
}
