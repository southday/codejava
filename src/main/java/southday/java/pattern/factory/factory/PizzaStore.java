package southday.java.pattern.factory.factory;

import southday.java.pattern.factory.common.Pizza;

/**
 * 工厂模式 - 抽象披萨店
 * @author southday
 * @date 2017年2月19日
 */
public abstract class PizzaStore {
    
    public final Pizza orderPizza(String pizzaType) {
        // 将创建披萨的任务交给披萨分店去完成
        Pizza pizza = createPizza(pizzaType);
        
        pizza.bake();
        pizza.cut();
        pizza.box();
        
        return pizza;
    }
    
    public abstract Pizza createPizza(String pizzaType);
}

/* 这样一来，即使有新的分店加入，我们可以保证：
 *  1) 原有的orderPizza()流程不变
 *  2) 创建pizza可以根据各个分公司的喜好来创建
 *  3) 我们在写代码的时候使用的是PizzaStore.orderPizza()，根本不用管子类怎么创建的pizza，创建了什么pizza
 */
