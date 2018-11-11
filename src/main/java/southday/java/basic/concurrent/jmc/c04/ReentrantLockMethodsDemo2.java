package southday.java.basic.concurrent.jmc.c04;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/* 本例主要讲述[ReentrantLock类]常用方法:
 * 
 * 不做代码演示的方法：
 *     [isFair()]: 判断是不是公平锁, lock.isFair();
 *     [isHeldByCurrentThread()]: 判断是不是当前线程持有该锁, lock.isH...
 *     [isLocked()]: 判断 锁lock 是否被任意线程获得, lock.isLocked()
 *     [tryLock(long time, TimeUnit unit)]: 在给定的时间内，试图取tryLock()
 * 
 * 代码演示的方法：
 *     [lockInterruptibly()]: 如果当前线程未被中断，则获取锁
 *     [tryLock()]: 当lock没有被其他线程获取时，才试图取获取lock
 */

class MethodsTest2 {
    static ReentrantLock lock = new ReentrantLock();
    static Condition con = lock.newCondition();
    
    public static void test1() {
        try {
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " in");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " out");
            lock.unlock();
        }
    }
    
    public static void test2() {
        if (lock.tryLock()) { 
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " get lock");
            try {
                System.out.println(Thread.currentThread().getName() + " in");
            } finally {
                System.out.println(Thread.currentThread().getName() + " out");
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " don't get lock");
        }
    } // end of test2
}

public class ReentrantLockMethodsDemo2 {
    public static void main(String[] args) {
//        testLockInterruptibly();
        testTryLock();
    }
    
    public static void testLockInterruptibly() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MethodsTest2.test1();
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
            Thread1.interrupt();
            System.out.println("main end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /* 会报两个错：
         * 错1) 线程B 被中断，其执行了lockInterruptibly(); -> InterrtupedException
         * 错2) 线程B 在没有获得锁的情况下执行了 finally{}中的lock.unlock(); -> IllegalMonitorStateException
         */
    }
    
    public static void testTryLock() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MethodsTest2.test2();
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
