package southday.java.pattern.factory.bad;

import southday.java.pattern.factory.common.ApplePizza;
import southday.java.pattern.factory.common.BananaPizza;
import southday.java.pattern.factory.common.Pizza;

/**
 * 披萨店
 * @author southday
 * @date 2017年2月19日
 */
public class PizzaStore {
    
    public PizzaStore() {}
    
    public Pizza orderPizza(String pizzaType) {
        Pizza pizza = null;
  
        // 实例化pizza对象
        if ("Apple".equals(pizzaType)) {
            pizza = new ApplePizza();
        } else if ("Banana".equals(pizzaType)) {
            pizza = new BananaPizza();
        }
        
        // 披萨制作的固定流程
        pizza.bake();
        pizza.cut();
        pizza.box();
        
        return pizza;
    }
    
}

/* 思考：
 *  1) 这样的设计，如果后面又加入新口味的披萨，比如：MangoPizza，PearPizza，那我们就得翻开这段代码，重新实现里面的方法。
 * 这种做法违背了设计模式中的【对修改关闭】的原则。
 * 
 *  2) 我们把实例化pizza对象的逻辑放到了orderPizza方法中，使得二者耦合度较高。
 * 以后如果要修改实例化对象的逻辑，会让orderPizza方法变得混乱。
 * 
 */
