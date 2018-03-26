package southday.java.basic.thread.jmc.chapter3;

/* 本例主要描述一个新的类（对于我来说时新的了）： ThreadLocal
 * []: 我们知道，变量的共享可以使用public static 关键字，这样所有线程都可以共享同一个变量，
 * 然而，现在我们想要让每一个线程都有自己的专有变量，该怎么做呢？
 * []: 使用ThreadLocal即可实现
 * 类ThreadLocal 主要解决的就是--->每个线程绑定自己的值，
 * 可以将ThreadLocal类比喻成存放数据的盒子，盒子中可以存放每个线程的私有数据。
 * []: 不同线程拥有自己的值，不同线程的值可以放入ThreadLocal类中进行保存
 * 
 * 通过输出可以发现：
 * 线程main A B 都分别有了自己的变量，而这些变量都保存在一个ThreadLocal实例中
 * 简单看了下ThreadLocal中的set(T value)方法的实现，过多的现在还没取深入理解,
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }    
    从几行代码中可以看出：
    每个线程都会有一个 ThreadLocalMap 与之对应，里面存放了以ThreadLocal实例为
    key，以set()中的值为value，来进行存储不同线程的属性的
    
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }
    发现get()方法也是先通过当前线程当作索引，获得一个ThreadLocalMap实例，然后才根据该实例值的情况，
    让而，另我搞不懂的是：---> 为什么要在后面加 return setInitialValue();方法，这样不是对于原本
    不存在变量的线程，还要设置空间吗？
 */

public class ThreadLocalDemo0 {
    public static ThreadLocal<Object> Thread_local = new ThreadLocal<Object>();
    
    public static void main(String[] args) {
        Thread Thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    Thread_local.set(Thread.currentThread().getName() + i);
                    System.out.println(Thread.currentThread().getName() + " --> "+ Thread_local.get());
                }
            }
        });
        Thread0.setName("A");
        Thread0.start();
        
        Thread Thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    Thread_local.set(Thread.currentThread().getName() + i);
                    System.out.println(Thread.currentThread().getName() + " --> "+ Thread_local.get());
                }
            }
        });
        Thread1.setName("B");
        Thread1.start();
        
        // main
        for (int i = 0; i < 2; i++) {
            Thread_local.set(Thread.currentThread().getName() + i);
            System.out.println(Thread.currentThread().getName() + " --> "+ Thread_local.get());
        }
    }
}
