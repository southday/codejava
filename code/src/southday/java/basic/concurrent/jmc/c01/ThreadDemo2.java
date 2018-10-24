package southday.java.basic.concurrent.jmc.c01;

class CountOperator extends Thread {
    public CountOperator() {
        System.out.println("CountOperator --- begin");
        System.out.println("Thread.currentThread().getName() = "
                + Thread.currentThread().getName());
        System.out.println("this.getName() = " + this.getName());
        System.out.println("CountOperator -- end");
    }
    
    @Override
    public void run() {
        System.out.println("run --- begin");
        System.out.println("Thread.currentThread().getName() = "
                + Thread.currentThread().getName());
        System.out.println("this.getName() = " + this.getName());
        System.out.println("run --- end");
    }
}

public class ThreadDemo2 {
    public static void main(String[] args) {
        CountOperator co = new CountOperator();
        Thread t1 = new Thread(co);
        t1.setName("A");
        t1.start();
    }
}

/* Output:
CountOperator --- begin
Thread.currentThread().getName() = main
this.getName() = Thread-0 // 其中，这个Thread-0是由Thread类的构造函数产生的
CountOperator -- end
run --- begin
Thread.currentThread().getName() = A
this.getName() = Thread-0 // 因为传到Thread()中的参数是同一个对象，所以this.getName() = THread-0
run --- end
 */
