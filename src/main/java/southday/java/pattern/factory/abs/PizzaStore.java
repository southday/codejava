package southday.java.pattern.factory.abs;

/**
 * 披萨店
 * @author southday
 * @date 2017年2月20日
 */
public abstract class PizzaStore {
    
    public final Pizza orderPizza(String pizzaType) {
        Pizza pizza = createPizza(pizzaType);
        
        pizza.bake();
        pizza.cut();
        pizza.box();
        
        return pizza;
    }
    
    protected abstract Pizza createPizza(String pizzaType);
}
