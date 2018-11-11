package southday.java.basic.concurrent.jmc.c02;

/* [volatile] 与 [synchronized]
 * 1) volatile 是线程同步的轻量级实现，所以通常情况下volatile性能要比synchronized要好
 * 2) volatile 只能修饰变量，而synchronized可以修饰 方法、代码块，而随着JDK的新版本发布，
 * synchronized的执行效率得到很大提升
 * 3) 多线程访问volatile不会发生阻塞，而访问synchronized可能会发生阻塞
 * 4) volatile 能保证数据的可见性，但不保证操作的原子性，而synchronized既保证原子性，又保证间接可见性，
 * 因为它会将私有内存和公共内存中的数据同步
 * 5) volatile 解决的是变量在多个线程间的可见性，而synchronized关键字解决的是多个线程之间访问资源的同步性
 * 
 * RunThread 中的变量 isRunning 定义为： boolean isRunning = true;
 * 会导致无限循环，即使后来的主线程执行了Thread.setRunning(false);
 *    这里涉及到Java的内存模型，线程都有一个私有堆栈，为了提高线程执行效率，线程一直都是在
 * 自己的私有堆栈中获取经常使用的变量isRunning，而其值恰好就是true，虽然setRunning(false)被
 * 执行，更新的却是公共堆栈中的isRunning，将其值变为false，所以一直处于死循环状态
 * 
 * RunThread 中的变量 isRunning 定义为： volatile boolean isRunning = true;
 * 线程的死循环得以结束，因为：
 *    volatile 关键字 的主要作用是：当线程访问isRunning这个变量时，强制性从公共堆栈中取，这样一来，
 * 取到的值就是被修改后的false，所以死循环结束
 */

class RunThread extends Thread { 
//    boolean isRunning = true; // 死循环
    volatile boolean isRunning = true; // 死循环结束
    
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    @Override
    public void run() {
        System.out.println("in running");
        while (isRunning == true) {}
        System.out.println("Thread was stopped");
    }
}

public class VolatileWeCanSee {
    public static void main(String[] args) {
        RunThread Thread = new RunThread();
        Thread.start();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        Thread.setRunning(false);
        System.out.println("isRunning 已经赋值为 false");
    }
}
