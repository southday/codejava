package southday.java.thinkinginjava.c21.s02;

/**
 * P654
 * @author southday
 * @date 2019/4/5
 */
public class LiffOff implements Runnable {
    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;
    public LiffOff() {}

    public LiffOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return new StringBuilder("#").append(id).append("(")
                .append(countDown > 0 ? countDown : "LiftOff!")
                .append(")，").toString();
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.print(status());
            Thread.yield();
        }
    }
}
