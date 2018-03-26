package southday.java.basic.thread.jmc.chapter6;

/* LazyMan1.java 中提到：在方法getInstance()中使用 synchronized 关键字是低效的，
 * 所以我们要改成 同步代码块！
 * 
 * 傻眼了不？！ 是不是又出现了多个实例了！
 * 你会想，怎么改成同步代码块就搞得非线程安全了呢？？
 * 
 * 原因：
 * 依旧假设有两个线程A，B同时进入了 getInstance()方法中，并且因为
 * 判断语句 if (lm == null) {} 没有同步，所以两个线程都进入了 if(){}方法中
 *     if (lm == null) {
 *         synchronized (LazyMode2.class) {
 *             lm = new LazyMode2();
 *         }
 *     }
 * 这时，线程A抢到了 锁，它执行完了 lm = new LazyMode2(); 假设此时的lm.hashCode() = 1
 * 然后，线程B也得到了 锁，它也执行了 lm = new LazyMode2(); lm 这个引用就指向了另一个实例对象，
 * 原来hashCode() = 1的实例被当作垃圾回收了，lm指向了新的对象实例，假设其hashCode() = 2
 * 此时，线程C进来了，执行判断语句 if (lm == null)，返回false，线程C得到的实例，其hashCode() == 2
 */

class LazyMode2 {
    private static LazyMode2 lm = null;
    
    private LazyMode2() {}
    
    public static LazyMode2 getInstance() {
        if (lm == null) {
            // 这里是用不了 this 锁的，因为加载该方法时还没对象呢！
            synchronized (LazyMode2.class) {
                lm = new LazyMode2();
            }
        }
        return lm;
    }
}

public class LazyMan2 {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " get a LazyMode2 instance, " 
                        + LazyMode2.getInstance().hashCode());
            }
        };
        
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
    }
}
