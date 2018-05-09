package southday.java.basic.concurrent.jmc.chapter6;

/* 本例主要描述：单例模式中的 “饿汉式” --> 立即加载
 * 建议： 在建立单例设计模式时，推荐使用“饿汉式”，
 * 因为： “饿汉式”代码简单，而且线程安全！！
 * 
 * “懒汉式”虽然可以延迟加载，但是如果同步得不好，就会出现非线程安全，
 * 而要实现同步，也就意味着要有一定的开销。
 * “懒汉式”请看 --> LazyMan 系列
 */

class StarveMode {
    private static StarveMode sm = new StarveMode();
    
    private StarveMode() {}
    
    public static StarveMode getInstance() {
        return sm;
    }
}

public class StarveMan {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " get a StarveMode instance, "
                        + StarveMode.getInstance().hashCode());
            }
        };
        
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
    }
}
