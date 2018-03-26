package southday.java.basic.thread.jmc.chapter1;

/* 本例主要讲述：守护线程：daemon Thread
 * 当线程中不再存在非守护线程时，守护线程自动销毁;
 * 典型的守护线程——>垃圾回收线程 GC 
 */
public class DaemonThreadDemo {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            private int i = 0;
            
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println(i++ + "守护线程Thread工作ing...");
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true); // 将线程Thread设置成为守护线程
        // 当非守护线程运行结束时，守护线程自动销毁
        thread.start();
        try { Thread.sleep(5000); } catch (InterruptedException e) {}
        System.out.println("我是main线程(主线程)，我现在运行完了！");
        System.out.println("我是Thread，我是守护线程，现在程序中唯一的非守护线程main运行完了"
                + "，我也要自动销毁了");
    }
}
