package southday.java.basic.thread.jcip.chapter11;

/**
 * 程序清单 11-8 在基于散列的<code>Map</code>中使用锁分段技术<p>
 * 1.拥有<code>N_LOCKES</code>个锁，并且每个锁保护散列桶的一个子集<br>
 * 2.大多数方法，例如<code>get</code>都只需要获得一个锁，而有些方法则需要获得所有的锁，但并不要求同时获得，例如<code>clear</code><br>
 * 3.在<code>ConcurrentHashMap</code>的实现中使用了一个包含16个锁的数组，每个锁保护所有散列桶的1/16，其中第N个散列桶由
 * 第（N mode 16）个锁来保护。正是这项技术使得<code>ConcurrentHashMap</code>能够支持多达16个并发的写入器
 * @author southday
 * @date 2018年4月26日
 */
public class StripeMap {
    // 同步策略：buckets[n]由locks[n%N_LOCKS]来保护
    private static final int N_LOCKS = 16;
    private final Node[] buckets;
    private final Object[] locks;
    
    private static class Node {
        // 为了书写方便，直接赋值为null
        private Node next = null;
        private Object key = null;
        private Object value = null;
    }
    
    public StripeMap(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[numBuckets];
        for (int i = 0; i < N_LOCKS; i++) {
            locks[i] = new Object();
        }
    }
    
    private final int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }
    
    public Object get(Object key) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node m = buckets[hash]; m != null; m = m.next) {
                if (m.key.equals(key)) {
                    return m.value;
                }
            }
        }
        return null;
    }
    
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            synchronized (locks[i % N_LOCKS]) {
                buckets[i] = null;
            }
        }
    }
}