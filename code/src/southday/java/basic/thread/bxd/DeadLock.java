package southday.java.basic.thread.bxd;
//写一个死锁程序
//例子：关于售票的死锁程序

class Chus implements Runnable
{
    private int tick = 100;
    Object obj = new Object();
    boolean flag = true;
    
    public void run() {
        if(flag==true) {
            while(true) {
              synchronized (obj)    //obj锁里面调用show()，
              {
                show();        //show()使用的是this锁，而show()里面也有使用obj锁的同步代码块
              }            //t1拿着obj锁，但没有this锁，所以调用show()方法无用
            }
        }
        else
            while(true)
                show();    //可能出现t2拿着this锁，但没有obj锁，进不去
    }
    
    public synchronized void show() { //this锁
       synchronized (obj) { //obj锁
          if(tick > 0) {
              try{Thread.sleep(10);}catch(Exception e){}
              System.out.println(Thread.currentThread().getName()+"---sell---: "+tick--);
          }
       }
    }
}

class DeadLock {
    public static void main(String[] args) {
        Chus s = new Chus();
        
        Thread t1 = new Thread(s);
        Thread t2 = new Thread(s);
        
        t1.start();
        try{Thread.sleep(10);}catch(Exception e){}
        s.flag = false;
        t2.start();
    }
}
/*
t1线程先执行，执行到synchronized (obj)时，拿到了OBJ锁，然后进入{show();}，拿到了this锁，
当t1执行一段时间（对于电脑很短）输出相关语句后，主线程苏醒，flag→false，t2线程被激活;
重点：死锁出现的原因：
        t1在执行完输出语句后，再次进入while(ture),然后通过synchronized (obj)拿到了OBJ锁，
 就在这时，    t2杀了过来（抢到了执行权），t2线程通过if(flag)然后进入else语句，调用show()方法，拿到了   同步函数的this锁，
         就在t2要继续前进，进入synchronized (obj)时，才发现，obj锁被t1拿去了，进不去了。无奈，t2只能等待，
 然后  t1抢到了执行权，就在t1要进入show()时，发现，this锁被t2拿去了，进不去了，无奈，t1只能等待；
     就这样，两个线程就这样一直等待，程序无法继续运行.....
 */

