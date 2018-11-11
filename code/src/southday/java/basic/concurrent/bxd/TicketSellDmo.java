package southday.java.basic.concurrent.bxd;
/*
 * 需求：简单的卖票程序：多个窗口同时卖票
 * 通过分析发现，打印出了0，-1，-2等错票，多线程的运行出现了安全问题
 * 原因是：多个线程在操作同一个共享数据，而操作的过程不同步，即各线程会受到其他线程的影响。
 * 
 * 解决方法：一个线程在执行的时候（访问共享数据时），禁止其他线程对该共享数据的访问。线程互斥性。
 * 同步代码块： 
     * synchronized (对象) {
     *         需要被同步的代码
     * }
 * 对象如同锁，持有锁的线程可以在同步进执行。没有锁的线程，即使获得了CPU执行权也进不去执行。
 * 
 * 同步的前提：
 * （1）必须要有两个或者两个以上的线程才需要同步；
 * （2）必须是多个线程使用同一个锁，这才要同步；
 * （3）必须保证同步中只有一个线程在运行。
 * 
 * 好处：解决了多线程的安全问题；
 * 弊端：多个线程都需要判断锁，较为消耗资源；
 */

class Tickets implements Runnable {
    private int ticket = 1000;
    Object obj = new Object();
    public void run() {
        while (true) {
            synchronized (obj) {
                if(ticket>0) {
                    try {Thread.sleep(10);} catch(InterruptedException e) {}
                    System.out.println(Thread.currentThread().getName()+"ticket = "+ticket--);
                }
            }
        }
    }
}
public class TicketSellDmo {
    public static void main(String[] args) {
        Tickets tic = new Tickets();
        
        Thread t1 = new Thread(tic,"t1--");
        Thread t2 = new Thread(tic,"t2-----");
        Thread t3 = new Thread(tic,"t3----------");
        
        t2.start();
        t3.start();
        t1.start();
    }
}
