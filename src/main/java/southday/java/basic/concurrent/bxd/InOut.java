package southday.java.basic.concurrent.bxd;
/*
import java.util.concurrent.locks.*;
class Resource {
    private String name;
    private int count = 1;
    private boolean flag = false;
    private Lock lock = new ReentrantLock();
    private Condition condition_pro = lock.newCondition();
    private Condition condition_con = lock.newCondition();
    
    public void set(String name)throws InterruptedException {
        lock.lock();
        while(flag) {
            try {
                condition_pro.await();
            } finally {
                lock.unlock();
            }
        }
        this.name = name+"----"+count++;
        System.out.println(Thread.currentThread().getName()+"--生产---"+this.name);
        flag = true;
        condition_con.signal();
    }
    
    public void out()throws InterruptedException {
        lock.lock();
        while(!flag) {
            try {
                condition_con.await();
            } finally {
                lock.unlock();
            }
        }
        System.out.println(Thread.currentThread().getName()+"------消费-----"+name);
        flag = false;
        condition_pro.signal();
    }
}

class Product implements Runnable {
    private Resource rce;
    Product(Resource rce) {
        this.rce = rce;
    }
    
    public void run() {
        while(true)
            try{rce.set("商品号");}catch(InterruptedException e){}
    }
}

class Consumer implements Runnable {
    private Resource rce;
    Consumer(Resource rce) {
        this.rce = rce;
    }
    
    public void run() {
        while(true)
            try{rce.out();}catch(InterruptedException e){}
    }
}

class InOut {
    public static void main(String[] args) {
        Resource rce = new Resource();
        
        new Thread(new Product(rce)).start();
        new Thread(new Product(rce)).start();
        new Thread(new Product(rce)).start();
        
        new Thread(new Consumer(rce)).start();
        new Thread(new Consumer(rce)).start();
        new Thread(new Consumer(rce)).start();
        
    }
}*/
