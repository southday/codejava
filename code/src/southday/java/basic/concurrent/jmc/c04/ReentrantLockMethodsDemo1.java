package southday.java.basic.concurrent.jmc.c04;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* 本例主要介绍[ReentrantLock类]中的一些常用方法：
 * [hasQueuedThread(Thread Thread)]:
 *     测试 指定线程Thread 是否在等待获取此锁
 * 
 * [hasQueuedThreads()]:
 *     测试 是否有线程在等待获取此锁
 * 
 * [hasWaiters(Condition condition)]:
 *     测试 是否有线程正在等待与 此锁 有关的 condition 条件
 * 
 */

class MethodsTest1 {
    static ReentrantLock lock = new ReentrantLock();
    static Condition con = lock.newCondition();
    
    /**
     * 该test主要用于测试：
     * [hasQueuedThread(Thread Thread)]
     * [hasQueuedThreads()]
     */
    public static void test1() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " in, and sleep...");
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public static void test2() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " in, and await...");
            con.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally { 
            lock.unlock();
        }
    }
    public static void test2signal() {
        lock.lock();
        try {
            System.out.println("是否有线程正在等待与lock有关的conditon ? " + lock.hasWaiters(con));
            if (lock.hasWaiters(con)) {
                System.out.println("如果有，有几个？ --> " + lock.getWaitQueueLength(con));
            }
            con.signal();
        } finally {
            System.out.println("唤醒了 1个 线程");
            lock.unlock();
        }
    }
}

public class ReentrantLockMethodsDemo1 {
    public static void main(String[] args) {
//        testHasQueuedThread();
        testHasWaiter();
    }
    
    public static void testHasQueuedThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MethodsTest1.test1();
            }
        };
        try {
            Thread Thread0 = new Thread(runnable);
            Thread0.setName("A");
            Thread0.start();
            Thread.sleep(1000);
            Thread Thread1 = new Thread(runnable);
            Thread1.setName("B");
            Thread1.start();
            Thread.sleep(1000);
            System.out.println("线程A 正在等待lock释放 ? " + MethodsTest1.lock.hasQueuedThread(Thread0));
            System.out.println("有线程正在等待lock释放 ? " + MethodsTest1.lock.hasQueuedThreads());
            if (MethodsTest1.lock.hasQueuedThreads()) {
                System.out.println("如果有，那么有多少线程在等待lock释放 ? --> " + MethodsTest1.lock.getQueueLength());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void testHasWaiter() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MethodsTest1.test2();
            }
        };
        try {
            Thread[] Threads = new Thread[3];
            for (int i = 0; i < 3; i++) {
                Threads[i] = new Thread(runnable);
                Threads[i].start();
            }
            Thread.sleep(1000);
            for (int i = 0; i < 4; i++) {
                MethodsTest1.test2signal();
                Thread.sleep(1000);
            }
            System.out.println("最后一个 \"唤醒了 1个 线程\" 就不用管了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
