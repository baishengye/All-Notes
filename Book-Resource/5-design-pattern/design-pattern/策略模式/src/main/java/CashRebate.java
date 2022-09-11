public class CashRebate implements CashSuper{//打折
    private double rebate=1.0;//折数

    public CashRebate(double rebate) {
        this.rebate = rebate;
    }

    @Override
    public double acceptCash(double money) {
        return money*rebate;
    }
}
