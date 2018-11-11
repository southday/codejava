package southday.java.basic.concurrent.jmc.c03;

/* [wait]: 使线程停止运行
 *  1) 在调用wait()前，线程必须获得该对象的对象级别锁，即只能在同步方法或同步代码块中调用wait()方法
 *  2) 如果调用wait()时没有持适当的锁，则抛出IllegalMonitorStateException, 它是RuntimeException的子类
 *  3) 在执行完wait()后，当前线程放弃锁
 *  4) 调用： 锁.wait();
 *  5) wait()方法可以使调用该方法的线程放弃共享资源的锁，然后从运行状态退出，进入等待队列，直到被再次唤醒
 *  6) 已经执行wait()的线程，再被interrupt()，就会报出:InterruptedException异常
 *  
 * [notify]: 使停止的线程继续运行
 *  1) 与[wait].(1)(2)一样
 *  2) notify()方法执行完后，并不立即释放锁，而被notify()的线程出去等待锁的状态
 *  3) 调用： 锁.notify();
 *  4) 如果发出notify()操作时没有处于阻塞状态的线程，那么该名利就会被忽略
 *  5) notify()方法可以随机唤醒等待队列中等待同一共享资源的”一个“线程，并使该线程退出等待队列，进入可运行状态
 */

public class WatiNotify {
    static int num = 0;
    static Object obj = new Object();
    
    public static void main(String[] args) {
        Thread Thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(Thread.currentThread().getName() + " --> num = " + num++);
                        if (num == 5) {
                            System.out.print("线程" + Thread.currentThread().getName() + "开始wait()");
                            System.out.println(", 并自动释放锁");
                            try { obj.wait(); } catch (InterruptedException e) {}
                            System.out.println("线程" + Thread.currentThread().getName() + "被唤醒");
                        }
                    } // for
                    System.out.println("线程" + Thread.currentThread().getName() + "任务执行完毕");
                }
            } // run
        });
        
        Thread Thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    for (int i = 0; i < 5; i++) {
                        System.out.println(Thread.currentThread().getName() + " --> num = " + num++);
                        if (num == 7) {
                            System.out.println("线程" + Thread.currentThread().getName() + "执行notify()");
                            try { obj.notify(); } catch (Exception e) {}
                            System.out.println("但并没有立即放弃锁，因为当前线程的任务还没执行完");
                        }
                    } // for
                    System.out.println("线程" + Thread.currentThread().getName() + "任务执行完毕");
                }
            } // run
        });
        
        Thread0.setName("A");
        Thread1.setName("B");
        Thread0.start();
        Thread1.start();
    }
}
