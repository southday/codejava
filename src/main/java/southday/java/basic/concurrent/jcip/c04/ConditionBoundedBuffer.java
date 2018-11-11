package southday.java.basic.concurrent.jcip.c04;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 程序清单 14-11 使用显示条件变量的有界缓存<p>
 * 如果需要一些高级功能，例如使用公平的队列操作或者在每个锁上对应多个等待线程集，那么应该优先使用<code>Condition</code>
 * 而不是内置条件队列
 * @author southday
 * @date 2018年5月1日
 * @param <T>
 */
public class ConditionBoundedBuffer<T> {
    private static final int BUFFER_SIZE = 100;
    protected final Lock lock = new ReentrantLock();
    // 条件谓词：notFull (count < items.length)
    private final Condition notFull = lock.newCondition();
    // 条件谓词：notEmpty (count > 0)
    private final Condition notEmpty = lock.newCondition();
    private final T[] items = (T[]) new Object[BUFFER_SIZE];
    private int tail, head, count;
    
    // 阻塞并直到：notFull
    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = x;
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
    
    // 阻塞并指导：notEmpty
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if (++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
