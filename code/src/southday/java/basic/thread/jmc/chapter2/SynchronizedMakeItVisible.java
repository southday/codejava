package southday.java.basic.thread.jmc.chapter2;

/* 本例主要讲解：由synchronized关键字带来的可见性
 * [synchronized]:
 * 1) synchronized可以保证同一时刻，只有一个线程可以执行某一个方法或一个代码块;
 * 2) synchronized包含了：互斥性和可见性
 * 3) 不仅可以解决一个线程看到对象处于不一致的状态，还可以保证进入同步方法或同步
 * 代码块的每个线程，都看到由同一个锁保护之前的所有修改.... [这里我不太理解]
 * 
 * 我自己的理解，还需要查查资料来更深入的解释：
 * 本例中: 
 *     while (isContinueRun == true) {
 *         synchronized (obj) {}
 *     }
 * 加上了 synchronized (obj) {}，让isContinueRun这个变量对于线程Thread0变得可见，
 * 所以可以停止循环，若不加这句，则即使Thread1修改了isContinueRun的值，Thread0还是
 * 看不到，总感觉这句话的功能与volatile类似        
 */

class SynRunClass {
    boolean isContinueRun = true;
    Object obj = new Object();
    
    public void runMethod() {
        while (isContinueRun == true) {
            synchronized (obj) {} // 加入这句，让程序可以停止！
        }
        System.out.println("停下来！");
    }
    
    public void stopMethod() {
        this.isContinueRun = false;
    }
}

public class SynchronizedMakeItVisible {
    public static void main(String[] args) {
        SynRunClass src = new SynRunClass();
        Thread Thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                src.runMethod();
            }
        });
        Thread Thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                src.stopMethod();
            }
        });
        Thread0.start();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        Thread1.start();
        System.out.println("已经发出停止命令！");
    }
}
