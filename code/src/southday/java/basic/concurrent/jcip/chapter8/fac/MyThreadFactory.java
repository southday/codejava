package southday.java.basic.concurrent.jcip.chapter8.fac;

import java.util.concurrent.ThreadFactory;

/**
 * 程序清单 8-6 自定义的线程工厂<p>
 * 将一个特定于线程池的名字传递给<code>MyAppThread</code>的构造函数，从而可以在线程转储和错误日志信息中
 * 区分来自不同线程池的线程
 * @author southday
 * @date 2018年4月23日
 */
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }
    
    @Override
    public Thread newThread(Runnable r) {
        // TODO Auto-generated method stub
        return new MyAppThread(r, poolName);
    }

}
