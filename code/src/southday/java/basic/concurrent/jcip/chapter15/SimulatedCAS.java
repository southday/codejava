package southday.java.basic.concurrent.jcip.chapter15;

/**
 * 程序清单 15-1 模拟CAS(Compare-and-Swap)操作<p>
 * <code>SimulatedCAS</code>说明了CAS语义，而不是实现或性能
 * @author southday
 * @date 2018年5月1日
 */
public class SimulatedCAS {
    private int value;
    
    public synchronized int get() {
        return value;
    }
    
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }
    
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return (expectedValue == compareAndSwap(expectedValue, newValue));
    }
    
    /* CAS的典型使用模式：
     *   V：需要读写的内存位置V
     *   A：进行比较的值A
     *   B：拟写入的新值B
     * 首先从V中读取值A，并根据A计算新值B，然后再通过CAS以原子方式将V中的值由A变成B，
     * 只要在这期间没有任何线程将V的值修改为其他值，
     * 由于CAS能检测到来自其他线程的干扰，因此即使不使用锁也能够实现原子的读-改-写操作序列
     * 
     * CAS的主要缺点是：它将使调用者处理竞争问题（通过重试、回退、放弃）
     * 而在锁中能自动处理竞争问题（线程在获得锁之前将一直阻塞）
     */
}
