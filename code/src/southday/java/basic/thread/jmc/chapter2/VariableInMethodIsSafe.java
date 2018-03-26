package southday.java.basic.thread.jmc.chapter2;

/* 方法内部的变量是线程安全的，因为方法内部的变量是私有的！
 * 
 * 输出结果：
 *  a set over
    b set over
    b num = 200
    a num = 100
 * Thread2 并没有影响到Thread1的输出，因为num是方法内的变量，是私有的！
 */
class HasSelfPrivateNum {
    /*
     * int num = 0; // 当num是一个实例变量是，就可能出现覆盖的问题
     * 这时候就需要给addI()方法上锁，如：
     *     public synchronized void addI(String username) {...}
     * 或者
     *     public void addI(String username) {
     *         synchronized (this) {...}
     *     }
     * 或者
     *     static Object lock = new Object();
     *     public void addI(String username) {
     *         synchronized (lock) {...}
     *     }
     * 或者
     *     public void addI(String username) {
     *         synchronized (HasSelfPrivateNum.class) {...}
     *     }
     */
    public void addI(String username) {
        try {
            int num = 0;
            if (username.equals("a")) {
                num = 100;
                System.out.println("a set over");
                Thread.sleep(2000); 
                // 这里让Thread1睡2s主要是想让Thread2来执行num = 200;
                // 然后Thread1再输出num值，从而判断Thread2的执行是否影响Thread1的num输出值
            } else {
                num = 200;
                System.out.println("b set over");
            }
            System.out.println(username + " num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PrivateNumMyThread extends Thread {
    private String username = null;
    private HasSelfPrivateNum hspn = null;
    
    public PrivateNumMyThread(HasSelfPrivateNum hspn, String username) {
        this.hspn = hspn;
        this.username = username;
    }
    
    @Override
    public void run() {
        super.run();
        hspn.addI(username);
    }
}

public class VariableInMethodIsSafe {
    public static void main(String[] args) {
        HasSelfPrivateNum hspn = new HasSelfPrivateNum();
        PrivateNumMyThread Thread1 = new PrivateNumMyThread(hspn, "a");
        Thread1.start();
        PrivateNumMyThread Thread2 = new PrivateNumMyThread(hspn, "b");
        Thread2.start();
    }
}
