package southday.java.basic.concurrent.jmc.c02;

/* 本例只想说明：以下两种情况使用的锁时一样的！
 * 
 * 1） public static synchronized show() {...}
 * 2） public void show() {
 *       synchronized (类名.class) {...}
 *    }
 * 两种情况使用的都是   类名.class   锁
 * 
 * 可以发现：不管运行几次，执行结果都是
 * 一个线程 进 退，之后另一个线程才 进 退
 */

public class SynchronizedClassLock {
    public static void main(String[] args) {
        SynchronizedClassLock scl = new SynchronizedClassLock();
        Thread Thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedClassLock.show();
            }
        });
        Thread Thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                scl.show2();
            }
        });
        Thread1.start();
        Thread2.start();
    }
    
    public static synchronized void show() {
        System.out.println(Thread.currentThread().getName() + " in ");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println(Thread.currentThread().getName() + " out ");
    }
    
    public void show2() {
        synchronized (SynchronizedClassLock.class) {
            System.out.println(Thread.currentThread().getName() + " in ");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println(Thread.currentThread().getName() + " out ");
        }
    }
}
