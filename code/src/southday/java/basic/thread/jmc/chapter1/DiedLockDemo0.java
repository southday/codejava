package southday.java.basic.thread.jmc.chapter1;

public class DiedLockDemo0 {

    public static void main(String[] args) {
        Thread t0 = new Thread(new DiedLockThreadClass("Thread 0", true));
        Thread t1 = new Thread(new DiedLockThreadClass("Thread 1", false));
        t0.start();
        t1.start();
    }
}

class DiedLockThreadClass implements Runnable {
    static Object lock1 = new Object();
    static Object lock2 = new Object();
    String msg;
    boolean flag = true;
    
    public DiedLockThreadClass(String msg, boolean flag) {
        this.msg = msg;
        this.flag = flag;
    }
    
    @Override
    public void run() {
        if (flag) {
            while (true) {
                synchronized (lock1) {
                    System.out.println(msg + "--lock1");
                    synchronized (lock2) {
                        System.out.println(msg + "--lock2");
                    }
                }
            }
        } else {
            while (true) {
                synchronized (lock2) {
                    System.out.println(msg + "**lock2");
                    synchronized (lock1) {
                        System.out.println(msg + "**lock1");
                    }
                }
            }
        } // else
    }
}
