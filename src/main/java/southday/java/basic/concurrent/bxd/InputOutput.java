package southday.java.basic.concurrent.bxd;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * notify():如果所有线程都在此对象上等待，则会选择唤醒其中“一个线程”。选择是“任意性”的，并在对实现做出决定时发生。
 *             notify往往唤醒的是线程池中的第一个，线程队列（先进先出）
 * 
 * JDK1.5中提供了多线程升级的解决方案：
 *         （1）将同步synchronized替换成现实Lock操作；
 *         （2）将Object中的wait(),notify(),notifyAll()替换成Condition对象，该对象可用Lock锁进行操作；
 *         （3）显示的锁机制，显示的等待，唤醒机制；一个锁对应多类别线程操作；
 * 在该实例中实现了本方只唤醒对方的操作：使用多个Condition对象
 * 下面是我对JDK1.5多线程中新特性的使用，我会把原来的代码注释（保留）
 */
class Resource {
    private String name;
    private int count = 1;
    private boolean flag = false;
    
    //新特性（1）Lock 与 Condition。Lock的子类ReentrantLock,Condition对象可通过Lock中的方法获取
    private Lock lock = new ReentrantLock();    //需要导包
    private Condition condition_pro = lock.newCondition();
    private Condition condition_con = lock.newCondition();
    
    // t0 t1
    /*
    public synchronized void set(String name) {
        while(flag)    //如果只是if(flag)，则原本沉睡的t1被t0唤醒后，不用判断直接输出，就会造成生产两个商品，只消费一次
            try{wait();}catch(Exception e){};    //wait()方法抛出的是InterruptedException异常
        this.name = name+"----"+count++;
        System.out.println(Thread.currentThread().getName()+"--生产---"+this.name);
        flag = true;
        notifyAll();    //notifyAll确保对方线程能被唤醒,当然本方线程也能被唤醒
        */
    public void set(String name) throws InterruptedException { //也可以不抛异常，用try,catch的方式解决
        //新特性（2）显示的锁机制，获取锁，释放锁
        lock.lock();//获取锁
        
        try {
            while(flag)
                condition_pro.await();    //throws InterruptedException
            this.name = name+"----"+count++;
            System.out.println(Thread.currentThread().getName()+"--生产---"+this.name);
            flag = true;
            condition_con.signal();//只唤醒对方线程，因为是用的是condition_con
        } finally {  //如果不catch()，则在函数上需要抛出异常
            lock.unlock(); //在线程被等待后，一定要执行释放锁的操作，所以用finally代码块
        }
    }
    
    // t2 t3
    /*
    public synchronized void out() {
        while(!flag)
            try{wait();}catch(Exception e){};        
        System.out.println(Thread.currentThread().getName()+"------消费-----"+name);
        flag = false;
        notifyAll();
        */
    //新特性（2）显示的锁机制，获取锁，释放锁
    public void out() throws InterruptedException {
        lock.lock();
        
        try {
            while(!flag) 
                condition_con.await();
            System.out.println(Thread.currentThread().getName()+"------消费-----"+name);
            flag = false;
            condition_pro.signal();
        } finally {
            lock.unlock();
        }
    }
}

class Product implements Runnable {
    private Resource rce;
    Product(Resource rce) {
        this.rce = rce;
    }
    
    public void run() {
        /*
        while(true)
            rce.set("商品号");*/
        
        //新特性-------因为在使用JDK1.5多线程新特性时，上面的set函数我抛出了异常，所以这里也需要处理
        while(true)    //而要保证是对run()方法进行复写，就不能在run()方法上抛出异常，所以在这里使用try,catch方法处理
            try{rce.set("商品号");} catch(InterruptedException e){}
    }
}

class Consumer implements Runnable {
    private Resource rce;
    Consumer(Resource rce) {
        this.rce = rce;
    }
    
    public void run() {
        /*
        while(true)
            rce.out();*/
        
        //新特性-------因为在使用JDK1.5多线程新特性时，上面的set函数我抛出了异常，所以这里也需要处理
        while(true)    //而要保证是对run()方法进行复写，就不能在run()方法上抛出异常，所以在这里使用try,catch方法处理
            try{rce.out();} catch(InterruptedException e){}
    }
}

class InputOutput {
    public static void main(String[] args) {
        Resource rce = new Resource();

        new Thread(new Product(rce)).start();
        new Thread(new Product(rce)).start();
        new Thread(new Product(rce)).start();
        
        new Thread(new Consumer(rce)).start();
        new Thread(new Consumer(rce)).start();
        new Thread(new Consumer(rce)).start();
        
        /*
        Product pro = new Product(rce);
        Consumer con = new Consumer(rce);
        
        Thread t0 = new Thread(pro);
        Thread t1 = new Thread(pro);
        Thread t2 = new Thread(con);
        Thread t3 = new Thread(con);
        
        t0.start();
        t1.start();
        t2.start();
        t3.start();*/
        
    }
}

/*
关于notifyAll():
（1）假设当前  flag = false， 而线程池中有  t0 t1（set）处于wait状态，现在t2抢到了执行权，执行代码后 flag = false，并且唤醒全部在线程池中的线程；
（2）就算唤醒t0 t1后，t3也会抢到执行权，（假设t3抢到），经过判断if(!flag)然后进入线程池wait了；
（3）所以现在只有t0 t1具备执行资格，假设t0抢到执行权，执行后 flag = true，并且唤醒了 t2 t3；
（4）即使随后t1抢到执行权，经过if(flag)后也会进入线程池wait，所以t0唤醒后只有t2 t3可能执行；
（5）以此类推，t2/t3执行后，唤醒wait线程后，也只有 t0/t1可能执行；
（6）所以保证了：  一生产一消费； */
