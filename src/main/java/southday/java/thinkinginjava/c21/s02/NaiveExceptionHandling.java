package southday.java.thinkinginjava.c21.s02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * P672
 * @author southday
 * @date 2019/4/6
 */
public class NaiveExceptionHandling {
    public static void main(String[] args) {
        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread());
        } catch (RuntimeException e) {
            // 下面的语句将不会被执行
            System.out.println("Exception has been handled");
        }
    }
}
