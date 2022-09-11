import Factory.FruitFactory;
import Factory.VegetableFactory;
import Implements.Apple;
import Implements.Tomato;
import org.junit.Test;

public class MyTest {
    @Test
    public void test() {
        // TODO Auto-generated method stub

        FruitFactory fruitFactory=(FruitFactory)Plantation.getFactory(Plantation.FRUIT);
        Apple apple=(Apple) fruitFactory.getFruit(FruitFactory.APPLE);
        apple.printInfo();

        VegetableFactory vegetableFactory=(VegetableFactory)Plantation.getFactory(Plantation.VEGETABLE);
        Tomato tomato=(Tomato) vegetableFactory.getVegetable(VegetableFactory.TOMATO);
        tomato.printInfo();
    }
}
