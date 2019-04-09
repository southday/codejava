package southday.java.thinkinginjava.c21.s07;

import southday.java.thinkinginjava.c15.s03.Generator;
import southday.java.thinkinginjava.c15.s04.s04.BasicGenerator;

import java.util.List;
import java.util.concurrent.*;

class ExchangerProducer<T> implements Runnable {
    private Generator<T> generator;
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    public ExchangerProducer(Exchanger<List<T>> exchg, Generator<T> gen, List<T> holder) {
        exchanger = exchg;
        generator = gen;
        this.holder = holder;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i < ExchangerDemo.size; i++) {
                    T x = generator.next();
                    System.out.println("produce " + x);
                    holder.add(x);
                }
                // exchange full for empty
                // 将阻塞直至对方任务调用自己的exchange()方法
                holder = exchanger.exchange(holder);
            }
        } catch (InterruptedException e) {
            System.out.println("ExchangerProducer run interrupted");
        }
    }
}

class ExchangerConsumer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private volatile T value;
    public ExchangerConsumer(Exchanger<List<T>> exch, List<T> holder) {
        exchanger = exch;
        this.holder = holder;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                holder = exchanger.exchange(holder);
                for (T x : holder) {
                    value = x; // fetch out value
                    // CopyOnWriteArrayList 允许在列表被遍历时调用remove()方法
                    System.out.println("---consume " + x);
                    holder.remove(x); // Ok for CopyOnWriteArrayList
                }
            }
        } catch (InterruptedException e) {
            System.out.println("ExchangerConsumer run interrupted");
        }
        System.out.println("Final value: " + value);
    }
}

/**
 * P736
 * @author southday
 * @date 2019/4/9
 */
public class ExchangerDemo {
    static int size = 10;
    static int delay = 60; // milliseconds

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        Exchanger<List<Fat>> xc = new Exchanger<>();
        List<Fat>
                producerList = new CopyOnWriteArrayList<>(),
                consumerList = new CopyOnWriteArrayList<>();
        exec.execute(new ExchangerProducer<>(xc, BasicGenerator.create(Fat.class), producerList));
        exec.execute(new ExchangerConsumer<>(xc, consumerList));
        TimeUnit.MILLISECONDS.sleep(delay);
        exec.shutdownNow();
    }
}
