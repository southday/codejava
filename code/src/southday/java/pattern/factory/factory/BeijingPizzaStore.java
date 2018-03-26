package southday.java.pattern.factory.factory;

import southday.java.pattern.factory.common.ApplePizza;
import southday.java.pattern.factory.common.Pizza;

/**
 * 工厂模式 - 北京披萨分店
 * @author southday
 * @date 2017年2月19日
 */
public class BeijingPizzaStore extends PizzaStore {

    @Override
    public Pizza createPizza(String pizzaType) {
        // TODO Auto-generated method stub
        return new ApplePizza();
    }

}
