package southday.java.basic.thread.jmc.chapter2;

import java.util.concurrent.atomic.AtomicInteger;

/* 本例旨在说明：在某些情况下，使用原子类也不一定安全
 * 如：
 *  method() {
 *      atomProc1();
 *      ...
 *      atomProc2();
 *  }
 *  方法中的atomProc1()和atomProc2()都具有原子性，但是
 *  从atomProc1()到2()，这个调用过程不具有原子性，所以依旧可能出错
 * 
 * 输出结果：
 *  Thread-3 加了100之后的值是: 200
    Thread-2 加了100之后的值是: 300
    Thread-0 加了100之后的值是: 400
    Thread-4 加了100之后的值是: 503
    Thread-1 加了100之后的值是: 100
    count = 505
    
    按理来说，打印的顺序应该如下：
    xxx 加了100之后的值时：100
    xxx 加了100之后的值时：201
    xxx 加了100之后的值时：302
    xxx 加了100之后的值时：403
    xxx 加了100之后的值时：504
    count = 505
 */

class AAtomClass {
    AtomicInteger count = new AtomicInteger();
    
    public void addCount() {
        System.out.println(Thread.currentThread().getName() + " 加了100之后的值是: " + count.addAndGet(100));
        count.addAndGet(1);
    }
}

class TestAtomClassThread extends Thread {
    AAtomClass aac;
    
    public TestAtomClassThread(AAtomClass aac) {
        this.aac  = aac;
    }
    
    @Override
    public void run() {
        aac.addCount();
    }
}

public class AtomClassAlsoNoSafety {
    public static void main(String[] args) {
        AAtomClass aac = new AAtomClass();
        TestAtomClassThread[] tacts = new TestAtomClassThread[5];
        for (int i = 0; i < 5; i++) {
            tacts[i] = new TestAtomClassThread(aac);
        }
        for (int i = 0; i < 5; i++) {
            tacts[i].start();
        }
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("count = " + aac.count);
    }
}
