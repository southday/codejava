package southday.java.basic.concurrent.jmc.chapter1;

import java.util.Random;

/* 本例主要是：演示一下线程优先级的作用效果
 * 对于同样的执行体，开辟两个线程，分别对这两个线程赋予不同的优先级进行测试。
 * 
 * 1) 可以看到，即使是Thread1先start()，但具有更高优先级的Thread2几乎每次都是先执行完毕
 * 这说明了：线程的优先级具有一定的规则性
 * 
 * 2) 线程的优先级具有一定的规则性，但其也具有随机性
 * 即：不是说具有高优先级的线程就一定在低优先级的线程前执行完，
 * (1)中的例子，优先级分别是1和10,相差较大，
 * 当我们缩小优先级的差距，比如Thread1 5，Thread2 6,这样随机性就更容易体现出来
 * 我们会发现，即使Thread1优先级更低，但其也可能在Thread2前执行完毕
 */

class PriorityMyThread extends Thread {
    private int tag = 0;
    
    public PriorityMyThread(int tag) {
        this.tag = tag;
    }
    
    @Override
    public void run() {
        long beginTime = System.currentTimeMillis();
        long result = 0l;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 50000; j++) {
                Random random = new Random();
                random.nextInt();
                result += (long) i;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Thread " + this.tag + " use time = " + (endTime - beginTime) + "ms");
    }
}

public class PriorityDemo {
    public static void main(String[] args) {
        PriorityMyThread Thread1 = new PriorityMyThread(1);
        PriorityMyThread Thread2 = new PriorityMyThread(2);
//        Thread1.setPriority(1);
        Thread1.setPriority(5);
        Thread1.start();
//        Thread2.setPriority(10);
        Thread2.setPriority(6);
        Thread2.start();
    }
}
