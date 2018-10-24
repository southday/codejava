package southday.java.basic.concurrent.jmc.c03;

/* 本例主要说明：join(long) 方法会使释放线程对象锁，
 * 因为其内部是由wait(0)实现的，而一旦wait()就会自动释放锁
 * 比如：
 * 线程A持有一把锁，然后它执行了 ThreadB.join()，那么线程A持有的锁就会自动释放，
 * 看下面的例子，ThreadA释放锁后，ThreadC就可以执行ThreadB.show()了
 */

class ThreadAA extends Thread {
    private ThreadBB ThreadB;
    
    public ThreadAA(ThreadBB ThreadB) {
        this.ThreadB = ThreadB;
    }
    
    @Override
    public void run() {
        try {
            synchronized (ThreadB) {
                System.out.println(Thread.currentThread().getName() + " run");
                ThreadB.start();
                ThreadB.join(5000);
                System.out.println(Thread.currentThread().getName() + " run over");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadBB extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " run");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        System.out.println(Thread.currentThread().getName() + " run over");
    }
    
    public synchronized void show() {
        System.out.println(Thread.currentThread().getName() + " process ThreadBB's method <show>");
    }
}

class ThreadCC extends Thread {
    private ThreadBB ThreadB;
    
    public ThreadCC(ThreadBB ThreadB) {
        this.ThreadB = ThreadB;
    }
    
    @Override
    public void run() {
        ThreadB.show();
    }
}

public class JoinSleepDemo1 {
    public static void main(String[] args) {
        ThreadBB ThreadB = new ThreadBB();
        ThreadAA ThreadA = new ThreadAA(ThreadB);
        ThreadCC ThreadC = new ThreadCC(ThreadB);
        
        ThreadA.setName("A");
        ThreadB.setName("B");
        ThreadC.setName("C");
        
        ThreadA.start();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        ThreadC.start();
    }
}
