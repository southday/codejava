package southday.java.basic.concurrent.jcip.c08.fac;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 程序清单 8-7 定制Thread基类 <p>
 * 设置自定义的<code>UncaughtExceptionHandler</code>向<code>Logger</code>中写入信息，
 * 维护一些统计信息（包括有多少个线程被创建和销毁），以及在线程被创建或者终止时把调试信息写入日志
 * @author southday
 * @date 2018年4月23日
 */
public class MyAppThread extends Thread {
    public static final String DEFAULT_NAME = "MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger log = Logger.getAnonymousLogger();
    
    public MyAppThread(Runnable r) {
        this(r, DEFAULT_NAME);
    }

    public MyAppThread(Runnable r, String name) {
        super(r, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                log.log(Level.SEVERE, "UNCAUGHT in thread " + t.getName(), e);
            }
        });
    }
    
    public void run() {
        // 复制debug标志以确保一致的值
        boolean debug = debugLifecycle;
        if (debug) {
            log.log(Level.FINE, "Created " + getName());
        }
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) {
                log.log(Level.FINE, "Exiting " + getName());
            }
        }
    }
    
    public static int getThreadsCreated() {
        return created.get();
    }
    
    public static int getThreadsAlive() {
        return alive.get();
    }
    
    public static boolean getDebug() {
        return debugLifecycle;
    }
    
    public static void setDebug(boolean b) {
        debugLifecycle = b;
    }
}
