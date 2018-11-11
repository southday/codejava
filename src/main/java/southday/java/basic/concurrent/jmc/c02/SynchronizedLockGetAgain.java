package southday.java.basic.concurrent.jmc.c02;

/* 本例主要讲：锁重入和通过继承的锁重入
 * "可重入锁“：线程A获得了某个对象的锁，此时这个对象锁还没有释放，
 * 当其再次想要获得这个对象的锁时，是可以再次获得的（或者说A压根就没释放过锁，还是可以进门的），
 * 如果不能”锁重入“的话，会造成死锁问题。
 * 
 * 1）自己可以再次获得自己的内部锁
 * 2）子类可以通过”可重入锁“调用父类的同步方法
 * 
 * 由于知识点差不多，这里为就不重新创建类来说明了，直接写在这里：
 * 1）出现异常时，线程自动释放锁：
 * A、B两线程要访问一个同步方法，A先拿到了锁，B等待，A运行出现异常，A释放锁，B获得锁后访问方法
 * 
 * 2）同步不具有继承性，这个东西好像不用说都知道，因为重写格式不对啊
 * 如：
 *  Father {
 *      public synchronized void run() {...}
 *  }
 *  Son extends Father {
 *      @Override
 *      public void run() {...}
 *  }
 * father.run()有同步机制，而son.run()无同步机制
 */

class LockGetAgainFather {
    public int i = 10;
    public synchronized void operateFaterMethod() {
        try {
            System.out.println("father print i = " + i--);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void show() {
        System.out.println("father show i = " + i--);
    }
}

class LockGetAgainSon extends LockGetAgainFather {
    public synchronized void operateSonMethod() {
        try {
            while (i > 0) {
                System.out.println("son print i = " + i--);
                Thread.sleep(100);
                super.operateFaterMethod();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void show() { // 并没有继承synchronized
        System.out.println("son show i = " + i--);
    }
}

public class SynchronizedLockGetAgain {
    public static void main(String[] args) {
        Thread Thread = new Thread() {
            @Override
            public void run() {
                LockGetAgainSon son = new LockGetAgainSon();
                son.operateSonMethod();
            }
        };
        Thread.start();
    }
}
