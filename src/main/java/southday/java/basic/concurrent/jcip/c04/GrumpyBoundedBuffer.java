package southday.java.basic.concurrent.jcip.c04;

/**
 * 程序清单 14-3 当不满足前提条件时，有界缓存不会执行相应的操作，而是抛出异常<p>
 * 1.异常应该用于发生异常条件的情况中，“缓存已满”并不是有界缓存的一个异常条件<br>
 * 2.实现缓存时得到的简化（使调用者管理状态依赖性）并不能抵消在使用时的复杂性<br>
 * 3.如果将状态依赖性交给调用者管理，那么将导致一些功能无法实现，例如维持FIFO顺序，由于迫使调用者重试，
 * 因此失去了“谁先到达”的信息
 * @author southday
 * @date 2018年4月30日
 * @param <V>
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    protected GrumpyBoundedBuffer(int capacity) {
        super(capacity);
        // TODO Auto-generated constructor stub
    }
    
    public synchronized void put(V v) throws BufferFullException {
        if (isFull()) {
            throw new BufferFullException();
        }
        doPut(v);
    }
    
    public synchronized V take() throws BufferEmptyException {
        if (isEmpty()) {
            throw new BufferEmptyException();
        }
        return doTake();
    }
}

/* 实现缓存时得到的简化（使调用者管理状态依赖性）并不能抵消在使用时的复杂性，
 * 因为现在调用者必须做好捕获异常的准备，并且在每次缓存操作时都需要重试，如：
 * while (true) {
 *     try {
 *         V item = buffer.take();
 *         // 对于item 执行一些操作
 *         break;
 *     } catch (BufferEmptyException e) {
 *         Thread.sleep(SLEEP_GRANULARITY);
 *     }
 * }
 */