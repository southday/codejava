package southday.java.basic.concurrent.jmc.c03;

/* 本例主要说明：notify()方法只随机唤醒等待队列中的一个线程
 * 方法：让多个线程等待，然后一个线程执行notify()。观察等待队列中线程被唤醒的状态
 * 
 * 对于两个线程A B处于等待唤醒状态，线程C只执行了一次notify()，那么只有A B中的一个被唤醒，
 * 另一个依旧处于等待唤醒状态，即使被唤醒的线程执行完毕后释放锁，等待状态的线程也无法执行，
 * 因为它现在最需要的是被notify()，而不是锁
 */

public class NotifyOnlyCallOneThread {    
    static Object obj = new Object();
    
    public static void main(String[] args) {
        try {
            Thread Thread0 = new Thread(new Runnable() {
                @Override
                public void run() {
                    NotifyOnlyCallOneThread.waitThread();
                }
            });
            Thread Thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    NotifyOnlyCallOneThread.waitThread();
                }
            });
            Thread0.setName("A");
            Thread1.setName("B");
            Thread0.start();
            Thread1.start();
            
            Thread.sleep(2000);
            Thread Thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (obj) {
                        obj.notify();
//                        obj.notify(); // 要想唤醒两个线程，就多notify()一次
                        System.out.println("线程" + Thread.currentThread().getName() + "执行了一次notify()");
                    }
                }
            });
            Thread2.setName("C");
            Thread2.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void waitThread() {
        synchronized (obj) {
            System.out.println("线程" + Thread.currentThread().getName() + " wait()");
            try { obj.wait(); } catch (InterruptedException e) {}
            System.out.println("线程" + Thread.currentThread().getName() + " 被唤醒");
        }
    }
}
