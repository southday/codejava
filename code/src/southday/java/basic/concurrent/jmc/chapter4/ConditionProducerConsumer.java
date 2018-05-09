package southday.java.basic.concurrent.jmc.chapter4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* 本例主要是使用Condition实例来实现生产者和消费者模式的，
 * 主要区别在于唤醒机制：
 * 1) 使用synchronized时是用：wait() notify() notifyAll()
 * 前面提到过，当有多个生产者和消费者时，使用notify()会产生“假死”情况，
 * 而使用notifyAll()虽然不会出现“假死”，但是其也唤醒了一些不必要的线程，效率地下
 * 
 * 2) 使用Condition时是用：await() signal() signalALl()
 * 不同的Condition实例在await()和signal()时，操作的线程类别不同，
 * 即通过: Lock lock = new ReentrantLock();
 *       Condition pro = lock.newCondition();
 *       Condition con = lock.newCondition();
 * 注册了两种类别的线程，这样在唤醒时就相当于明确了唤醒目标（要唤醒的线程是属于哪个类），
 * 从而不会出现唤醒贝类线程的问题，也就不会产生“假死”情况
 * 
 */

class ProducerThread implements Runnable {
    private ConditionProducerConsumer cpc;
    
    public ProducerThread(ConditionProducerConsumer cpc) {
        this.cpc = cpc;
    }
    
    @Override
    public void run() {
        while (true) {
            cpc.send();
        }
    }
}

class ConsumerThread implements Runnable {
    private ConditionProducerConsumer cpc;
    
    public ConsumerThread(ConditionProducerConsumer cpc) {
        this.cpc = cpc;
    }
    
    @Override
    public void run() {
        while (true) {
            cpc.take();
        }
    }
}

public class ConditionProducerConsumer {
    public final Lock lock = new ReentrantLock();
    public final Condition pro = lock.newCondition();
    public final Condition con = lock.newCondition();
    public final int MAX_GOODS_NUM = 4;
    public int goodsNum = 0;
    
    public static void main(String[] args) {
        ConditionProducerConsumer cpc = new ConditionProducerConsumer();
        Thread[] producer_Threads = new Thread[2];
        Thread[] consumer_Threads = new Thread[2];
        for (int i = 0; i < 2; i++) {
            producer_Threads[i] = new Thread(new ProducerThread(cpc));
            producer_Threads[i].setName("producer-" + i);
            consumer_Threads[i] = new Thread(new ConsumerThread(cpc));
            consumer_Threads[i].setName("consumer-" + i);
        }
        for (int i = 0; i < 2; i++) {
            producer_Threads[i].start();
            consumer_Threads[i].start();
        }
    }
    
    public void send() {
        try {
            lock.lock();
            while (goodsNum >= MAX_GOODS_NUM) {
                pro.await();
            }
            System.out.println(Thread.currentThread().getName() + 
                    " send goods, goodsNum = " + (goodsNum += 2));
            Thread.sleep(500);
            con.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public void take() {
        try {
            lock.lock();
            while (goodsNum <= 0) {
                con.await();
            }
            System.out.println(Thread.currentThread().getName() + 
                    " take goods, goodsNum = " + (goodsNum--));
            Thread.sleep(500);
            pro.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
