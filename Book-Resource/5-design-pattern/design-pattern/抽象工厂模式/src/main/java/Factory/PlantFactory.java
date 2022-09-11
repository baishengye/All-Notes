package Factory;

import Interface.Fruit;
import Interface.Vegetable;

public interface PlantFactory {
    Fruit getFruit(int fruitType);
    Vegetable getVegetable(int vegetableType);
}
