package southday.java.pattern.factory.abs;

import southday.java.pattern.factory.abs.factory.IngredientFactory;

/**
 * 水果披萨
 * @author southday
 * @date 2017年2月20日
 */
public class FruitPizza extends Pizza {
    IngredientFactory factory = null;
    
    public FruitPizza(IngredientFactory factory) {
        this.factory = factory;
    }

    @Override
    public void prepare() {
        // TODO Auto-generated method stub
        this.flower = null;
        this.fruit = factory.createFruit();
    }

}
