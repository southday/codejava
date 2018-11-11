package southday.java.basic.concurrent.jmc.c01;

/*
 * 使用suspend()和resume()方法来暂停和恢复线程
 * 
 * 下面的程序将会输出类似的结果：
 *  子线程suspend()
    A = 1458018187114 i = 2541139066
    main sleep 5000ms
    A = 1458018192115 i = 2541139066
    子线程resume()
    子线程suspend()
    B = 1458018197115 i = 5086310520
    main sleep 5000ms
    B = 1458018202115 i = 5086310520
    
    我们可以看到，子线程在被停止后，i没有再累加，主线程睡前睡后输出的i一样，
    当子线程被恢复后，i开始累加，然后被暂停再输出一样的结果
    
    但是，使用suspend()和resume()方法会导致类如死锁的问题，这也就是
    为什么suspend()和resume()方法被废除的原因? 详细请看 --> SuspendResumeEngross.java
 */

class SuspendResumeMyThread extends Thread {
    private long i = 0;
    
    public void setI(long i) {
        this.i = i;
    }
    
    public long getI() {
        return i;
    }
    
    @Override
    public void run() {
        while (true) {
            i++;
        }
    }
}

public class SuspendResumeThread {
    public static void main(String[] args) {
        try {
            SuspendResumeMyThread srThread = new SuspendResumeMyThread();
            srThread.start();
            Thread.sleep(5000);
            
            System.out.println("子线程suspend()");
            // A part
            srThread.suspend();
            System.out.println("A = " + System.currentTimeMillis() + " i = " + srThread.getI());
            System.out.println("main sleep 5000ms");
            Thread.sleep(5000);
            System.out.println("A = " + System.currentTimeMillis() + " i = " + srThread.getI());
            
            System.out.println("子线程resume()");
            // B part
            srThread.resume();
            Thread.sleep(5000);
            System.out.println("子线程suspend()");
            srThread.suspend();
            System.out.println("B = " + System.currentTimeMillis() + " i = " + srThread.getI());
            System.out.println("main sleep 5000ms");
            Thread.sleep(5000);
            System.out.println("B = " + System.currentTimeMillis() + " i = " + srThread.getI());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
