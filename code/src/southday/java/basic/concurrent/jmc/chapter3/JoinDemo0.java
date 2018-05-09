package southday.java.basic.concurrent.jmc.chapter3;

/* [join()]:
 * 当线程A执行到线程B的join()方法时，线程A等待，直到线程B结束
 *     A  |
 *        |
 *          B.join()
 * wait
 *          B run over
 *     A  |
 *        |
 * 当线程A执行了B.join()后，并且在B结束前被中断，那么线程A会报错，
 * 如下：
 * 线程Thread0执行了Thread1.start()，然后执行了Thread1.join()，
 * 而这时,主线程main执行了Thread0.interrupt();
 * 这样就导致了Thread0线程报错，而Thread1并未停止....
 * 
 * 那么：
 * join(long) 和 sleep(long)有什么区别吗？
 * 根据上面的解说，join(2000)可以理解为，只等2s
 * 此外,join和sleep还是其他区别的，请看---> JoinSleepDemo 系列 
 */

public class JoinDemo0 {
    public static void main(String[] args) {
        Thread Thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread Thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {}
                    }
                });
                Thread1.start();
                try {
                    System.out.println("当前时间: " + System.currentTimeMillis());
//                    Thread1.join();
                    Thread1.join(2000); // 只等Thread1线程执行2秒，然后Thread0就要执行自己的任务
                } catch (InterruptedException e) {
                    System.out.println("Thread0 catch");
                    e.printStackTrace();
                }
                System.out.println("当前时间: " + System.currentTimeMillis());
                System.out.println("我已经等了 Thread1 2s，现在我要执行为我的任务了...");
            }
        });
        
        Thread0.start();
        // 先让Thread1执行2s，然后再执行Thread0.interrupt()看看还会不会报错
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        Thread0.interrupt();
        // 发现没有报错，但Thread1依旧在运行
    }
}
