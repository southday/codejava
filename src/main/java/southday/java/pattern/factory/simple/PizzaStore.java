package southday.java.pattern.factory.simple;

import southday.java.pattern.factory.common.Pizza;

/**
 * 简单工厂模式
 * @author southday
 * @date 2017年2月19日
 */
public class PizzaStore {
    
    public final Pizza orderPizza(String pizzaType) {
        // 将实例化对象的逻辑放到了工厂PizzaFactory中处理
        Pizza pizza = PizzaFactory.createPizza(pizzaType);
        
        pizza.bake();
        pizza.cut();
        pizza.box();
        
        return pizza;
    }
}

/* 简单工厂模式：
 *  1) 将实例化对象的逻辑抽离出来放到工厂里
 *  2) 若以后有新口味的pizza，只需要修改PizzaFactory中的createPizza方法
 *  3) 虽然避免不了修改，但是把实例化的逻辑独立出来，让orderPizza逻辑变得更清晰
 */