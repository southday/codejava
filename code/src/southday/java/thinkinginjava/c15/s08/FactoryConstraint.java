package southday.java.thinkinginjava.c15.s08;

interface FactoryI<T> {
    T create();
}

class Foo2<T> {
    private T x;
    // 传入显示工厂
    public <F extends FactoryI<T>> Foo2(F factory) {
        x = factory.create();
    }
    
    public T get() { return x; }
}

class IntegerFactory implements FactoryI<Integer> {
    public Integer create() {
        return Integer.valueOf(0);
    }
}

class DoubleFactory implements FactoryI<Double> {
    public Double create() {
        return Double.valueOf(0.0);
    }
}

/**
 * {@link InstantiateGenericType}类中的T没有默认构造器的错误，无法在编译器捕获，因此提出显示的工厂，
 * 并限制其类型，达到编译期检测的目的。
 * @author southday
 * @date 2018年10月26日
 */
public class FactoryConstraint {
    public static void main(String[] args) {
        new Foo2<Integer>(new IntegerFactory());
        // new Foo2<Double>(new IntegerFactory()); // 这种写法，编译期间就能捕获到错误
        new Foo2<Double>(new DoubleFactory());
    }
}
