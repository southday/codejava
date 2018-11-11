package southday.java.basic.concurrent.jmc.c06;

/* LazyMan0.java 中 说道了 最初的 ”懒汉式“ 存在非线程安全问题，
 * 本例就来讲解，如何解决 ”懒汉式“的非线程安全问题
 *
 * 解决的方法很简单，就是在
 * public static LazyMode1 getInstance() {...} 中
 * 加上 synchronized 关键字，这样就可以实现多线程同步访问了，就不会有多个实例的出现了！
 * 
 * 但是！
 * 同步是很耗时的，当 lm != null 时， 其实是没必要每个线程执行getInstance()时都去同步的，
 * 所以，为了提高效率，我们改用 同步代码块 来替换方法中的 synchronized 关键字
 * 
 */

class LazyMode1 {
    private static LazyMode1 lm = null;
    
    private LazyMode1() {}
    
    public synchronized static LazyMode1 getInstance() {
        if (lm == null) {
            lm = new LazyMode1();
        }
        return lm;
    }
}

public class LazyMan1 {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " get a LazyMode1 instance, " 
                        + LazyMode1.getInstance().hashCode());
            }
        };
        
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
    }
}
