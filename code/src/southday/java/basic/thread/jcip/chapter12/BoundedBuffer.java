package southday.java.basic.thread.jcip.chapter12;

import java.util.concurrent.Semaphore;

/**
 * 程序清单 12-1 基于信号量的有界缓存<p>
 * 使用<code>Semaphore</code>来实现。在实际情况中，如果需要一个有解缓存，应该直接使用<code>ArrayBlockingQueue</code>
 * 或者<code>LinkedBlockingQueue</code>，而不是自己编写
 * @author southday
 * @date 2018年4月27日
 */
public class BoundedBuffer<E> {
    private final Semaphore availableItems, availableSpaces;
    private final E[] items;
    private int putPosition = 0, takePosition = 0;
    
    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }
    
    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }
    
    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }
    
    public void put(E x) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(x);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = doExtract();
        availableSpaces.release();
        return item;
    }
    
    private synchronized void doInsert(E x) {
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length) ? 0 : i;
    }
    
    private synchronized E doExtract() {
        int i = takePosition;
        E x = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;
        return x;
    }
}
