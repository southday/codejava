package southday.java.basic.concurrent.jmc.c01;

/*
 * 触发了隐藏在源码中的锁，使用suspend(),resume()仍然会导致类死锁问题
 * 
 * 运行下面这个程序，可以发现：到最后没有输出 main end
 * 原因：println()方法的源码
 *     public void println(String x) {
 *        synchronized (this) {
 *            print(x);
 *            nextLine();
 *        }
 *    }
 * 问题就在于 println()方法中有一个同步代码块，
 * 当Thread1线程执行进入到println()方法中执行时被suspend了，该同步代码块的锁还是在Thread1手中
 * 这时，主线程main想要执行println()方法，却没有锁，所以无法输出"main end"
 * 
 * [此外，使用suspend()方法与stop()一样，同样会造成数据不同步的问题，导致输出的数据对不上号]
 */

public class SuspendResumePrintlnLock {
    public static void main(String[] args) {
        try {
            Thread Thread = new Thread() {
                private long i = 0;
                
                @Override
                public void run() {
                    while (true) {
                        i++;
                        System.out.println(i);
                    }
                } // run
            };
            Thread.start();
            Thread.sleep(1000);
            Thread.suspend();
            System.out.println("main end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
