package southday.java.basic.concurrent.bxd;
/*
 * 进程：是一个正在执行中的程序，每一个进程都有一个执行顺序，该顺序是一个执行路径，或者叫一个控制单元
 * 线程：就是进程中一个独立的控制单元，线程在控制着进程的执行
 * 一个进程中至少有一个线程
 * 
 * JAVA VM启动时会有一个进程java.exe，该进程中至少有一个线程负责java程序的执行
 * 而且这个线程运行的代码存在于main方法中，该线程称之为“主线程”
 * 
 * 扩展：其实更细节的说明jvm，jvm启动不只一个线程，还有负责垃圾回收机制的线程
 */

/*
 * 如何在代码中自定义一个线程呢？
 * 第一种方法：
     * (1)定义类继承Thread
     * (2)复写Thread中的run()方法
         * 目的：将自定义的代码存储在run中，让线程运行。
     * (3)调用线程的start()方法，该方法有两个作用：
         * ①启动线程，②调用run()方法
    发现运行结果每次都不同，因为多个线程都在获取CPU的执行权，CPU执行到谁谁就运行。
    明确一点：在某一时刻，只能有一个程序（线程）在运行【多核除外】，CPU在做着快速的切换以达到宏观上的同时运行。
    我们可以形象的把“多线程”的运行形容为在互相抢夺执行权，这就是多线程的一个特性——随机性【谁抢到谁执行，执行时间目前是CPU说了算】
    
    第二种方法：
        (1)定义类 实现 Runnable接口
        (2)实现Runnable接口中的run()方法
            将线程要运行的代码存在run方法中
        (3)通过Thread类建立线程对象Thread
        (4)将Runnable接口的子类对象作为实际参数传递给构造函数Thread(Runnable target)
            【问】：为什么要将Runnable的子类对象传递给Thread的构造函数？
            【答】：因为自定义的run方法所属的对象是Runnable接口的子类对象，所以要让线程去执行指定对象的run方法，
                 就必须明确该run方法所属的对象。
        (5)调用Thread类的start()方法开启线程并调用Runnable子类中的run()方法
    
    ------“实现方式” 与  “继承方式” 有什么区别呢？------
    （1）实现方式：避免了单继承的局限性，在定义线程时建议使用“实现方式”   【好处】
    （2）继承Thread类，线程代码存放在Thread子类的run方法中，
              实现Runnable接口，线程代码存放在接口子类的run方法中。
    
    
    Ⅰ.为什么要覆盖run()方法呢？
        Thread用于描述线程，该类就定义了一个功能用于存储线程要定义的代码，该存储功能就是run方法。
        也就是说Thread类中的run方法是用于存储线程要运行的代码。
    
    Ⅱ.原来线程都有自己默认的名称——Thread-编号【该编号从0开始】，如： Thread-0,Thread-1...通过getName()获取
        此外，Thread中有自定义名称的构造函数Thread(String name)，可以通过构造函数来对线程进行起名，或者通过setName()方法也可以。
        在获取名称时，可以通过静态方法cuurentThread()来获得当前线程对象，然后用该对象调用getName()方法即可。
 */

/* 第一种方法：
class Demo extends Thread {    //(1)定义类 继承Thread
    
    Demo(String name) {
        super(name);    //Thread中已经有Thread(String name)的构造函数，这里直接调用父类构造函数即可
    }
    
    public void run() {        //(2)复写run()方法
        for(int x=0; x<50; x++)
            System.out.println(Thread.currentThread().getName()+"----demo-----"+x);
    }
}*/

/* 第二种方法 */
class Demo implements Runnable {    //(1)创建类 实现 Runnable
    public void run() {            //(2)实现run()方法
        for(int x=0; x<50; x++)
            System.out.println(Thread.currentThread().getName()+"---demo---"+x);
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
//        Demo demo1 = new Demo("li");    //创建好了一个线程
//        Demo demo2 = new Demo("zhao");
//        
//        demo1.start();    //开启线程。如果没用调用start()方法，而是直接嗲用run()方法，这时demo线程是没有开启的。
//        demo2.start();
        
        Demo demo = new Demo();    //(1)    //(3)创建Runnable接口子类对象
        
        //Thread t = new Thread(new Demo());
        Thread t1 = new Thread(demo,"lixi");    //(4)将Runnable接口子类对象作为实际参数传入Thread构造函数，创建相关线程
        Thread t2 = new Thread(demo,"wangwu");    //可以通过Thread(Runnable target, String name)构造函数来进行命名
        
        t1.start();        //(5)调用线程的run方法
        t2.start();
        for(int x=0; x<50; x++)
            System.out.println(Thread.currentThread().getName()+"---main--------"+x);
    }
}
