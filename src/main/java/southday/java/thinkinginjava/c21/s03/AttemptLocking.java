package southday.java.thinkinginjava.c21.s03;

import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * P679
 * @author southday
 * @date 2019/4/6
 */
public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            System.out.println("untimed tryLock(): " + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }

    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("timed tryLock(2, TimeUnit.SECOND): " + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        final AttemptLocking al = new AttemptLocking();
        al.untimed(); // true
        al.timed(); // true
        new Thread(() -> {
            al.lock.lock();
            System.out.println("acquired");
        }).start();
        TimeUnit.MILLISECONDS.sleep(180);
        al.untimed();
        al.timed();
    }
}
