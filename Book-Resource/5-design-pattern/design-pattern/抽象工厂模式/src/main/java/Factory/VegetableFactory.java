package Factory;

import Implements.Eggplant;
import Implements.Tomato;
import Interface.Fruit;
import Interface.Vegetable;

public class VegetableFactory implements PlantFactory {
    public static final int CABBAGE=1;
    public static final int TOMATO=2;
    public static final int EGGPLANT=3;
    @Override
    public Fruit getFruit(int fruitType) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Vegetable getVegetable(int vegetableType) {
        // TODO Auto-generated method stub
        if(vegetableType==TOMATO){
            return new Tomato();
        }
        if(vegetableType==EGGPLANT){
            return new Eggplant();
        }
        return null;
    }
}
