package southday.java.thinkinginjava.c15.s04;

import southday.java.thinkinginjava.c15.s03.Generator;

/**
 * 为任何类构造一个Generator
 * @author southday
 * @date 2018年10月25日
 */
public class BasicGenerator<T> implements Generator<T> {
    private Class<T> type;
    
    public BasicGenerator(Class<T> type) {
        this.type = type;
    }
    
    public T next() {
        try {
           return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    // 节省代码，不必写成：new BasicGenerator<MyType>(MyType.class);
    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<T>(type);
    }
}
