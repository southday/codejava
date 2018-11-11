package southday.java.basic.concurrent.jcip.c04;

/**
 * 程序清单 14-2 有界缓存实现的基类
 * @author southday
 * @date 2018年4月30日
 * @param <V>
 */
public abstract class BaseBoundedBuffer<V> {
    private final V[] buf;
    private int tail;
    private int head;
    private int count;
    
    protected BaseBoundedBuffer(int capacity) {
        this.buf = (V[]) new Object[capacity];
    }
    
    protected synchronized final void doPut(V v) {
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0;
        }
        ++count;
    }
    
    protected synchronized final V doTake() {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0;
        }
        --count;
        return v;
    }
    
    public synchronized final boolean isFull() {
        return count == buf.length;
    }
    
    public synchronized final boolean isEmpty() {
        return count == 0;
    }
}
