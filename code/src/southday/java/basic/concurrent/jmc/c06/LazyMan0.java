package southday.java.basic.concurrent.jmc.c06;

/* 本例为 “LazyMan”系列第一个类，从最简单的部分开始，一点一点分析
 * “懒汉式”的问题所在，及解决办法。
 * 
 * 通过本系列代码演示后，大家就会明白为什么开发的时候还是选“饿汉式”比较好，
 * 此外会对线程安全更加重视，因为稍不注意就会犯错
 * 
 * 经过测试，你会发现：
 * Oh, my god! 说好的单例呢？ 结果却蹦出了至少两个实例！
 * 
 * 原因：
 * 现在假设有两个线程A，B， 他们两都进入了 getInstance()方法中，
 *    if (lm == null) {
 *        lm = new LazyMode0();
 *    }
 * 
 * 1) 两个线程同时判断 if (lm == null) 语句，结果都是 true，于是
 * A，B两线程各自new了一个LazyMode0实例，所以导致了非单例！
 * 
 * 2) 假设线程A先判断 if (lm == null) 语句，而当线程A正打算new对象时，
 * 线程B抢了线程A的执行权，然后判断 if (lm == null)，结果线程B也进入了if(){}方法中，
 * 这样两个线程都可以new 对象了，就导致了非单例！
 */

class LazyMode0 {
    private static LazyMode0 lm = null;
    
    private LazyMode0() {}
    
    public static LazyMode0 getInstance() {
        if (lm == null) {
            lm = new LazyMode0();
        }
        return lm;
    }
}

public class LazyMan0 {
    public static void main(String[] args) {
        // 很明显，最初的“懒汉式”已经满足了单线程安全，该系列主要测试多线程安全问题
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " get a LazyMode0 instance, " 
                        + LazyMode0.getInstance().hashCode());
            }
        };
        
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
    }
}
