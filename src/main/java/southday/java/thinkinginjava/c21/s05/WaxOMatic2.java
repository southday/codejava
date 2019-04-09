package southday.java.thinkinginjava.c21.s05;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Car {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean waxOn = false;

    public void waxed() {
        lock.lock();
        try {
            waxOn = true; // wait for buff
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void buffed() {
        lock.lock();
        try {
            waxOn = false; // wait for wax
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void waitForWaxing() throws InterruptedException {
        lock.lock();
        try {
            while (!waxOn)
                condition.await();
        } finally {
            lock.unlock();
        }
    }

    public void waitForbuffing() throws InterruptedException {
        lock.lock();
        try {
            while (waxOn)
                condition.await();
        } finally {
            lock.unlock();
        }
    }
}

class WaxOn implements Runnable {
    private Car car;
    public WaxOn(Car c) {
        car = c;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.print("Wax on! ");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed(); // 已涂腊，waxOn 为 true 表示：等待抛光 / 准备抛光
                car.waitForbuffing(); // 等待抛光
            }
        } catch (InterruptedException e) {
            System.out.println("waxon: Exiting via interrupted");
        }
        System.out.println("Ending wax on task");
    }
}

class WaxOff implements Runnable {
    private Car car;
    public WaxOff(Car c) {
        car = c;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.waitForWaxing(); // 等待涂腊
                System.out.print("Wax off! ");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffed(); // 已抛光，waxOn 为 false 表示：等待涂腊 / 准备涂腊
            }
        } catch (InterruptedException e) {
            System.out.println("waxoff: Exiting via interrupted");
        }
        System.out.println("Ending wax off task");
    }
}

/**
 * Lock Condition <br/>
 * P713 给汽车涂腊（WaxOn），抛光（Buff）<br/>
 * 涂腊后才能抛光，抛光后才能涂腊
 * @author southday
 * @date 2019/4/7
 */
public class WaxOMatic2 {
    public static void main(String[] args) throws Exception {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new WaxOff(car));
        exec.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
