package southday.java.basic.thread.jmc.chapter1;

/*
 * 调用interrupt()方法来停止线程，但interrupt()方法的使用效果并不像for + break语句那样，
 * 马上就停止循环。调用interrupt()方法仅仅是在当前线程中打了一个停止的标记，并不是真正的停止线程。
 * 
 * 对于下面的例子：
 * 开启一个线程，让其输出50w个数，而在这个过程中会被interrupt()，
 * 按理来说，可能是输出50w个数中的其中一部分就被interrupt了，然而却完整地输出了0~499999
 * [也就是说interrupt()方法并没有立即阻断mt3线程]
 * 
 * 如何判断线程是否停止？ ----> 请看 ThreadDemo30.java
 */

class MyThread3 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 500000; i++) {
            System.out.println("i = " + i);
//            try { Thread.sleep(100); } catch (Exception e) {}
        }
    }
}

public class ThreadDemo3 {
    public static void main(String[] args) {
        try {
            MyThread3 mt3 = new MyThread3();
            mt3.start();
            Thread.sleep(2000);
            mt3.interrupt();
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
}
