package southday.java.basic.concurrent.bxd;

/*
 * （1） wait(),notify(),notifyAll()都使用在同步中，因为要对持有监视器（锁）的线程操作，而监视器一定在同步里面，所以要使用在同步中
 * （2）为什么这些操作线程的方法要定义在Object类中的？
 * 答： ① 因为这些方法在操作线程同步时，都需要表示它们所操作线程持有的锁。
 *       ② 只有同一个锁上的被等待线程可以被同一个锁上的notify()唤醒，  不可以对不同锁中的线程唤醒。【即：wait()和notify()必须是同一把锁。
 *    ③ 而锁可以是任意对象，所以可以被任意对象调用的方法定义在Object类中。
 *    
 *  notify():如果所有线程都在此对象上等待，则会选择唤醒其中“一个线程”。选择是“任意性”的，并在对实现做出决定时发生。
 *           notify往往唤醒的是线程池中的第一个，线程队列（先进先出）
 */

/*
 * 一个类的成员变量一般都是private（比如：姓名，年龄等），可以提供函数对其赋值或者获取其值，而不是直接改变。
 * 所以，针对这个例子，我们可以进行一些优化：把成员变量私有化，提供函数对其进行操作，保证线程同步，保证一赋值一输出！
 * 优化情况如下【我把需要优化的先前代码注释掉】：
 */


class CoalResourse {
//    String name;
//    String sex;
//    boolean flag = false;
    
    //优化（1）把成员变量私有化
    private String name;
    private String sex;
    private boolean flag = false;
    
    //优化（2）（3）提供函数对其进行操作，确保线程同步
    public synchronized void Set(String name,String sex) {
        if(flag) {    //优化（4）保证一赋值一输出
            try{this.wait();} catch(InterruptedException e){}
        }
        this.name = name;
        this.sex = sex;
        flag = true;
        this.notify();
    }
    
    public synchronized void Print() {
        if(!flag) {    //优化（4）保证一赋值一输出
            try{this.wait();} catch(InterruptedException e){}
        }
        System.out.println(this.name+"..."+this.sex);
        flag = false;
        this.notify();
    }
}

class CoalInput implements Runnable{
    private CoalResourse cr;
    private int x;
    CoalInput(CoalResourse cr) {
        this.cr = cr;
    }
    
    public void run() {
        x=0;
        while(true) {
            /*
            synchronized (this.cr) {//也可以用 CoalInput.class（要确保下面的同步代码块也用这个锁才能保证同步）
                if(cr.flag) {    //必须表面是对哪个对象（锁）中的线程进行wait()，notify()操作的
                    try{cr.wait();} catch(InterruptedException e){}
                }
                if(x==0) {
                    cr.name = "nike";
                    cr.sex = "man";
                }
                else {
                    cr.name = "莉莉";
                    cr.sex = "女女女女女";
                }
                x = (x+1)%2;
                cr.flag = true;    //在睡眠前先唤醒其他进程，保证程序继续运行
                cr.notify();  //必须表面是对哪个对象（锁）中的线程进行wait()，notify()操作的
            }*/
            //优化代码，将同步放到资源类中提供的函数上实现！！
            if(x==0)
                cr.Set("nike", "man");
            else
                cr.Set("莉莉", "女女女女女");
            x = (x+1)%2;
        }
    }
}

class CoalOutput implements Runnable{
    private CoalResourse cr;
    public CoalOutput(CoalResourse cr) {
        this.cr = cr;
    }
    
    public void run() {
        while(true) {
            /*
            synchronized (this.cr) {  //也可以用CoalOutput.class（但要确保上面的同步代码块也用这个锁才能保证同步）
                if(!cr.flag) {
                    try{cr.wait();} catch(InterruptedException e){}
                }
                System.out.println(cr.name+"..."+cr.sex);
                cr.flag = false;
                cr.notify();
            }
            */
            //优化代码，将同步放到资源类中提供的函数上实现！！
            cr.Print();
        }
    }
}

public class InputOutputDemo {
    public static void main(String[] args) {
        CoalResourse cr = new CoalResourse();
        Thread in = new Thread(new CoalInput(cr));
        Thread out = new Thread(new CoalOutput(cr));
        
        in.start();
        out.start();
    }
}
