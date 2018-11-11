package southday.java.basic.concurrent.jcip.c12;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 程序清单 12-5 测试<code>BoundedBuffer</code>的生产者-消费者程序<p>
 * 启动N个生产者和N个消费者来操纵队列，当元素近处队列时，每个线程都会更新对这些元素计算得到的校验和，
 * 每个线程都拥有一个校验和，并在测试结束后将他们合并起来，从而在测试缓存时不会引入过多的同步或竞争
 * @author southday
 * @date 2018年4月27日
 */
public class PutTakeTest {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final CyclicBarrier barrier;
    private final BoundedBuffer<Integer> bb;
    private final int nTrials, nPairs;
    
    public static void main(String[] args) {
        new PutTakeTest(10, 10, 100000).test(); // 实例参数
        pool.shutdown();
    }

    PutTakeTest(int capacity, int npairs, int ntrials) {
        this.bb = new BoundedBuffer<Integer>(capacity);
        this.nTrials = ntrials;
        this.nPairs = npairs;
        this.barrier = new CyclicBarrier(npairs*2+1);
    }
    
    void test() {
        try {
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            /* 栅栏 barrier
             * 通常第一个线程比其他线程具有“领先权势”，因此我们可能无法获得预想中的交替执行；
             * 使用barrier.await()，等待所有线程都到达该执行点时才放行，以解决该问题
             */
            barrier.await(); // 等待所有的线程就绪
            barrier.await(); // 等待所有的线程执行完成
            // 这里使用Junit的assertEquals()方法
            // assertEquals(putSum.get(), takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Marsaglia xorShift, 2003<p>
     * https://en.wikipedia.org/wiki/Xorshift
     * @param y
     * @return
     */
    public static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }
    
    class Producer implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                int seed = (this.hashCode() ^ (int)System.nanoTime());
                int sum = 0;
                barrier.await();
                for (int i = nTrials; i > 0; i--) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    class Consumer implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; i--) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
