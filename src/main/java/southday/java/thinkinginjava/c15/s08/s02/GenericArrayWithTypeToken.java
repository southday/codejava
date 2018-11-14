package southday.java.thinkinginjava.c15.s08.s02;

import java.lang.reflect.Array;

/**
 * 使用类型标签和反射机制，运行时动态创建泛型类型数组T[]
 * @author southday
 * @date 2018年10月26日
 */
public class GenericArrayWithTypeToken<T> {
    private T[] array;
    
    @SuppressWarnings("unchecked")
    public GenericArrayWithTypeToken(Class<T> type, int sz) {
        array = (T[])Array.newInstance(type, sz);
    }
    
    public void put(int index, T item) {
        array[index] = item;
    }
    
    public T get(int index) {
        return array[index];
    }
    
    public T[] rep() {
        System.out.println("rep()");
        return array;
    }
    
    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> gai = new GenericArrayWithTypeToken<Integer>(Integer.class, 10);
        // 对比 GenericArray.java，现在下面的语句可以正常工作
        Integer[] ia = gai.rep();
    }
    
    /* 即使Java类库源代码中出现了某些协管用法，如：elementData = (E[])new Object[size];
     * 也不能表示这就是正确的解决之道；
     * 对比GenericArray.java，本类中内部使用的运行时类型是确切类型T[]（反射机制创建）
     */
}
