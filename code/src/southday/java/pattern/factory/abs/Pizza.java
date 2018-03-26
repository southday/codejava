package southday.java.pattern.factory.abs;

import southday.java.pattern.factory.abs.ingredient.Flower;
import southday.java.pattern.factory.abs.ingredient.Fruit;

/**
 * 抽象工厂 - 披萨
 * @author southday
 * @date 2017年2月20日
 */
public abstract class Pizza {
    protected Flower flower = null;
    protected Fruit fruit = null;
    
    public abstract void prepare();
    
    public final void cut() {
        System.out.println("cut...");
    }
    
    public final void bake() {
        System.out.println("bake...");
    }
    
    public final void box() {
        System.out.println("box...");
    }
}
