package southday.java.basic.concurrent.jmc.c03.pc;

/* 本例主要讲：多个生产者和消费者对一个仓库资源进行同步操作
 * 由notify()方法唤醒同类线程而造成了“假死”的情况
 * 
 * [假死] : 线程们都在等待，并且都得不到唤醒
 * 如：
 * 生产者线程： p1 p2
 * 消费者线程： c1 c2
 * 
 * 开始：
 * 1) p1 持锁，发现仓库无货，生产货物，p1.notify() (被忽略), p1等待，
 *     这时 p2.start()
 * 2) p2 持锁, 发现仓库有货，p2等待，
 *     这时 c1.start()
 * 3) c1 持锁，发现仓库有货，c1拿走货物，c1.notify()，c1等待
 *     这时 c2.start()
 * 4) c2 与被c1唤醒的p1争夺执行权，并且成功获得执行权，持锁，发现仓库无货，c2等待,
 * 5) p1 现在得到了执行权，持锁，发现仓库无货，生产货物，p1.notify(), p1等待,
 * 6) [p2 被 p1唤醒，持锁，发现仓库有货，p2等待]
 * 至此：线程 p1 p2 c1 c2 都在等待状态，这就是一个“假死”的产生过程
 * 
 * 下面有输出结果分析：
 */
public class ProducerAndConsumer {
    public static void main(String[] args) {
        Storage storage = new Storage();
        Producer producer = new Producer(storage);
        Consumer consumer = new Consumer(storage);
        
        Thread[] producer_Threads = new Thread[2];
        Thread[] consumer_Threads = new Thread[2];
        
        for (int i = 0; i < 2; i++) {
            producer_Threads[i] = new Thread(new ProducerThread(producer));
            producer_Threads[i].setName("p" + i);
            producer_Threads[i].start();
        }
        
        // 确保两个生产者线程先与消费者线程开启
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        for (int i = 0; i < 2; i++) {
            consumer_Threads[i] = new Thread(new ConsumerThread(consumer));
            consumer_Threads[i].setName("c" + i);
            consumer_Threads[i].start();
        }
    }
}

/*
看其中的一次运行结果：
为什么会出现这个结果？我一开始觉得挺纳闷的，感觉不可思议～
按理来说应该是生成，消费，同步的，不会出现”假死“，
然后为试着分析了下，也不知道对不对，但感觉说的通：

    producer [p0] runing    // p0 p1启动，p0抢到锁，running, p0.notify() （被忽略），p0运行结束弃锁
    producer [p0] waiting... // p0 再次抢到锁，仓库有货，waiting，p0自动放弃锁
    producer [p1] waiting... // p1 获得锁，仓库有货，waiting，p1自动放弃锁
*    -----consumer [c0] running    // c0 抢到锁，running, c0.notify()唤醒p0, c0运行结束弃锁
    -----consumer [c1] waiting... // c1 抢到锁，仓库无货，waiting
    producer [p0] runing    // p0 持锁, running， p0.notify()唤醒p1, p0运行结束弃锁
    producer [p0] waiting... // p0和p1抢锁，p0抢到锁，仓库有货，waiting，p0自动放弃锁
    -----consumer [c0] running // c0和p1抢锁，c0抢到锁，running，c0.notify()唤醒c1，c0运行结束弃锁
>    -----consumer [c0] waiting... // c0和p1、c1抢锁，c0抢到锁，仓库无货，waiting，
>    -----consumer [c1] waiting... // c1和p1抢锁，c1抢到锁，仓库无货，waiting
    producer [p1] runing    // p1终于获得锁了，running, p1.notify()唤醒p0，p1运行结束弃锁
>    producer [p1] waiting... // p1和p2抢锁，p1获得锁，仓库有货，waiting
>    producer [p0] waiting... // p0获得锁，仓库有货，waiting
    
    至此，四个线程都处于waiting状态，就出现了所谓的”假死“，不是互相需要对方的锁，而是都在等待被唤醒
    主要原因还是由于，线程唤醒了同类线程，而使用notify()只能唤醒线程池中的一个线程，
    现在，使用notifyAll()来唤醒线程池中的所有线程，发现就不会出现”假死“情况
    
    然而，我发现对于这个实例，使用notifyAll()，会唤起不必要的线程（唤醒同类线程），增大开销，
    其实，这个例子，虽说时多个生产者和多个消费值，但本质和1个生产者1个消费者是一样的，
    因为在操作仓库资源时，都是一个生产者放东西，一个消费者拿东西，
    不同的只是：生产者和消费者由多个，可以轮流着送东西和拿东西，不用那么累
*/
