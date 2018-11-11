package southday.java.basic.concurrent.jmc.c02;

/* 本例主要说明：多个线程对于判断式的异步访问，可能造成脏读现象
 * 
 * 定义一个Integer型变量N，初始值时0,我们期望N不大于1
 */

public class SynchronizedDirtyRead {
    int N = 0;
    
    public static void main(String[] args) {
        System.out.println("N 的初始值为0, 并且我们不希望N大于1");
        SynchronizedDirtyRead sdr = new SynchronizedDirtyRead();
        Thread Thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
//                sdr.addN();
                sdr.improveAddN();
            }
        });
        Thread Thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                sdr.addN();
                sdr.improveAddN();
            }
        });
        Thread1.start();
        Thread2.start();
    }
    
    public void addN() {
        if (N < 1) { // 由于对判断式的非同步进行，导致了脏读
            // 假设花2S的时间执行了其他语句
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            synchronized (this) {
                N = N + 1;
                System.out.print(Thread.currentThread().getName() + " 执行了 N = N + 1");
                System.out.println(", 现在 N = " + N);
            }
        }
    }
    
    public void improveAddN() {
        synchronized (SynchronizedDirtyRead.class) {
            if (N < 1) { // 解决办法是在判断这里也进行同步
                // 假设花2S的时间执行了其他语句
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                synchronized (this) {
                    N = N + 1;
                    System.out.print(Thread.currentThread().getName() + " 执行了 N = N + 1");
                    System.out.println(", 现在 N = " + N);
                }
            } // if
        }
    }
}
