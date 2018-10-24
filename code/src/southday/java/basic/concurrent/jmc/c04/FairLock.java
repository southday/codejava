package southday.java.basic.concurrent.jmc.c04;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* 本例主要讲：[公平锁] [非公平锁]
 * [公平锁]:
 *     线程获取锁的顺序是按线程加锁的顺序来分配的，即FIFO顺序，先到先得
 * [非公平锁]:
 *     获取锁的一种抢占机制，是随机获得锁的，这种方式可能导致某一线程永远拿不到锁
 * 
 * 《java多线程编程核心技术》中用了一个和本例差不多的例子，但为感觉并不能完美地
 * 说明公平锁和非公平锁，因为：
 *     public void run() {
 *         System.out.println(Thread.currentThread().getName() + " run...");
           flock.test();
       }
    作者用了这么一个输出语句，是想表明线程加锁的顺序（即：线程等待锁释放的顺序），然而
    并不是线程执行了这条输出语句就证明它被加锁，因为{输出语句;flock.test();}这两条语句
    这不同步的，即可能存在：
    Thread0 run...
    Thread1 run...
    然而执行flock.test()的顺序是：
    Thread1 -> flock.test();
    Thread0 -> flock.test();
    所以为觉得这个例子不能很好的证明公平锁与非公平锁的机制，而若把这两条语句同步，却又违背
    了我们想要解释的东西。作者还真是忽悠小白...
    
    new ReentrantLock() --> 默认是不公平锁
 */

public class FairLock {
    private Lock lock;
    
    public FairLock(boolean isFair) {
        this.lock = new ReentrantLock(isFair);
    }
    
    public static void main(String[] args) {
        FairLock flock = new FairLock(true);
        
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " run...");
                flock.test();
            }
        };
        
        Thread[] Threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            Threads[i] = new Thread(thread);
        }
        for (int i = 0; i < 3; i++) {
            Threads[i].start();
        }
    }
    
    public void test() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " lock()");
        } finally {
            System.out.println(Thread.currentThread().getName() + " unlock()");
            lock.unlock();
        }
    }
}
