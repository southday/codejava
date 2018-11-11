package southday.java.thinkinginjava.c15.s03;

import java.util.Iterator;
import java.util.Random;

/**
 * 
 * @author southday
 * @date 2018年10月25日
 */
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {
    // 写为：Class<Coffee>[] types = ... 会报错：Cannot create a generic array of Class<Coffee>
    // 原因等看完后面的内容再来补充
    private Class<?>[] types = {Latte.class, Mocha.class, Cappuccino.class,
                             Americano.class, Breve.class};
    private static Random rand = new Random(47);
    
    public CoffeeGenerator() {}
    
    private int size = 0;
    public CoffeeGenerator(int sz) {
        this.size = sz;
    }
    
    @Override
    public Coffee next() {
        // Generator<Coffee>
        try {
            return (Coffee)types[rand.nextInt(types.length)].getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator<Coffee> iterator() {
        // Iterable<Coffee> 实现该接口后，可以使用foreach语法糖（就是编译时期转为了对象的iterator()）
        return new CoffeeIterator();
    }
    
    // 内部迭代器，由 iterator()方法引用
    class CoffeeIterator implements Iterator<Coffee> {
        int count = size;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Coffee next() {
            count--;
            // 在内部方法中使用：OuterClass.this.xxx，表示使用外部类对象的xxx
            return CoffeeGenerator.this.next();
        }
    }
    
    // ---------------------------------------------------------------
    // 测试
    public static void main(String[] args) {
        CoffeeGenerator gen = new CoffeeGenerator();
        for (int i = 0; i < 5; i++)
            System.out.println(gen.next());
        System.out.println("-------------------------");
        for (Coffee c : new CoffeeGenerator(5))
            System.out.println(c);
    }
}
