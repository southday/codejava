package southday.java.basic.concurrent.bxd;

/*
 * 什么时候使用多线程？—— 当你的资源可以被同步执行时 —— 这样可以提高效率!!
 * 
 * 自己举一个不太确切的例子，比如你要把一个变量sum从0加到100亿【每次+1】，而你的CPU是多核的，
 * 这时候你使用多线程，将sum从0加到100亿这个任务分为几个子任务同时执行，总共要加100亿次。
 * 线程1负责加30亿次，线程2负责加30亿次，线程3负责加40亿次，因为CPU是多核的（>=3），所以线程1，2，3可以同时进行。
 * 这样就可以提高效率！！  right??
 */

public class ThreadTestDemo {
    public static void main(String[] arg) {
        for(int x=0; x<50; x++)
            System.out.println(Thread.currentThread().getName()+"..."+x);
        for(int x=0; x<50; x++)
            System.out.println(Thread.currentThread().getName()+"..."+x);
        for(int x=0; x<50; x++)
            System.out.println(Thread.currentThread().getName()+"..."+x);
        //比如以上的3个循环语句，是可以同时执行的（宏观上，当然多核处理器可以实现真正的并行）
        //但如果只是用单线程，则后一个for()就必须等前一个for()结束后才执行，这样会导致效率低
        
        //所以使用多线程来改进，如下：
        new Thread() {    //使用匿名内部类，复写Thread类中的run()方法，开启一个线程
            public void run() {
                for(int x=0; x<50; x++)
                    System.out.println(Thread.currentThread().getName()+"..."+x);
            }
        }.start();
        
        Runnable r = new Runnable() {    //也是使用匿名内部类，实现Runnable接口中的run()方法
            public void run() {
                for(int x=0; x<50; x++)
                    System.out.println(Thread.currentThread().getName()+"..."+x);
            }
        };
        new Thread(r).start();    //创建一个(Runnable r)线程并开启，
        
        for(int x=0; x<50; x++)        //主线程搞定
            System.out.println(Thread.currentThread().getName()+"..."+x);
    }
}
