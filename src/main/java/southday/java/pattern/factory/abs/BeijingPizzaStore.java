package southday.java.pattern.factory.abs;

import southday.java.pattern.factory.abs.factory.BeijingIngredientFactory;
import southday.java.pattern.factory.abs.factory.IngredientFactory;

/**
 * 北京披萨分店
 * @author southday
 * @date 2017年2月20日
 */
public class BeijingPizzaStore extends PizzaStore {

    @Override
    protected Pizza createPizza(String pizzaType) {
        // TODO Auto-generated method stub
        
        // 先准备原料
        Pizza pizza = null;
        IngredientFactory factory = new BeijingIngredientFactory();
        if ("fruit".equals(pizzaType)) {
            pizza = new FruitPizza(factory);
        } else if ("flower".equals(pizzaType)) {
            pizza = new FlowerPizza(factory);
        }
        pizza.prepare();
        
        return pizza;
    }

}
