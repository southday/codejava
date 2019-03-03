package southday.java.jvm;

/**
 * 引用计数算法进行GC测试，测试对象间循环引用时的GC情况<br/>
 * 打印GC日志：vm args: -XX:+PrintGCDetails（无效，改为使用：-Xlog:gc*）
 * @author southday
 * @date 2019/3/2
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;
    // 这个成员属性的唯一意义就是占点内存，以便能在GC日志中看清楚是否被回收过
    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        ReferenceCountingGC a = new ReferenceCountingGC();
        ReferenceCountingGC b = new ReferenceCountingGC();
        // 互相引用
        a.instance = b;
        b.instance = a;
        // 置空，但是a.instance还在引用b之前指向的实例，b.instance也是一样
        a = null;
        b = null;
        // 强制GC，查看a和b是否可以被回收？
        System.gc();
    }
}
