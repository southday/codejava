package southday.java.basic.collection;

/*
 * JDK1.5版本新特性   --- 可变参数
 * 在使用时，可变参数一定要定义在参数列表的最后面，如：
 *     |-- public static void show(int... arr, String str) {...} 这种写法是错误的，编译不通过!
 *     |-- public static void show(String str, int... arr) {...} 必须把可变参数定义在最后面!
 */

public class ParamMethodDemo {
    public static void main(String[] args) {
        show2(); // [I@15db9742 --> 0
        show2(1, 2, 3); // [I@6d06d69c --> 3
        show2(1, 2, 4, 5, 6, 7); // [I@7852e922 --> 6 
    }

    // 【1】现在我定义了一个输出两个参数的方法 show
    public static void show(int a, int b) {
        sop(a + " : " + b);
    }
    // 【2】而根据需要，我现在需要一个输出三个参数的方法，那么我就需要重载
    public static void show(int a, int b, int c) {
        sop(a + " : " + b + " : " + c);
    }
    // 【3】那么当还需要一个输出 4个、5个、6个参数的方法呢？是不是还得继续重载呢？
    // 这样虽然行得通，但是太麻烦，也不符合程序设计的理念
    public static void show1(int[] arr) {
        // 我们这样定义后，是不是就可以传个数组进来，就不需要重载来定义带有很多参数的方法了
        // 但是这个方法也有不足之处，就是传个参数就需要定义一个数组，显得很麻烦！！
    }
    
    /* 【4】JDK1.5版本后出现了新特性：
     * 【可变参数】 其实就是【3】中方法数组的简写形式，不用每次都手动地建立数组对象，
     * 只要将要操作的参数传入即可，隐式地将这些参数封装成数组（封装数组的操作由虚拟机帮你完成）
     */
    public static void show2(int... arr) { // 这 arr 代表一个数组
        sop(arr + " --> " + arr.length);
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
