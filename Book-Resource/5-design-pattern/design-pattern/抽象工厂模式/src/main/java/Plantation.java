import Factory.FruitFactory;
import Factory.PlantFactory;
import Factory.VegetableFactory;

public class Plantation {
    public static final int FRUIT=1;
    public static final int VEGETABLE=2;
    public static PlantFactory getFactory(int factoryType){
        if(factoryType==FRUIT){
            return new FruitFactory();
        }
        if(factoryType==VEGETABLE){
            return new VegetableFactory();
        }
        return null;
    }
}
