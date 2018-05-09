package southday.java.basic.concurrent.bxd;
/*
* 举一个售票的例子，采用两个线程。售100张票；
* 来讨论同步函数的锁是this 而不是像同步代码块中的obj对象
* 
* 如果同步函数被静态修饰后，使用的锁是什么？
*     静态进内存时，内存中没有本类对象，但一定有该类对应的自节码文件对象。
*     类名.class   该对象类型是Class
* 静态的同步方法，使用的锁是该方法所在类的字节码文件对象：  类名.class
*/

class SellTick implements Runnable {
    private static int tick = 100;
    //Object obj = new Object();
    boolean falg = true;
    
    public void run() {
        if(falg) {
            while(true) {
              //synchronized(this)  //使用this对象作为锁，来实现与非静态同步函数的同步
              synchronized(SellTick.class) { //类也是一个对象，当xxx类加载到堆中，JAVA虚拟机经过判断后，会加载Class类的对象，
                                            //即Class 名称 = SellTick.class  创建Class类的方法有3种，但创建的都是同一个类对象
                   if(tick>0) {  //或者 SellTick s = new SellTick(); →  Class = s.getClass();
                     try{Thread.sleep(10);}catch(Exception e){}
                     System.out.println(Thread.currentThread().getName()+"--code--: "+tick--);
                   }
             }
            }//while
        }//if
        else
            while(true)
                show();
    }
    public static synchronized void show() { //锁是  SellTick.class
    //public synchronized void show()    //锁是 this
        if(tick>0) {
           try{Thread.sleep(10);}catch(Exception e){} 
           System.out.println(Thread.currentThread().getName()+"--sell--: "+tick--);
        }
    }
}

class SynFucation {
    public static void main(String[] args) {
        SellTick s = new SellTick();
        Thread t1 = new Thread(s);
        Thread t2 = new Thread(s);
        
        t1.start();
        try{Thread.sleep(10);}catch(Exception e){}
        s.falg = false;
        t2.start();
    }
}

/*
第一大点：
try{Thread.sleep(10);}catch(Exception e){}
作用是：（1）让主线程停10毫秒；
                （2）保证t1线程进入到while(true)中；
若没有该语句，则可能出现：
主线程在执行到    t1.start();后  还没等t1线程进入到while(true)中的同步代码块，主线程就瞬间将执行权夺回来，
然后将剩下的语句执行完，  这样 falg → false。  
因此，当t1拥有执行权后，开始进入if(falg)判断，发现是false,则进入到else中的show(),导致不会出现--code--输出语句，即没有进入同步代码块。

第二大点：
使用同步的前提是：：：：必须多个线程使用同一个锁；
上面的  （1）同步代码块使用的锁是  obj对象锁   ——synchronized (obj)
              （2）同步函数使用的锁是this（本类对象）锁；即上面调用show(),其实是this.show();
所以两个同步方法的锁不同，这样就可能出现：不同的线程出售相同的票；
                                                                                     或者出现0票等；
为了使两个线程同步，则需要改变同步代码块中的锁，将其改为→→→→→→this 锁————即：synchronized(this)

第三大点：
若Thread.sleep(time)中的time过短，则也可能出现---code--- 0情况：
原因：   （1）当t1线程进入判断tick = 1 > 0 时，还没等输出tick = 1，它的执行权就被t2抢了，则t1被冻结了；
               （2）t2线程抢到执行权后，一不小心就输出了tick = 1这个情况，然后进行运行tick = tick - 1;
               （3）当tick=0时，t1线程抢到了执行权，然后开始输出tick，此时，输出的tick就变成了0；
*/











