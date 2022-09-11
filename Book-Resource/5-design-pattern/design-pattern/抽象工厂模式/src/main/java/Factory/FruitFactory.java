package Factory;

import Implements.Apple;
import Implements.Banana;
import Interface.Fruit;
import Interface.Vegetable;

public class FruitFactory implements PlantFactory{
    public static final int APPLE=1;
    public static final int BANANA=2;
    @Override
    public Fruit getFruit(int fruitType){
        if(fruitType==APPLE){
            return new Apple();
        }
        if(fruitType==BANANA){
            return new Banana();
        }
        return null;
    }
    @Override
    public Vegetable getVegetable(int vegetableType) {
        // TODO Auto-generated method stub
        return null;
    }
}
