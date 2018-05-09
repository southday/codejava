package southday.java.basic.concurrent.jmc.chapter3;

/* 本例旨在说明：使用[InheritableThreadLocal]类可以在子线程中
 * 取得父线程继承下来的值
 * 
 * 发现两种情况：
 * 1) main线程的itl.get()在子线程对象Thread创建前执行，这时候输出：
 *  main --> main
 *  son --> main
 * 2) 子线程对象Thread先得到创建，然后main线程才执行itl.get()，这时输出：
 *  main --> main
 *  son --> A
 * 
 * 为什么会这样，目前为也说不清除，只是发现：
 * []:子线程要想使用父线程的变量，那么子线程对象就需要在父线程调用itl.set() or itl.get() 后被创建，
 * 如果子线程对象在父线程调用itl.set() / get()前被创建，则不会继承到父线程的变量.
 */

public class InheritableThreadLocalDemo0 {
    
    public static void main(String[] args) {
        InheritableThreadLocal<Object> itl = new InheritableThreadLocal<Object>() {
            @Override
            public Object initialValue() {
                return Thread.currentThread().getName();
            }
        };
        
//        System.out.println("main --> " + itl.get()); // (1)
        Thread Thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread0 --> " + itl.get());
            }
        });
        Thread0.setName("A");
        System.out.println("main --> " + itl.get()); // (2)
        
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        Thread0.start();
    }
}
