package southday.java.thinkinginjava.c21.s04;

import java.util.concurrent.TimeUnit;

class NeedsCleanup {
    private final int id;

    public NeedsCleanup(int ident) {
        id = ident;
        System.out.println("NeedsCleanup " + id);
    }

    public void cleanup() {
        System.out.println("Cleaning up " + id);
    }
}

class Blocked3 implements Runnable {
    private volatile double d = 0.0;

    public void run() {
        try {
            while (!Thread.interrupted()) {
                // point 1
                NeedsCleanup n1 = new NeedsCleanup(1);
                // 惯用法：在创建完资源后紧跟 try-finally 来确保资源的清除
                try {
                    System.out.println("Sleeping");
                    TimeUnit.SECONDS.sleep(1);
                    // point 2
                    NeedsCleanup n2 = new NeedsCleanup(2);
                    // 惯用法：在创建完资源后紧跟 try-finally 来确保资源的清除
                    try {
                        System.out.println("Calculating");
                        for (int i = 1; i < 2500000; i++)
                            d = d + (Math.PI + Math.E) / d;
                        System.out.println("Finished time-consuming operation");
                    } finally {
                        n2.cleanup();
                    }
                } finally {
                    n1.cleanup();
                }
            }
            System.out.println("Exiting via while() test");
        } catch (InterruptedException e) {
            System.out.println("Exiting via InterruptedException");
        }
    }
}

/**
 * P702
 * @author southday
 * @date 2019/4/7
 */
public class InterruptingIdiom {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Blocked3());
        t.start();
        // 控制sleep()时间来测试在不同点 point1, point2 的中断效果
//        TimeUnit.MILLISECONDS.sleep(100); // point1 - point2 第一次循环
//        TimeUnit.MILLISECONDS.sleep(1022); // point2 之后
        TimeUnit.MILLISECONDS.sleep(1058); // point1 - point2 第二次循环
        t.interrupt();
    }
}
