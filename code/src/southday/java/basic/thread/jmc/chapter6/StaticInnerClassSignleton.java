package southday.java.basic.thread.jmc.chapter6;

/* 本例讲的是，使用静态内部类来实现单例模式的方法，也可以达到线程安全，
 * [其实为感觉就是“饿汉式”的变化版]
 * 
 * 由于好奇为什么有静态内部类来实现单例模式这种方法，为就取科普了下，
 * 发现坑好大，看了几篇文章发现原来为以为搞定的LazyMan3.java还是会出现一定问题的。
 * 
 * 由于原因涉及到一些我还没学的知识，所以目前无法作深入解说，
 * 有篇文章上分析，主要是因为JVM中没有规定有关编译器优化的内容，也就是说JVM可以自由
 * 地实现优化。并且，JVM中没有明确规定：创建一个变量的步骤，是新申请一块内存，然后
 * 再用构造方法初始化，还是先用构造方法初始化，再申请内存。（那位作者是这么说的，但我
 * 自己没研究过，也不能全信）。
 * 文章地址：http://devbean.blog.51cto.com/448512/203501/
 * 
 * 文章中举了了例子，我不太明确， 等学完编译原理，估计就会更容易理解了！
 * 
 * 话转回来，正是因为上面说的某些原因，Java在1.5版本后出现了volatile关键字，确保了
 * 被volatile修饰的写变量不能和之前的读写代码调整，读变量不能和之后的读写代码调整。
 * 而这是Java1.5版本的解决方法，1.5之前的解决方法呢？ 就是该例中的“静态内部类实现单例“方法
 * 
 */

public class StaticInnerClassSignleton {
    // inner class
    public static class InnerForObjectHandler {
        private static StaticInnerClassSignleton sics = new StaticInnerClassSignleton();
    }
    
    private StaticInnerClassSignleton() {}
    
    public static StaticInnerClassSignleton getInstance() {
        return InnerForObjectHandler.sics;
    }
    
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " get a instance, "
                        + StaticInnerClassSignleton.getInstance().hashCode());
            }
        };
        
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
    }
}
