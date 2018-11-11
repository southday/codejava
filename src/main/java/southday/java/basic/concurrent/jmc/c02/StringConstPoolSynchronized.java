package southday.java.basic.concurrent.jmc.c02;

/* 本例主要提醒coder注意：JVM中具有String常量持缓冲，看例子即可明白！
 * 正是因为这个特性，synchronized 同步块中一般不用String类型作为锁
 * 
 * 发现：
 * 1）当run()中的方法是 show("Thread"); 时，两个线程时同步执行的;
 * 2）当run()中的方法时 show(new String("Thread")); 时， 两个线程时异步执行的
 * 这也说明了在内存中，字符串对象和字符串常量存储的区别！
 */
public class StringConstPoolSynchronized {
    public static void main(String[] args) {
        StringConstPoolSynchronized.showStringConst();
        Thread Thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
//                StringConstPoolSynchronized.show("Thread");
                StringConstPoolSynchronized.show(new String("Thread"));
            }
        });
        Thread Thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                StringConstPoolSynchronized.show("Thread");
                StringConstPoolSynchronized.show(new String("Thread"));
            }
        });
        Thread1.start();
        Thread2.start();
    }
    
    // 测试JVM中的常量池，即str1 和 str2指向同一个地址，该地址中保存了常量"LiChaoxi"
    public static void showStringConst() {
        String str1 = "LiChaoxi";
        String str2 = "LiChaoxi";
        System.out.println("str1 == str2 ? -> " + (str1 == str2));
        
        // 而对于Integer
        Integer integer1 = 222;
        Integer integer2 = 222;
        System.out.println("integer1 == integer2 ? -> " + (integer1 == integer2));
        // 说道Integer，需要注意的是：
        /* 当Integer num = 0 ~ 127 之间的数时，如
         * Integer num1 = 127;
         * Integer num2 = 127;
         * 这种情况下 num1 == num2 -> true
         * 即：num1和num2都指向一个地址，该地址中保存了常量127
         */
    }
    
    public static void show(String str) {
        synchronized (str) {
            System.out.println(Thread.currentThread().getName() + " in ");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println(Thread.currentThread().getName() + " out ");
        }
    }
}
