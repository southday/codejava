package southday.java.thinkinginjava.c21.s02;

/**
 * P672
 * @author southday
 * @date 2019/4/6
 */
public class ExceptionThread implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }
}
