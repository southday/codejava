package southday.java.basic.concurrent.jmc.c04;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* 本例主要介绍一些ReentrantLock类中的常用方法：
 * [getHoldCount]:
 *     获取当前线程持 此锁 的个数（次数），即：执行lock.lock()的次数
 * 
 * [getQueueLength]:
 *     返回正在等待 此锁 释放的线程估计数，不包括那些被sleep(), await()等的线程
 * 
 * [getWaitQueueLength(Condition condition)]:
 *     返回等待与 此锁 相关的给定条件 condition 的线程估计数，
 *     比如：有5线程，每个都执行了condition.await()方法时，返回5
 * 
 * [testReleaseOneLockTwice] 有重点哦！
 */

class MethodsTest {
    static ReentrantLock lock = new ReentrantLock();
    static Condition con = lock.newCondition();
    
    /**
     * 该test是我突发奇想，想看看两次释放同一锁，即两次
     * 执行lock.unlock();会怎么样
     * <p> 我估计第二次unlock()会被自动忽视 </p>
     */
    public static void test1() {
        lock.lock();
        try {
            System.out.println("I get the lock!");
        } finally {
            lock.unlock();
            System.out.println("I release the lock once!");
//            lock.unlock(); // 经过测试，在没有拿到锁的情况下释放锁，会报IllegalMonitorStateExcepion异常
//            System.out.println("I release the lock twice! Is true?");
        }
    }
    
    static int callNum = 0;
    /**
     * 该test主要测试getHoldCount()方法
     */
    public static void test2() {
        if (callNum++ < 2) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " call lock() " 
                    + lock.getHoldCount() + " times");
                test2();
            } finally {
                System.out.println(Thread.currentThread().getName() + " call unlock()");
                lock.unlock();
                // 这里我是要进行 testReleaseOneLockTwice()中的测试才加入下面的语句的，
                // 为的是测试对于同一个锁拿两次，内部锁释放而外部锁没释放的情况下，其他线程可不可以拿到 该锁
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    } // end of test2
    
    /**
     * 该test主要用于测试getQueueLength()方法
     */
    public static void test3() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " in method");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " out method");
            lock.unlock();
        }
    }
    
    /**
     * 该test主要用于测试getWaitQueueLength()方法，与test4signal()搭配
     */
    public static void test4() {
        lock.lock();
        try {
            con.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public static void test4signal() {
        lock.lock();
        try {
            System.out.println("当前处于conditon.await()的线程数: " + lock.getWaitQueueLength(con));
            con.signal();
            System.out.println("唤醒了 1个 线程");
        } finally {
            lock.unlock();
        }
    }
}

public class ReentrantLockMethodsDemo0 {
    public static void main(String[] args) {
        testReleaseOneLockTwice();
//        testGetHoldCount();
//        testGetQueueLength();
//        testGetWaitQueueLength();
    }
    
    public static void testReleaseOneLockTwice() {
        /*
         * 我现在要测试的是，对于test2()
         * 一个线程拿了同一个锁两次， 类似于 lock（lock(..)) 这种结构，
         * 那这个线程在释放锁的时候也时先从内部开始释放的，而既然是同一个锁，
         * 它内部释放完锁后（外部锁还没释放），其他线程可不可以获得 该锁呢？
         */
        Thread Thread = new Thread() {
            @Override
            public void run() {
                MethodsTest.test2();
            }
        };
        Thread.start();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        MethodsTest.test1();
        /* 经过测试发现，
         * 对于同一个锁，拿多次，直到最外部的lock释放前，
         * 其他线程对象都不能获得该锁，
         * [猜测]:是否锁里有个计数器（表示持该锁次数），直到计数器的值为0时，
         *         该锁才能算正真的free or release
         * 刚才瞄了一眼源码，感觉和getState()这个有关，不过没深入去分析
         */
    }
    
    public static void testGetHoldCount() {
        Thread Thread = new Thread() {
            @Override
            public void run() {
                MethodsTest.test2();
            }
        };
        Thread.setName("A");
        Thread.start();
    }
    
    public static void testGetQueueLength() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MethodsTest.test3();
            }
        };
        try {
            Thread[] Threads = new Thread[3];
            for (int i = 0; i < 3; i++) {
                Threads[i] = new Thread(runnable);
                Threads[i].start();
            }
            
            for (int i = 0; i < 6; i++) {
                System.out.println("当前等待lock释放的线程数: " + MethodsTest.lock.getQueueLength());
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void testGetWaitQueueLength() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MethodsTest.test4();
            }
        };
        Thread[] Threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            Threads[i] = new Thread(runnable);
            Threads[i].start();
        }
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        for (int i = 0; i < 3; i++) {
            MethodsTest.test4signal();
        }
    }
}
