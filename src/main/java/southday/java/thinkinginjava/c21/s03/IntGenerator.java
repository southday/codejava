package southday.java.thinkinginjava.c21.s03;

/**
 * P674
 * @author southday
 * @date 2019/4/6
 */
public abstract class IntGenerator {
    private volatile boolean canceled = false;
    public abstract int next();

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceld() {
        return canceled;
    }
}
