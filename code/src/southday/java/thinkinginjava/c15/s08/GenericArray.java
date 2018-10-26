package southday.java.thinkinginjava.c15.s08;

import java.lang.reflect.Array;

/**
 * 成功创建泛型数组的唯一方式就是创建一个被擦除类型的新数组，然后对其转型</br>
 * 一般来说，在任何想要创建泛型数组的地方都使用ArrayList
 * @author southday
 * @date 2018年10月26日
 */
public class GenericArray<T> {
    // 过早转型，在编译期该数组的实际类型就丢失，并且可能错过某些潜在的错误检查
    // 所以在内部尽量还是使用Object[]，等要传到外部时再进行转型 (T)array[index]
    private T[] array;
    
    @SuppressWarnings("unchecked")
    public GenericArray(int sz) {
        array = (T[])new Object[sz];
    }

    public void put(int index, T item) {
        array[index] = item;
    }
    
    public T get(int index) {
        return array[index];
    }
    
    public T[] rep() {
        // 运行时类型依旧是 Object[]
        System.out.println("rep()");
        return array;
    }
    
    public static void main(String[] args) {
        GenericArray<Integer> gai = new GenericArray<Integer>(10);
        /* Integer[] ia = gai.rep();
         * 这条语句编译期无错，运行时报异常：ClassCastException
         * 因为实际的运行时类型为 Object[]
         */
//        Integer[] ia = gai.rep();
        Object[] oa = gai.rep();
    }
}
