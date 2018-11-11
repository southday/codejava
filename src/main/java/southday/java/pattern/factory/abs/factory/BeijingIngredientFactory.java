package southday.java.pattern.factory.abs.factory;

import southday.java.pattern.factory.abs.ingredient.Banana;
import southday.java.pattern.factory.abs.ingredient.Flower;
import southday.java.pattern.factory.abs.ingredient.Fruit;
import southday.java.pattern.factory.abs.ingredient.Lotus;

/**
 * 北京原料工厂
 * @author southday
 * @date 2017年2月20日
 */
public class BeijingIngredientFactory implements IngredientFactory {

    @Override
    public Fruit createFruit() {
        // TODO Auto-generated method stub
        return new Banana();
    }

    @Override
    public Flower createFlower() {
        // TODO Auto-generated method stub
        return new Lotus();
    }

}
