package southday.java.pattern.factory.abs;

import southday.java.pattern.factory.abs.factory.IngredientFactory;
import southday.java.pattern.factory.abs.factory.YunnanIngredientFactory;

/**
 * 云南披萨分店
 * @author southday
 * @date 2017年2月20日
 */
public class YunnanPizzaStore extends PizzaStore {

    @Override
    protected Pizza createPizza(String pizzaType) {
        // TODO Auto-generated method stub
        
        // 先准备原料
        Pizza pizza = null;
        IngredientFactory factory = new YunnanIngredientFactory();
        if ("fruit".equals(pizzaType)) {
            pizza = new FruitPizza(factory);
        } else if ("flower".equals(pizzaType)) {
            pizza = new FlowerPizza(factory);
        }
        pizza.prepare();
        
        return pizza;
    }

}
