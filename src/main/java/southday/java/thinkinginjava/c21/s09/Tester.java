package southday.java.thinkinginjava.c21.s09;

import com.github.southday.mindview.util.Generated;
import com.github.southday.mindview.util.RandomGenerator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * P755 免锁容器 泛型测试框架
 * @author southday
 * @date 2019/4/10
 */
public abstract class Tester<C> {
    static int testReps = 10;
    static int testCycles = 1000;
    static int containerSize = 1000;
    static ExecutorService exec = Executors.newCachedThreadPool();

    C testContanier;
    String testId;
    int nReaders;
    int nWriters;
    volatile long readResult = 0;
    volatile long readTime = 0;
    volatile long writeTime = 0;
    CountDownLatch endLatch;
    Integer[] writeData;

    abstract C containerInitializer();
    abstract void startReadersAndWriters();

    Tester(String testId, int nReaders, int nWriters) {
        this.testId = String.format("%s %dr %dw", testId, nReaders, nWriters);
        this.nReaders = nReaders;
        this.nWriters = nWriters;
        writeData = Generated.array(Integer.class, new RandomGenerator.Integer(), containerSize);
        for (int i = 0; i < testReps; i++) {
            runTest();
            readTime = 0;
            writeTime = 0;
        }
    }

    void runTest() {
        endLatch = new CountDownLatch(nReaders + nWriters);
        testContanier = containerInitializer();
        startReadersAndWriters();
        try {
            endLatch.await();
        } catch (InterruptedException e) {
            System.out.println("endLatch run interrupted");
        }
        System.out.printf("%-27s %14d %14d\n", testId, readTime, writeTime);
        if (readTime != 0 && writeTime != 0)
            System.out.printf("%-27s %14d\n", "readTime + writeTime =", readTime + writeTime);
    }

    abstract class TestTask implements Runnable {
        abstract void test();
        abstract void putResults();
        long duration;

        public void run() {
            long startTime = System.nanoTime();
            test();
            duration = System.nanoTime() - startTime;
            synchronized (Tester.this) {
                putResults();
            }
            endLatch.countDown();
        }
    }

    public static void initMain(String[] args) {
        System.out.printf("%-27s %14s %14s\n", "Type", "Read time", "Write time");
    }
}
