package southday.java.basic.concurrent.jmc.c01;

/*《Java多线程编程核心技术》1.2.1的图1.8上写道：
 * 线程的启动顺序与start()执行顺序无关
 * 
 * 我有点疑惑，书上的例子是使用了一个输出语句来证明无关，
 * 即：本来应该是输出 1 2 3 4 5 6 7 8 9 10
 *   而实际输出：   1 2 3 4 5 6 7 8 10 9
 * 然后作者就说：线程的启动顺序与start()执行顺序无关
 * 
 * 想一想：如果t9已经启动了，正准备输出9时，却被刚启动的t10线程抢了执行权，然后抢先输出了10
 * 这种情况的出现，不足以证明作者的观点。
 * 
 * 而为接下来要做的实验就是：既然作者以输出语句来判断线程启动的顺序，那么为在输出语句哪里加把锁
 * 应该是与该观点的证明没有冲突的，因为线程一启动就输出啊（证明该线程启动了），然后马上释放锁，
 * 这并不影响其他线程的启动。
 * 
 * 多次start()一个线程对象，会报出：java.lang.IllegalThreadStateException异常
 */
// 这是《Java多线程编程核心技术》1.2.1中的例子
class MyThread extends Thread {
    static Object lock = new Object();
    private int i = 0;
    
    public MyThread(int i) {
        super();
        this.i = i;
    }
    
    @Override
    public void run() {
        synchronized (lock) {
            System.out.println(i);
        }
    }
}

public class ThreadDemo0 {
    public static void main(String[] args) {
        MyThread t1 = new MyThread(1);
        MyThread t2 = new MyThread(2);
        MyThread t3 = new MyThread(3);
        MyThread t4 = new MyThread(4);
        MyThread t5 = new MyThread(5);
        MyThread t6 = new MyThread(6);
        MyThread t7 = new MyThread(7);
        MyThread t8 = new MyThread(8);
        MyThread t9 = new MyThread(9);
        MyThread t10 = new MyThread(10);
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
    }
}

/* 最后经过测试，发现即使为在run()中加了锁，得到的结果还是和书中的类似
 * 但为还是存在疑惑，因为目前为还不确定t1.start()是一个原语类型的操作，
 * 如果不是，那么从start() -> run()这个过程中依旧可以被抢夺执行权，
 * 刚才看了下start()的源码，发现是带有synchronized关键字的函数，虽说时同步，但锁是this，
 * 这么一来，对于其他对象时无干扰的。
 */
