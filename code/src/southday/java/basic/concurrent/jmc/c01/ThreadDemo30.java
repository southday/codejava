package southday.java.basic.concurrent.jmc.c01;

/* 如何判断线程是否停止呢？
 * 在这里有两种方法：
 *  1）public static boolean interrupted() {}
 *  2）public boolean isInterrupted() {}
 *  
 * 这两个方法是有区别的：
 *    对于(1)，可以看出它是个静态方法，所以调用时不需要对象，
 * 它表示的是：判断”当前线程“是否停止，而这个”当前线程“则是指执行这句代码的线程。
 *    对于(2)， 是一个非静态方法，很明确地说明：
 * 它是用来判断”调用这个isInterrupted()方法的对象（线程）“是否停止。
 * 
 * 再具体一点，查看Thread类中的源码，可以发现：
 * 
 * public static boolean interrupted() {
 *     return currentThread().isInterrupted(true);
 * }
 * 
 * public boolean isInterrupted() {
 *     return isInterrupted(false);
 * }
 * 
 * 而 isInterrupted(boolean ClearInterrupted)是一个native 方法，
 * 具体怎么实现，我暂时没有深入研究。 --- 2016.3.13
 * 
 * 最后面还有解说----> please look down
 * 有了[如何判断线程是否停止]的方法，那即使程序没有自动让该线程停止，我们也可以做相应的操作
 * 请看 --> ThreadDemo31.java
 */

public class ThreadDemo30 {
    public static void main(String[] args) {
//        test1();
        test2();
    }
    
    public static void test1() {
        Thread.currentThread().interrupt();
        System.out.println("是否停止 1 --> " + Thread.interrupted());
        System.out.println("是否停止 2 --> " + Thread.interrupted());
        System.out.println("end");
    }
        
    public static void test2() {
        try {
            MyThread3 mt3 = new MyThread3();
            mt3.start();
            Thread.sleep(1000);
            mt3.interrupt();
            // 这尼玛输出的太快，根本看不见
            System.out.println("****************************************");
            System.out.println("是否停止 1 --> " + mt3.isInterrupted());
            System.out.println("****************************************");
            System.out.println("是否停止 2 --> " + mt3.isInterrupted());
            System.out.println("****************************************");
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
/* 对于test1()，输出的是
 *  是否停止 1 --> true
            是否停止 2 --> false
    end
 * 发现第一次判断时，得到的结果时主线程main是处于结束标志了，
 * 但第二次再判断是，却得到主线程main的标志不是结束标志。
 * 原因：[官方帮助文档]
 * interrupted()方法，测试当前线程是否已经中断。线程中断状态由该方法清楚。
 * 换句话说，如果连续调用两次该方法，第二次调用将会返回false，因为第一次调用已清除了中断状态。
 * 除非：在第一次调用已经清除中断状态后，且在第二次调用检验完中断状态前，当前线程再次中断的情况除外。
 * 
 * 对于test2()，输出的时（这里为只截取重要的部分）
 *  xxxxxx
 *  是否停止 1 --> true
 *  xxxxxx
 *  是否停止 2 --> true 
 *  xxxxxx
 * 与interrupted()不同的是，isInterrputed()不会清除中断状态！
 */
