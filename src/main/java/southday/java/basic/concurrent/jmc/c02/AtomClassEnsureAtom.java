package southday.java.basic.concurrent.jmc.c02;

import java.util.concurrent.atomic.AtomicInteger;

/* 本例旨在描述：使用原子类才确保原子性操作：
 * 如：使用AtomicInteger
 * java.lang.Object
 *      |-- java.lang.Number
 *          |-- java.util.concurrent.atomic.AtomicInteger
 * 使用count.incrementAndGet()后，保证了原子性，成功累加到了预期值
 */

class AtomicCountThread extends Thread {
    AtomicInteger count = new AtomicInteger();
    
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("count = " + count.incrementAndGet());
        }
    }
}

public class AtomClassEnsureAtom {
    public static void main(String[] args) {
        AtomicCountThread act = new AtomicCountThread();
        Thread Thread0 = new Thread(act);
        Thread0.start();
        
        Thread Thread1 = new Thread(act);
        Thread1.start();
        
        Thread Thread2 = new Thread(act);
        Thread2.start();
        
        Thread Thread3 = new Thread(act);
        Thread3.start();
        
        Thread Thread4 = new Thread(act);
        Thread4.start();
    }
}
