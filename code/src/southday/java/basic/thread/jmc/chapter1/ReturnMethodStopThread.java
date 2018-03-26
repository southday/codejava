package southday.java.basic.thread.jmc.chapter1;

/*
 * 使用return的方法来停止线程
 * 《Java多线程核心编程技术》中作者更推荐使用抛出异常的方式来停止线程，
 * 因为这样可以将异常抛给调用者，是线程停止的事件得以传播
 */

class MyThreadReturnMethod extends Thread {
    @Override
    public void run() {
        while (true) {
            if (this.isInterrupted()) {
                System.out.println("停止了!");
                return;
            }
            System.out.println("timer = " + System.currentTimeMillis());
        }
    }
}

public class ReturnMethodStopThread {
    public static void main(String[] args) throws InterruptedException {
        MyThreadReturnMethod Thread = new MyThreadReturnMethod();
        Thread.start();
        Thread.sleep(2000);
        Thread.interrupt();
    }
}
