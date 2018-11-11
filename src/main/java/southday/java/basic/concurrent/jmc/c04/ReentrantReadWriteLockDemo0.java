package southday.java.basic.concurrent.jmc.c04;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

/* 本例主要介绍 [ReentrantReadWriteLock] 类的相光知识：
 * 
 * [ReentrantLock]类具有完全互斥排他效果，即同一时间内只能有一个线程在执行ReentrantLock.lock()后的任务，
 * 这样作虽然保证了实例变量的安全性，但效率是低下的，为此引入了 [ReentrantReadWriteLock]类，用来提升效率。
 * 
 * 1） [ReentrantReadWriteLock]有两个锁， 读锁 和 写锁;
 *     读锁：也称“共享锁”
 *     写锁：也称“排他锁”
 * 
 * 2）    a) 多个读锁之间 不互斥
 *     b) 多个写锁之间 互斥
 *     c) 读锁与写锁      互斥
 * 
 */

class ReadWriteLock {
    static ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    static Lock rlock = rwlock.readLock();
    static Lock wlock = rwlock.writeLock();
    
    public static void readtest() {
        rlock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get readLock"
                    + ", time = " + System.currentTimeMillis() + "ms");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rlock.unlock();
        }
    }
    
    public static void writetest() {
        wlock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get writeLock"
                    + ", time = " + System.currentTimeMillis() + "ms");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " release writeLock");
            wlock.unlock();
        }
    }
}

public class ReentrantReadWriteLockDemo0 {
    public static void main(String[] args) {
//        testReadLock();
//        testWriteLock();
        testReadWriteLock();
    }
    
    /**
     * 测试  多个读线程之间不互斥
     */
    public static void testReadLock() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ReadWriteLock.readtest();
            }
        };
        
        Thread[] Threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            Threads[i] = new Thread(runnable);
            Threads[i].start();
        }
        /* 可以发现 3个线程几乎是同一时间获得readLock
         * 这也就证明了：多个读线程之间不互斥
         */
    }
    
    /**
     * 测试     多个写线程之间是互斥的
     */
    public static void testWriteLock() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ReadWriteLock.writetest();
            }
        };
        
        Thread[] Threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            Threads[i] = new Thread(runnable);
            Threads[i].start();
        }
    }
    
    /**
     * 测试     读线程和写线程 互斥
     */
    public static void testReadWriteLock() {
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ReadWriteLock.readtest();
            }
        });
        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ReadWriteLock.writetest();
            }
        });
        readThread.start();
        writeThread.start();
    }
}
