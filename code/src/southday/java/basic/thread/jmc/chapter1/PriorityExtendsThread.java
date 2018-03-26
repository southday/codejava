package southday.java.basic.thread.jmc.chapter1;

/* 本例主要说明：优先级具有继承性
 * 
 * 意外收获：
 *     子线程时在父线程创建子线程对象的时候继承优先级的，而不是在子线程对象.start()的时候继承的，
 * 所以即使接下来父线程再改变优先级，子线程的优先级也不变;
 *
 */
public class PriorityExtendsThread {
    public static void main(String[] args) {
        System.out.println("main Thread begin priority = " + Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(7);
        System.out.println("main Thread now priority = " + Thread.currentThread().getPriority());
        Thread mainSon = new Thread() {
            @Override
            public void run() {
                System.out.println("mainSon priority = " + this.getPriority());
                Thread mainSonSon = new Thread() {
                    @Override
                    public void run() {
                        System.out.println("mainSonSOn priority = " + this.getPriority());
                    }
                };
                mainSonSon.start();
            }
        };
//        System.out.println("main Thread begin priority = " + Thread.currentThread().getPriority());
//        Thread.currentThread().setPriority(7);
//        System.out.println("main Thread now priority = " + Thread.currentThread().getPriority());
        mainSon.start();
    }
}
