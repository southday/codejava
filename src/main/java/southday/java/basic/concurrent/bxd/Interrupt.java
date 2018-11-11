package southday.java.basic.concurrent.bxd;

/*
 * 当没有指定的方式让线程恢复要运行状态时，这时需要对线程的冻结状态进行清除，
 * 强制让线程恢复到运行状态中来，这样就可以操作标记让线程结束。
 * Thread类中提供了    interrupt() 方法来实现此操作
 */
class Print implements Runnable {
    private boolean flag = true;
    
    public synchronized void run() {
        int x = 0;
        while(flag) {        
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+".....Exception");
                flag = false;    //结束，不让其wait()就OK
            }
            System.out.println(Thread.currentThread().getName()+".....run----"+x++);
        }
    }
}

class Interrupt {
    public static void main(String[] args) {
        Print pr = new Print();
        
        Thread t1 = new Thread(pr);
        Thread t2 = new Thread(pr);
        
        //t1.setDaemon(true);
        //t2.setDaemon(true);
        
        t1.start();
        t2.start();
        
        int num = 0;
        while(true) {
            if(num++ == 50) {
                t1.interrupt();    //一砖头把冻结状态的线程拍醒
                t2.interrupt();
                break;
            }    
            System.out.println(Thread.currentThread().getName()+"......main run----"+num);
        }
        System.out.println("over");
    }
}
