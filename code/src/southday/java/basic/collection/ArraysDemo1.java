package southday.java.basic.collection;

import java.util.Arrays;
import java.util.List;

/*
 * Arrays：用于操作数组的工具类，里面都是静态方法
 * 此类包含用来操作数组（比如排序和搜索）的各种方法，此类还包含一个允许将数组作为列表来查看的静态工厂
 * 
 * (1) public static <T> List<T> asList(T.. a);  // 将数组变成List集合  --- a 为支持列表的数组
 */

public class ArraysDemo1 {
    public static void main(String[] args) {
        int[] arr = {1, 6, 9};
        sop(Arrays.toString(arr)); // 将数组变成字符串输出 【1】
        
        String[] str = {"haha", "pp", "xx"};
        /* 把数组变成集合有什么好处？
         * 【答】：可以使用集合的思想和方法来操作数组中的元素，
         * 比如查找数组中是否存在某个元素x：
         *     |-- 数组：使用角标遍历数组来判断
         *     |-- 集合：List<T>.contains(x);
         * 
         * 【注意】：将数组变成集合，不可以使用集合的--增、删方法，因为数组的长度是固定的！
         * 如果你 增、删了，那么会发生java.lang.UnsupportedOperationException (不支持操作异常)
         */
        List<String> ls = Arrays.asList(str); // 将支持列表的数组变成List集合 【2】
        sop("ls = " + ls);
//        ls.add("aa"); // java.lang.UnsupportedOperationException (不支持操作异常)
        
        // 将 arr 转换成List 【3】
        List la = Arrays.asList(arr); // 输出 [[I@15db9742]--代表一个数组
        sop("la = " + la);
        List<int[]> laa = Arrays.asList(arr);
        sop("laa = " + laa);
        /* 【注意】【注意】【注意】
         * 如果数组中的元素都是对象，那么变成集合时，数组中的元素直接转变成集合中的元素
         *     |-- Integer[] num = {1, 2, 3} // 对象型数据类型
         *     |-- List<Integer> ln = Arrays.asList(num);
         * 如果数组中的元素都是基本数据类型，那么会将该数组作为集合中的元素存在
         *  |-- int[] num = {1, 2, 3}  // 基本数据类型
         *  |-- List<int[]> ln = Arrays.asList(num);
         * 
         */
        Integer[] num = {1, 2, 3};
        List<Integer> ln = Arrays.asList(num);
        sop("ln = " + ln);
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
