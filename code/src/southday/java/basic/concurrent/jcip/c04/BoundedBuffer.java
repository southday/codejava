package southday.java.basic.concurrent.jcip.c04;

/**
 * 程序清单 14-6 使用条件队列实现的有界缓存
 * @author southday
 * @date 2018年4月30日
 * @param <V>
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

    protected BoundedBuffer(int capacity) {
        super(capacity);
        // TODO Auto-generated constructor stub
    }

    // 阻塞并直到：not-full
    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            /* Object.wait会自动释放锁，并请求操作系统挂起当前线程，从而使其他线程能够获得这个锁并修改对象的状态，
             * 当被挂起的线程醒来时，它将在返回之前重新获取所
             */
            wait();
        }
        doPut(v);
        notifyAll();
    }
    
    // 阻塞并直到：not-empty
    /* 当调用某个特定条件谓词的wait时，调用者必须已经持有与条件队列相关的锁，
     * 并且这个锁必须保护着构成条件谓词的状态变量
     */
    public synchronized V take() throws InterruptedException {
        /* 为什么这里要用while()而不是用if()?
         * 1.在发出通知的线程调用notifyAll时，条件谓词可能已经变成真，但在重新获取锁时可能会再次变为假（这段时间内被其他线程修改了状态）
         * 2.或者，条件谓词从调用wait起根本就没有变成真。“一个条件队列与多个条件谓词相关”，你并不知道另一个线程为什么调用notify或notifyAll
         * 3.基于这些原因，每当线程从wait中唤醒时，都必须再次测试条件谓词，即使用while()而不是用if()
         */
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        /* 由于多个线程可以基于不同的条件谓词在同一个条件队列上等待， 因此如果使用notify而不是notifyAll，那么将是一中危险的操作，
         * 因为单一的通知很容易导致类似信号丢失的问题
         * 
         * 只有同时满足以下两个条件时，才能用单一的notify而不是notifyAll
         * 1.所有等待线程的类型都相同。只有一个条件谓词与条件队列相关，并且每个线程在从wait返回后将执行相同的操作
         * 2.单进单出。在条件变量上的每次通知，最多只能唤醒一个线程来执行
         */
        notifyAll();
        return v;
    }
    
    // 基于条件通知的put，这是一中优化措施
    // 仅当缓存从空变为非空，或者从满变为非满时，才需要释放一个线程
    public synchronized void put2(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        boolean wasEmpty = isEmpty(); // 在put该元素前，缓存为空，就需要释放一个take线程
        doPut(v);
        if (wasEmpty) {
            notifyAll();
        }
    }
}

