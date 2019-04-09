package southday.java.thinkinginjava.c21.s02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * P656 使用Executor来管理Thread对象<br/>
 * Executor 在客户端和任务执行之间提供了一个间接层
 * @author southday
 * @date 2019/4/5
 */
public class CachedThreadPool {

    public static void main(String[] args) {
//        ExecutorService exec = Executors.newCachedThreadPool();
        ExecutorService exec = Executors.newFixedThreadPool(3);
//        ExecutorService exec = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++)
            exec.execute(new LiffOff());
        exec.shutdown();
    }
}
