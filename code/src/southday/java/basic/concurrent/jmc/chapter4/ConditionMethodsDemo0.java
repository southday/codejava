package southday.java.basic.concurrent.jmc.chapter4;

import java.util.Calendar;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* 本例主要讲[Condition]中的两个方法：
 * [awaitUninerruptibly()]:
 *     当 当前线程 进入该方法时 或 线程正在等待时，被interrupt()，这个线程会继续等待，直到被signal
 * 
 * [awaitUnitl(Date deadLine)]:
 *     当前线程等待，直到：  1）线程被唤醒        2）线程被中端        3）指定的时间已到
 *     这三种情况发生时，停止等待。对于(3)，线程会自动唤醒
 *             
 */
class ConditionMethods {
    static ReentrantLock lock = new ReentrantLock();
    static Condition con = lock.newCondition();
    
    public static void test1() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " in, and await");
            con.awaitUninterruptibly(); // (1) 
//            con.await();                // (2) 如果使用await()，则线程被终端时会报错
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public static void test2() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 5);
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " begin await");
            con.awaitUntil(calendar.getTime()); // 时限是5秒后
            System.out.println(Thread.currentThread().getName() + " end await");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public static void test2signal() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " signal");
            con.signal();
        } finally {
            lock.unlock();
        }
    }
}

public class ConditionMethodsDemo0 {
    public static void main(String[] args) {
//        testAwaitUninterruptibly();
        testAwaitUntil();
    }
    
    public static void testAwaitUninterruptibly() {
        Thread Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConditionMethods.test1();
            }
        });
        Thread.setName("A");
        Thread.start();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        Thread.interrupt();
        System.out.println("main --> 使用awaitUninterruptibly()，线程被中断时会继续等待，不会报错");
    }
    
    public static void testAwaitUntil() {
        Thread Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConditionMethods.test2();
            }
        });
        Thread.setName("A");
        Thread.start();
        try {
            Thread.sleep(3000);
//            ConditionMethods.test2signal();
            /* 线程在指定时间到之前可以被其他线程signal，
             * 或者指定时间一到，自动唤醒
             */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
