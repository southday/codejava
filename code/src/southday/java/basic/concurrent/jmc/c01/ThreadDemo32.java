package southday.java.basic.concurrent.jmc.c01;

/* [在沉睡中停止线程]
 * 
 * 1）先sleep()再interrupt()
 *  run begin
    在沉睡中被停止！进入catch!
    java.lang.InterruptedException: sleep interrupted
    at java.lang.Thread.sleep(Native Method)
    at Thread.coco.MyThread32.sleepInterrupt(ThreadDemo32.java:22)
    at Thread.coco.MyThread32.run(ThreadDemo32.java:15)
 * 
 * 2）先interrupt()再sleep()
 *  xxxx
 *  end
 *  xxxx
 *  run begin
 *  先停止，再sleep！进入catch!
 *  java.lang.InterruptedException: sleep interrupted
    at java.lang.Thread.sleep(Native Method)
    at Thread.coco.MyThread32.interruptSleep(ThreadDemo32.java:48)
    at Thread.coco.MyThread32.run(ThreadDemo32.java:26)
    
    2)这个错误为是运行了好几次才和教材中的一样的
 *
 * (1)(2)说明了不管是先sleep再interrupt，还是先interrupt再sleep，都会报出异常：sleep interrupted
 */

class MyThread32 extends Thread {
    @Override
    public void run() {
//        sleepInterrupt();
        interruptSleep();
    }
    
    // 1) 先sleep 再interrupt
    public static void sleepInterrupt() {
        try {
            System.out.println("run begin");
            Thread.sleep(2000000);
            System.out.println("run over");
        } catch (InterruptedException e) {
            System.out.println("在沉睡中被停止！进入catch!");
            e.printStackTrace();
        }
    }
    
    // 2) 先interrupt 再sleep
    public static void interruptSleep() {
        try {
            for (int i = 0; i < 100000; i++) {
                System.out.println("i = " + i);
            }
            System.out.println("run begin");
            Thread.sleep(2000000);
            System.out.println("run over");
        } catch (InterruptedException e) {
            System.out.println("先停止，再sleep！进入catch!");
            e.printStackTrace();
        }
    }
}

public class ThreadDemo32 {
    public static void main(String[] args) {
        testIS();
//        testSI();
    }
    
    public static void testSI() {
        try {
            MyThread32 mt32 = new MyThread32();
            mt32.start();
            Thread.sleep(1000);
            mt32.interrupt();
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
    
    public static void testIS() {
        MyThread32 mt32 = new MyThread32();
        mt32.start();
        mt32.interrupt();
        System.out.println("end");
    }
}
