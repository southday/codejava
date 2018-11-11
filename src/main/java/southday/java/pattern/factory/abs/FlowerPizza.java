package southday.java.pattern.factory.abs;

import southday.java.pattern.factory.abs.factory.IngredientFactory;

/**
 * 鲜花披萨
 * @author southday
 * @date 2017年2月20日
 */
public class FlowerPizza extends Pizza {
    IngredientFactory factory = null;
    
    public FlowerPizza(IngredientFactory factory) {
        this.factory = factory;
    }

    @Override
    public void prepare() {
        // TODO Auto-generated method stub
        this.flower = factory.createFlower();
        this.fruit = null;
    }

}
