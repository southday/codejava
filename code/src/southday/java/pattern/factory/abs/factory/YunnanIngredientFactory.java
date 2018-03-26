package southday.java.pattern.factory.abs.factory;

import southday.java.pattern.factory.abs.ingredient.Apple;
import southday.java.pattern.factory.abs.ingredient.Flower;
import southday.java.pattern.factory.abs.ingredient.Fruit;
import southday.java.pattern.factory.abs.ingredient.Rose;

/**
 * 云南原料工厂
 * @author southday
 * @date 2017年2月20日
 */
public class YunnanIngredientFactory implements IngredientFactory {

    @Override
    public Fruit createFruit() {
        // TODO Auto-generated method stub
        return new Apple();
    }

    @Override
    public Flower createFlower() {
        // TODO Auto-generated method stub
        return new Rose();
    }

}
