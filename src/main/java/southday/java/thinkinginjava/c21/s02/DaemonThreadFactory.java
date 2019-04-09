package southday.java.thinkinginjava.c21.s02;

import java.util.concurrent.ThreadFactory;

/**
 * P663
 * @author southday
 * @date 2019/4/6
 */
public class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
