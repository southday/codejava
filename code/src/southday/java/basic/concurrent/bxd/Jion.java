package southday.java.basic.concurrent.bxd;

/*
 * 守护线程，具有后台依赖性。前台线程一结束，后台守护线程无存在意义，随之也结束。
 * 
 * join 特点： （1）当A线程执行到了B线程的join方法时，那么A线程就会等待，等B线程都执行完A才会执行；
 * join 可以用来临时加入线程执行。
 * 
 * public String toString()
 *         返回该线程的字符串表示形式，包括线程【名称】、【优先级】和【线程组】。比如B线程开启A线程，则A线程就在B线程组中
 * public static void yield()
 *         暂停当前正在执行的线程对象，并执行其他线程。
 */
class JionDeamo implements Runnable {
    public void run() {
        for(int x=0;x<50;x++) {
            System.out.println(Thread.currentThread().toString()+"---"+x);
            Thread.yield();    //暂停当前正在执行的线程对象，并执行其他线程，可以稍微减缓线程的运行，而且能达到线程都有机会达到平均运行的效果
        }
    }
}

class Jion {
    public static void main(String[] args)throws InterruptedException {
        JionDeamo jio = new JionDeamo();
        
        Thread t0 = new Thread(jio);
        Thread t1 = new Thread(jio);
        Thread t2 = new Thread(jio);
        
        t0.start();        //所有线程（包括主线程），其默认优先级都是5
        t0.setPriority(Thread.MAX_PRIORITY);//改变线程优先权1-10   NORM_PRIORITY — 5（默认） MIN_PRIORITY —— 1
        t0.join(); //join抢夺执行权，join后等到t0线程执行完再执行其他线程
        
        t1.start();
        t2.start();

//        t0.join(); // 如果把t0.join()放在这【此时有t0,t1,t2三个进程处于活动状态】，
        //那么主线程必须等t0线程结束才能醒来，而至于t1,t2执行到什么状态，不影响主线程的复活。
        for(int x=0;x<50;x++)
            System.out.println("main.."+x);
        System.out.println("over");
    }
}
