package southday.java.basic.thread.jmc.chapter2;

/* 本例旨在表明：volatile 不具备原子性
 * 
 * []:分析在下面
 */

class NoAtomThread extends Thread {
    public volatile static int count = 0;
    
    // 当然，这里你使用synchronized关键字就可以得到count最终值为1000,但这就没有使用volatile的必要了
    public static void addCount() {
        for (int i = 0; i < 100; i++) {
            count++;
        }
        System.out.println("count = " + count);
        /* 如果 volatile 关键字使对 count 的操作具备了原子性
         * 那么按理来说，应该输出10次count值，每次都是以100的差值递增，如：
         * count = 100
         * count = 200
         * ...
         * count = 1000
         * 
         * 然而，事实并不是这样，这是因为count++;这个操作并不具有原子性
         * count++; 
         * 1) 从内存中取出count 的值
         * 2) 计算 count = count + 1;
         * 3) 将新的count值写入内存中
         * 
         * 举个例子：线程A 线程B 都要执行count++;
         * 1) 线程A获取了count = 5的值，并且计算了count = count + 1，
         * 而当线程A还没来得及将新的count = 6值更新入内存时，线程B就已经从
         * 内存中获得了count的值，而这个值是5
         * 2) 然后线程B再次计算count = count + 1; 得到的count = 6
         * 3) 这时候，线程A 线程B中的count值都是6，所以刷新到内存中的count = 6,
         * 而不是我们以为的值7
         */
    }
    
    @Override
    public void run() {
        addCount();
    }
}

public class VolatileNoAtom {
    public static void main(String[] args) {
        NoAtomThread[] nats = new NoAtomThread[10];
        for (int i = 0; i < 10; i++) {
            nats[i] = new NoAtomThread();
        }
        for (int i = 0; i < 10; i++) {
            nats[i].start();
        }
    }
}
