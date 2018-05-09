package southday.java.basic.concurrent.jmc.chapter2;

/* 本例主要讲解：内部类中的同步，其实和类方法中的同步是差不多的，
 * 关键是要搞清楚：  谁持有锁，什么样的锁，谁需要锁，什么样的锁
 * 
 * 可以看到：
 * 1) 线程(A B) (B C)是异步的
 * 2) 线程(A C) 是同步的
 */

class OuterClass {
    
    static class InnerClass0 {
        public void show0(InnerClass1 ic1) {
            synchronized (ic1) {
                System.out.println("线程" + Thread.currentThread().getName() + " in");
                System.out.println("线程" + Thread.currentThread().getName() + " out");
                System.out.println("-------------------------");
            }
        }
        
        public synchronized void show1() {
            System.out.println("线程" + Thread.currentThread().getName() + " in");
            System.out.println("线程" + Thread.currentThread().getName() + " out");
            System.out.println("-------------------------");
        }
    }
    
    static class InnerClass1 {
        public synchronized void show2() {
            System.out.println("线程" + Thread.currentThread().getName() + " in");
            System.out.println("线程" + Thread.currentThread().getName() + " out");
            System.out.println("-------------------------");
        }
    }
}

public class InnerClassSynchronized {
    public static void main(String[] args) {
        OuterClass.InnerClass0 ic0 = new OuterClass.InnerClass0();
        OuterClass.InnerClass1 ic1 = new OuterClass.InnerClass1();
        
        Thread Thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                ic0.show0(ic1);
            }
        });
        Thread0.setName("A");
        Thread Thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ic0.show1();
            }
        });
        Thread1.setName("B");
        Thread Thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                ic1.show2();
            }
        });
        Thread2.setName("C");
        
        Thread0.start();
        Thread1.start();
        Thread2.start();
    }
}
