package southday.java.basic.thread.jcip.chapter14;

/**
 * 程序清单 14-5 使用简单阻塞实现的有界缓存
 * @author southday
 * @date 2018年4月30日
 * @param <V>
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    public static final long SLEEP_GRANULARITY = 3000;

    protected SleepyBoundedBuffer(int capacity) {
        super(capacity);
        // TODO Auto-generated constructor stub
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            // 休眠的间隔越小，响应性越高，但消耗的CPU资源也越高
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }
    
    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }
}
