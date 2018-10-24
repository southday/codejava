package southday.java.basic.concurrent.jmc.c01;

/* 使用suspend()和resume()方法造成同步资源遭独占的问题
 * 
 * 如下面的例子：
 * Thread1 在获得printString()方法的锁this对象后，被暂停了
 * Thread2 启动，想要进入printString()方法却无法进入，因为没有锁，
 * 这样就导致了Thread2一直在等待永远被暂停的Thread1释放this锁
 * 成了一个类似与死锁的问题!
 * 
 * 这是一个“外部死锁“(这个词是为自己定义的，不要多想，只是为了区分接下来的println()锁）
 * 接下来来看一个”内部死锁“，即这个锁是源码级的，其实本质是一样的，只不过锁是在JDK中
 * 请看 --> SuspendResumePrintlnLock.java
 */

class SynchronizedSuspendResume {
    public synchronized void printString() {
        System.out.println("begin");
        if (Thread.currentThread().getName().equals("a")) {
            System.out.println("a 线程永远suspend!");
            Thread.currentThread().suspend();
        }
        System.out.println("end");
    }
}

public class SuspendResumeEngross {
    public static void main(String[] args) {
        try {
            final SynchronizedSuspendResume srobj = new SynchronizedSuspendResume();
            Thread Thread1 = new Thread() {
                @Override
                public void run() {
                    srobj.printString();
                }
            };
            Thread1.setName("a");
            Thread1.start();
            Thread.sleep(1000);
            
            Thread Thread2 = new Thread() {
                @Override
                public void run() {
                    System.out.println("Thread2 已经启动，但进不了printString()方法");
                    System.out.println("因为printString()方法的锁被已处于暂停状态的Thread1拿去了，得不到释放");
                    srobj.printString();
                }
            };
            Thread2.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
