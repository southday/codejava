package southday.java.basic.collection;

import java.util.*;
/* 继 ArraysDemo1后，来看看   集合 变  数组
 * Collection 接口中的方法
 * (1) Object[] toArray() // 返回包含此 collection 中所有元素的数组
 * (2) <T> T[] toArray(T[] a)
 *         |-- a - 存储此 collection 元素的数组（如果其足够大）；否则，将为此分配一个具有相同运行时类型的新数组
 *      |-- 返回包含此 collection 中所有元素的数组
 *      |-- toArray(new Object[0]) 和  toArray()的功能是相同的
 */

public class ArraysDemo2 {
    public static void main(String[] args) {
        ArrayList<String> al = new ArrayList<String>();
        al.add("hhh");
        al.add("pppp");
        al.add("e");
        al.add("cc");
        
        /*
         * 1. 指定类型的数组，到底要定义多长呢？
         *     (1) 当指定类型的数组类型长度  小于了  集合的size，那么该方法的内部会创建一个新的数组，长度为集合的size
         *     (2) 当指定类型的数组类型长度  大于了  集合的size，就不会创建新的数组，而是使用传递进来的数组
         *     (3) 所以创建长度刚刚好的数组最优！！！
         * 
         * 2. 为什么要将集合变数组？？
         *     其实是为了限定对元素的操作↓
         *  老子集合操作完毕后，转成数组返回给你，就这么多元素，你无法 增、删，限定你的操作！！
         */
        String[] arr = al.toArray(new String[6]); // [hhh, pppp, e, cc, null, null]
        sop(Arrays.toString(arr));
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
