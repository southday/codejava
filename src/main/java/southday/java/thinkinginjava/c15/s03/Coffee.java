package southday.java.thinkinginjava.c15.s03;

/**
 * 泛型接口-Coffee
 * @author southday
 * @date 2018年10月25日
 */
public class Coffee {
    private static long counter = 0;
    private final long id = counter++;
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }
}
