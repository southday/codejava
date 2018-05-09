package southday.java.basic.concurrent.jmc.chapter6;

/* LazyMan2.java 中出现了把synchronized 关键字换成了 同步代码块就导致了非线程安全问题，
 * 主要原因，在LazyMan2.java中也解释了，现在我们只需要再加个判断就能实现线程安全，
 * 即：对 引用的双重判断！
 * 
 * 到了 LazyMan3.java，终于把“懒汉式”非线程安全的问题较好的解决了（我也不敢说完美解决），
 * 大家可以发现，写起来比“饿汉式”麻烦多了，而且判断来判断去的，还要进行同步，效率确实低了。
 * 所以开发过程中，推荐大家使用“饿汉式”，”懒汉式“只是面试之类的会问到些相关知识点！
 */

class LazyMode3 {
//    private static LazyMode3 lm;
    // 为什么用 volatile修饰，请看 --> StaticInnerClassSignetion.java
    private static volatile LazyMode3 lm = null; 
    
    private LazyMode3() {}
    
    public static LazyMode3 getInstance() {
        if (lm == null) {
            synchronized (LazyMode3.class) {
                if (lm == null) {
                    lm = new LazyMode3();
                }
            }
        }
        return lm;
    }
}

public class LazyMan3 {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " get a LazyMode3 instance, " 
                        + LazyMode3.getInstance().hashCode());
            }
        };
        
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
    }
}
