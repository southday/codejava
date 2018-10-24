package southday.java.basic.concurrent.jmc.c01;

/* 有了如何判断线程是否中断的方法，那我们就可以实现：
 * 当该线程中断后，停止运行该线程中的run()方法。
 * 
 * 用 [异常法] 停止线程，感觉不太好！
 */

class MyThread31 extends Thread {
    @Override
    public void run() {
        super.run();
        try {
            for (int i = 0; i < 500000; i++) {
                if (Thread.interrupted()) {
                    System.out.println("线程已经是停止状态，但并没有真正的停止!");
//                    break;
                    throw new InterruptedException(); // 抛出异常就直接停止了该线程
                }
                System.out.println("i = " + i);
            }
            System.out.println("statements under for(){}");
        } catch (InterruptedException e) {
            System.out.println("进入MyThread31.java类中的catch！");
            e.printStackTrace();
        }
        // 虽然上面的break跳出了for()循环，但是ThreadDemo30中已说过，interrupt()方法并
        // 没有真正中断线程，所以for(){}下面还有语句的话，还是会继续执行下面的语句
    }
}

public class ThreadDemo31 {
    public static void main(String[] args) {
        try {
            MyThread31 mt31 = new MyThread31();
            mt31.start();
            Thread.sleep(1000);
            mt31.interrupt();
        } catch (InterruptedException e) {
            System.out.println("main catch ");
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
