package southday.java.thinkinginjava.c21.s02;

import java.util.concurrent.TimeUnit;

class ADemon implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Starting ADaemon");
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("This should always run?");
        }
    }
}

/**
 * P664
 * 后台线程在不执行 finally 子句的情况下就会终止其 run() 方法
 * @author southday
 * @date 2019/4/6
 */
public class DaemonsDontRunFinally {
    public static void main(String[] args) {
        Thread t = new Thread(new ADemon());
        t.setDaemon(true);
        t.start();
    }
}
