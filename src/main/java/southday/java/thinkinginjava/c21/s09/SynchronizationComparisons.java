package southday.java.thinkinginjava.c21.s09;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract class Accumulator {
    public static long cycles = 50000L;
    // Number of Modifiers and Readers during each test
    private static final int N = 4;
    public static ExecutorService exec = Executors.newFixedThreadPool(N * 2);
    private static CyclicBarrier barrier = new CyclicBarrier(N*2 + 1);
    protected volatile int index = 0;
    protected volatile long value = 0;
    protected long duration = 0;
    protected String id = "error";
    protected final static int SIZE = 100000;
    protected static int[] preLoaded = new int[SIZE];
    static {
        // Load the array of random numbers
        Random rand = new Random(47);
        for (int i = 0; i < SIZE; i++)
            preLoaded[i] = rand.nextInt();
    }
    public abstract void accumulate();
    public abstract long read();

    private class Modifier implements Runnable {
        @Override
        public void run() {
            for (long i = 0; i < cycles; i++)
                accumulate();
            try {
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class Reader implements Runnable {
        private volatile long value;
        @Override
        public void run() {
            for (long i = 0; i < cycles; i++)
                value = read();
            try {
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void timedTest() {
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            exec.execute(new Modifier());
            exec.execute(new Reader());
        }
        try {
            barrier.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        duration = System.nanoTime() - start;
        System.out.printf("%-13s: %13d\n", id, duration);
    }

    public static void report(Accumulator acc1, Accumulator acc2) {
        System.out.printf("%-22s: %.2f\n", acc1.id + "/" + acc2.id,
                (double)acc1.duration / (double)acc2.duration);
    }
}

class BaseLine extends Accumulator {
    { id = "BaseLine"; }

    @Override
    public void accumulate() {
        int i = index++; // i 是线程私有的
        if (i >= SIZE) {
            i = 0;
            index = 0;
        }
        value += preLoaded[i];
    }

    @Override
    public long read() {
        return value;
    }
}

class SynchronizedTest extends Accumulator {
    { id = "synchronized"; }

    @Override
    public synchronized void accumulate() {
        value += preLoaded[index++];
        if (index >= SIZE)
            index = 0;
    }

    @Override
    public synchronized long read() {
        return value;
    }
}

class LockTest extends Accumulator {
    { id = "Lock"; }
    private Lock lock = new ReentrantLock();

    @Override
    public void accumulate() {
        lock.lock();
        try {
            value += preLoaded[index++];
            if (index >= SIZE)
                index = 0;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long read() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }
}

class AtomicTest extends Accumulator {
    { id = "Atomic"; }
    private AtomicInteger index = new AtomicInteger(0);
    private AtomicLong value = new AtomicLong(0);

    @Override
    public void accumulate() {
        // Oops! Relying on more than one Atomic at a time doesn't work.
        // But it still gives us a performance indicator
        int i = index.getAndIncrement();
        if (i >= SIZE) {
            i = 0;
            index.set(0);
        }
        value.getAndAdd(preLoaded[i]);
    }

    @Override
    public long read() {
        return value.get();
    }
}

/**
 * P751 比较各类互斥技术
 * @author southday
 * @date 2019/4/10
 */
public class SynchronizationComparisons {
    static BaseLine baseLine = new BaseLine();
    static SynchronizedTest synch = new SynchronizedTest();
    static LockTest lock = new LockTest();
    static AtomicTest atomic = new AtomicTest();
    static void test() {
        System.out.println("============================");
        System.out.printf("%-12s : %13d\n", "Cycles", Accumulator.cycles);
        baseLine.timedTest();
        synch.timedTest();
        lock.timedTest();
        atomic.timedTest();
        Accumulator.report(synch, baseLine);
        Accumulator.report(lock, baseLine);
        Accumulator.report(atomic, baseLine);
        Accumulator.report(synch, lock);
        Accumulator.report(synch, atomic);
        Accumulator.report(lock, atomic);
    }

    public static void main(String[] args) {
        int iterations = 5;
        System.out.println("Warmup");
        /* Now the initial test doesn't include the cost
         * of starting the threads for the first time.
         */
        baseLine.timedTest();
        // Produce multiple data points
        for (int i = 0; i < iterations; i++) {
            test();
            Accumulator.cycles *= 2;
        }
        Accumulator.exec.shutdown();
    }
}

/* 实际测试
使用书本原本的代码测试，BaseLine，AtomicTest 都会数组越界；

原始代码：
class BaseLine extends Accumulator {
    public void accumulate() {
        value += preLoaded[index++];
        if (index >= SIZE)
            index = 0;
    }
}

class AtomicTest extends Accumulator {
    public void accumulate() {
        int i = index.getAndIncrement();
        value.getAndAdd(preLoaded[i]);
        if (++i >= SIZE)
            index.set(0);
    }
}

发现作者没有考虑到一个情况：
N个 Modifier 对象（在N个线程中）同时对同一个 index 进行修改，
可能存在某个线程对 index++ 完成后任务被中断（判断 index 是否越界和置0的代码还没有被执行），
另外一个线程又调用了 index++，这样index 就有可能超出SIZE的大小！

所以对代码进行了修改：先判断是否越界，再获取值，通过线程私有的变量 i 来防止并发修改引起的问题
class BaseLine extends Accumulator {
    public void accumulate() {
        int i = index++; // i 是线程私有的
        if (i >= SIZE) {
            i = 0;
            index = 0;
        }
        value += preLoaded[i];
    }
}

class AtomicTest extends Accumulator {
    public void accumulate() {
        int i = index.getAndIncrement();
        if (i >= SIZE) {
            i = 0;
            index.set(0);
        }
        value.getAndAdd(preLoaded[i]);
    }
}
 */

/* 输出
Warmup
BaseLine     :      16078743
============================
Cycles       :         50000
BaseLine     :       9482560
synchronized :      38331014
Lock         :      39701815
Atomic       :      11775980
synchronized/BaseLine : 4.04
Lock/BaseLine         : 4.19
Atomic/BaseLine       : 1.24
synchronized/Lock     : 0.97
synchronized/Atomic   : 3.26
Lock/Atomic           : 3.37
============================
Cycles       :        100000
BaseLine     :      21798255
synchronized :      94747195
Lock         :      38778466
Atomic       :      14170780
synchronized/BaseLine : 4.35
Lock/BaseLine         : 1.78
Atomic/BaseLine       : 0.65
synchronized/Lock     : 2.44
synchronized/Atomic   : 6.69
Lock/Atomic           : 2.74
============================
Cycles       :        200000
BaseLine     :      43757695
synchronized :     191437725
Lock         :      68612263
Atomic       :      28046540
synchronized/BaseLine : 4.37
Lock/BaseLine         : 1.57
Atomic/BaseLine       : 0.64
synchronized/Lock     : 2.79
synchronized/Atomic   : 6.83
Lock/Atomic           : 2.45
============================
Cycles       :        400000
BaseLine     :      82810029
synchronized :     377097957
Lock         :     143663536
Atomic       :      60041748
synchronized/BaseLine : 4.55
Lock/BaseLine         : 1.73
Atomic/BaseLine       : 0.73
synchronized/Lock     : 2.62
synchronized/Atomic   : 6.28
Lock/Atomic           : 2.39
============================
Cycles       :        800000
BaseLine     :     180189061
synchronized :     809101989
Lock         :     257019202
Atomic       :     113139780
synchronized/BaseLine : 4.49
Lock/BaseLine         : 1.43
Atomic/BaseLine       : 0.63
synchronized/Lock     : 3.15
synchronized/Atomic   : 7.15
Lock/Atomic           : 2.27
 */