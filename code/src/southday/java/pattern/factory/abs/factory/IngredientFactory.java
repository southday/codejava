package southday.java.pattern.factory.abs.factory;

import southday.java.pattern.factory.abs.ingredient.Flower;
import southday.java.pattern.factory.abs.ingredient.Fruit;

/**
 * 原料工厂
 * @author southday
 * @date 2017年2月20日
 */
public interface IngredientFactory {
    
    Fruit createFruit();
    Flower createFlower();
}
