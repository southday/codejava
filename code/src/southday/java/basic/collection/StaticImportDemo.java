package southday.java.basic.collection;

import java.util.Arrays; // import 后面接 类,可以是单独一个类，也可以是一个包中的所有类
import static java.util.Arrays.*; // 导入 Arrays 中所有的静态成员(方法 & 变量)
import static java.lang.System.*; // 静态导入 System 类中所有的静态成员
/*
 * static import 静态导入
 *       【import static 后面接的是  某一个类中所有的静态成员】
 * 当类名重名时，需要指定具体的包名;
 * 当方法重名时，需要指定具体的所属对象或者类;
 */

public class StaticImportDemo {
    public static void main(String[] args) {
        int[] arr = {1, 3, 5};
        
        // 【1】你会发现，每次要调用 sort 都需要在前面写 Arrays，还有没有更简化的书写呢?
//        Arrays.sort(arr);
//        System.out.println(Arrays.toString(arr));
        
        // 【2】那就是静态导入  --->  import static java.util.Arrays.*;
        // 将Arrays 中所有的静态成员导入，这样一来就可以简写如下：
        sort(arr);
        System.out.println(Arrays.toString(arr));
        /* 【3】上面的 System.out.println(Arrays.toString(arr));
         * 为什么不写成 System.out.println(toString(arr))呢？
         * 【答】：因为StaticImportDemo 默认是 extends Object类的，而Object类中也有toString()方法
         * 如果省略Arrays，那么方法中默认是先调用extends Object来的toString()方法，而该方法中是没有参数的
         */
        
        // 【4】 此外，在写输出语句时，老是写System是不是很烦？其实还可以使用静态导入：
        // import static java.lang.System.*; 之后就可以这样写了：
        out.println("hahahah");
    }
}
