package southday.java.basic.thread.jcip.chapter3;

/**
 * 程序清单 3-1 在没有同步的情况下共享变量（不要这么做）
 * @author southday
 * @date 2018年4月14日
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;
    
    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }
    
    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}

/* 1. 我们所预期的情况是输出：42
 * 2. 然而可能存在另外两种情况：
 *   1) NoVisibility 可能会持续循环下去，因为读线程可能永远看不到ready的值
 *   2) NoVisibility 可能会输出0，因为读线程可能看到了ready的值，但却没有看到number的值，这种现象被称为“重排序”
 *
 */