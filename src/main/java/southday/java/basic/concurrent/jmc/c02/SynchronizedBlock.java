package southday.java.basic.concurrent.jmc.c02;

/* 本例主要说明：为什么使用同步代码块(而不是同步方法)可以改善程序性能？
 * 因为：在同步方法中，其实有些过程是不需要同步的，可以让线程异步运行，
 * 使用同步代码块就可以让一个方法中需要同步的地方得以同步，而不需要的地方可以异步，
 * 这样可以提高程序运行效率。
 * 
 * 1）使用synMethodRun()方法，输出
 *  a over run
    b over run
    spend time = 6002ms
 * 2）使用synBlockRun()方法，输出
 *  a over run
    b over run
    spend time = 3001ms
 * 发现，使用同步代码块，可以局部的进行同步，方法中部分过程异步执行提高了效率
 * 
 * 然而，还有另一种情况，如下：
 *  b over run
    spend time = 1458225205941ms
    a over run
 * 由于在Chapter1 中提到过，线程的启动顺序与.start()的执行顺序无关（我还是持怀疑态度），
 * 所以在代码中虽然时Thread1先start()的，但却会出现Thread2先启动并运行相关代码。
 * 这么一来就出现了endTime 先得到了值，而此时beginTime还没有得到值并且初始值是一个随机值，
 * 然后就执行了输出语句，就得到了这个意想不到的结果。
 * 
 * [注意]:这个结果说明了，同步代码块放在非同步的方法中，我们并不能保证条用方法的线程的执行顺序，
 * 也就是线程们调用这个方法是无序的。虽然同步代码块中的代码是同步了，但其他没有被同步的地方可能由于
 * 线程调用方法的顺序与[预期的顺序]不一致而得到[非预期结果]，换句话说就是”脏读“
 * 看例子--->SynchronizedDirtyRead.java
 * 
 * 这里补充一个点：
 * 同步方法和如： synchronized (this) {...} 的同步代码块，使用的都是对象锁
 */

class SynBlockExmp {
    static long beginTime, endTime;
    public synchronized void synMethodRun() {
        // 我们将Thread.sleep(3000)表示为方法中不需要同步的过程体
        if (Thread.currentThread().getName().equals("a")) {
            beginTime = System.currentTimeMillis();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " over run");
        if (Thread.currentThread().getName().equals("b")) {
            endTime = System.currentTimeMillis();
            System.out.println("spend time = " + (endTime - beginTime) + "ms");
        }
    }
    
    public void synBlockRun() {
        // 我们将Thread.sleep(3000)表示为方法中不需要同步的过程体
        if (Thread.currentThread().getName().equals("a")) {
            beginTime = System.currentTimeMillis();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " over run");
        }
        if (Thread.currentThread().getName().equals("b")) {
            endTime = System.currentTimeMillis();
            System.out.println("spend time = " + (endTime - beginTime) + "ms");
        }
    }
}

public class SynchronizedBlock {
    public static void main(String[] args) {
        SynBlockExmp sbe = new SynBlockExmp();
        Thread Thread1 = new Thread(new Runnable(){
            @Override
            public void run() {
                sbe.synMethodRun();
//                sbe.synBlockRun();
            }
        });
        Thread Thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sbe.synMethodRun();
//                sbe.synBlockRun();
            }
        });
        Thread1.setName("a");
        Thread2.setName("b");
        Thread1.start();
        Thread2.start();
    }
}
