package southday.java.pattern.factory.simple;

import southday.java.pattern.factory.common.ApplePizza;
import southday.java.pattern.factory.common.BananaPizza;
import southday.java.pattern.factory.common.Pizza;

/**
 * 简单工厂 - Pizza工厂
 * @author southday
 * @date 2017年2月19日
 */
public class PizzaFactory {
    
    public static Pizza createPizza(String pizzaType) {
        Pizza pizza = null;
        
        if ("Apple".equals(pizzaType)) {
            pizza = new ApplePizza();
        } else if ("Banana".equals(pizzaType)) {
            pizza = new BananaPizza();
        }
        
        return pizza;
    }
}
