package southday.java.basic.thread.jmc.chapter4;

import java.util.concurrent.locks.ReentrantLock;

/* 本例简要介绍了ReentrantLock 的中lock()和unlock()的使用，
 * lock()和unlock()搭配可以让同步更加灵活：
 * 
 * []:线程 持A锁， 持B锁: 
 * 1) 使用synchronized()方法:
 * 在释放时就必须是： 释放B锁， 释放A锁 这样的顺序
 * 2) 使用lock()，unlock():
 * 在释放锁时，可以先释放A锁，再释放B锁
 * 
 */
public class ReentrantLockDemo0 {
    static ReentrantLock lock = new ReentrantLock();
    
    public static void main(String[] args) {
        
        Thread Thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                ReentrantLockDemo0.test();
            }
        });
        
        Thread Thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ReentrantLockDemo0.test();
            }
        });
        
        Thread0.start();
        Thread1.start();
    }
    
    public static void test() {
        lock.lock();
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName());
        lock.unlock();
    }
}
