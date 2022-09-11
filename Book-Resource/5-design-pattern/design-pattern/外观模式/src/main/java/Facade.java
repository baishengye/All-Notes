public class Facade {
    SubSystemOne systemOne;
    SubSystemTwo systemTwo;
    SubSystemThree systemThree;
    SubSystemFour systemFour;

    public Facade(){
        systemOne =new SubSystemOne();
        systemTwo=new SubSystemTwo();
        systemThree=new SubSystemThree();
        systemFour=new SubSystemFour();
    }

    public void MethodA(){
        System.out.println("方法组A");
        systemOne.MethodOne();
        systemTwo.MethodTwo();
        systemThree.MethodThree();
    }

    public void MethodB(){
        System.out.println("方法组B");
        systemFour.MethodFour();
        systemOne.MethodOne();
        systemTwo.MethodTwo();
        systemThree.MethodThree();
    }
}
