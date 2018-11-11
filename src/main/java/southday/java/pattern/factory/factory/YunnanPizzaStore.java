package southday.java.pattern.factory.factory;

import southday.java.pattern.factory.common.MangoPizza;
import southday.java.pattern.factory.common.Pizza;

/**
 * 工厂模式 - 云南披萨分店
 * @author southday
 * @date 2017年2月19日
 */
public class YunnanPizzaStore extends PizzaStore {

    @Override
    public Pizza createPizza(String pizzaType) {
        // TODO Auto-generated method stub
        
        /* 云南芒果好吃，所以这里我就创建了芒果披萨，这里同样可以使用简单工厂来生产云南分店的披萨
         * 为了突出重点，我就直接new对象了
         */
        return new MangoPizza();
    }

}
