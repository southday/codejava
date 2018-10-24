package southday.java.basic.concurrent.jmc.c06;

/* 本例主要介绍，使用静态代码块来实现单例
 * 
 * 使用 静态代码块 的方式可以避免非线程安全，
 * 但又是为什么要使用静态代码块来实现单例呢？
 * 
 */

public class StaticBlockSignleton {
    private static StaticBlockSignleton sbs = null;
    
    private StaticBlockSignleton() {}
    
    static {
        sbs = new StaticBlockSignleton();
    }
    
    public static StaticBlockSignleton getInstance() {
        return sbs;
    }
    
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " get a instance, "
                        + StaticBlockSignleton.getInstance().hashCode());
            }
        };
        
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
    }
}
