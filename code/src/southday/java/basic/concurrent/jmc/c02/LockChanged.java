package southday.java.basic.concurrent.jmc.c02;

/* 本例主要描述：程序运行中，锁的改变造成了线程的异步执行
 * 
 * 发现：
 * 1) 线程A拿到lock("123")后，修改了lock锁的值(lock = "456"),
 * 原本没有抢到锁的线程B就不需要再等待线程A释放锁了，因为此时的锁lock为“456",
 * 如方法：show()的运行效果
 * 
 * 2) 只要对象不变，对象的属性改变，运行结果还是同步
 * 如方法：test()的运行效果
 */

public class LockChanged {
    static String lock = "123";
    
    public static void main(String[] args) {
        LockChanged lc = new LockChanged();
        Thread Thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
//                LockChanged.show(); // 锁lock值改变
                lc.test(); // 对象lc的属性lock值改变
            }
        });
        Thread0.setName("A");
        Thread Thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
//                LockChanged.show();
                lc.test(); // 对象lc的属性lock值改变
            }
        });
        Thread1.setName("B");
        Thread0.start();
        Thread1.start();
    }
    
    public static void show() {
        synchronized (lock) {
            System.out.println("线程" + Thread.currentThread().getName() + " in");
            lock = "456"; // 锁被改变
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println("线程" + Thread.currentThread().getName() + " out");
        }
    }
    
    public void test() {
        synchronized (this) {
            System.out.println("线程" + Thread.currentThread().getName() + " in");
            lock = "456"; // 对象的属性改变 
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println("线程" + Thread.currentThread().getName() + " out");
        }
    }
}
