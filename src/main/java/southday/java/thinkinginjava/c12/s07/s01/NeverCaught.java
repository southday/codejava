package southday.java.thinkinginjava.c12.s07.s01;

/*
Throwable对象可分为两种：
    1) Error：表示编译时或系统错误（除特殊情况外，一般不用开发者关心）
    2) Exception：是可以被抛出的基本类型，Java程序员需要关心

1) 运行时异常的类型很多，它们会自动被java虚拟机抛出，所以不必在异常说明中把它们列出来；
2) RuntimeException（或任何从RuntimeException继承的异常），被称为“不受检查异常”，这类异常属于错误，将被自动捕获；
3) RuntimeException（或它的子类）是一个特例，对于这种异常，编译器不需要异常说明（即在方法中声明：throws RuntimeException）；

[注意]：
只能在代码中忽略RuntimeException（及其子类）类型的异常，其他类型异常的处理都是有编译器强制实施的。
究其原因，RuntimeException代表的是编程错误。
 */

/**
 * Author southday
 * Date   2018/11/15
 */
public class NeverCaught {

    // RuntimeException（或它的子类）是一个特例，对于这种异常，编译器不需要异常说明（即在方法中声明：throws RuntimeException）；
    static void f1() {
        throw new RuntimeException("From f()");
    }
    static void g1() {
        f1();
    }

    // 需要声明 throws Exception
    static void f2() throws Exception {
        throw new Exception();
    }

    public static void main(String[] args) {
        // RuntimeException没有被捕获而直达main()，程序在退出前将调用异常的printStackTrace()方法
        g1();
    }
}
