package southday.java.basic.thread.jmc.chapter3;

/* 本例旨在说明： sleep(long)方法执行时，不释放线程的对象锁
 * 其实从直观上就很容易确认，因为sleep(long)是一个静态方法，何来线程对象可言，
 * 它是 只要运行了本方法的线程，一律通吃，都给我取睡觉，
 * 不过sleep(long)是个native方法，为也挺好奇它是怎么知道是谁运行了它的，难道用了currentThread()？
 * 
 * 发现: 线程C必须等到线程B执行完，才能执行ThreadB.show()，因为Thread.sleep(2000)并没有让线程B释放对象锁
 */

class ThreadA extends Thread {
    private ThreadB ThreadB;
    
    public ThreadA(ThreadB ThreadB) {
        this.ThreadB = ThreadB;
    }
    
    @Override
    public void run() {
        try {
            synchronized (ThreadB) {
                ThreadB.start();
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadB extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " run");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        System.out.println(Thread.currentThread().getName() + " run over");
    }
    
    public synchronized void show() {
        System.out.println(Thread.currentThread().getName() + " process ThreadB's method <show>");
    }
}

class ThreadC extends Thread {
    private ThreadB ThreadB;
    
    public ThreadC(ThreadB ThreadB) {
        this.ThreadB = ThreadB;
    }
    
    @Override
    public void run() {
        ThreadB.show();
    }
}

public class JoinSleepDemo0 {
    public static void main(String[] args) {
        ThreadB ThreadB = new ThreadB();
        ThreadA ThreadA = new ThreadA(ThreadB);
        ThreadC ThreadC = new ThreadC(ThreadB);
        
        ThreadA.setName("A");
        ThreadB.setName("B");
        ThreadC.setName("C");
        
        ThreadA.start();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        ThreadC.start();
    }
}
