package southday.java.thinkinginjava.c21.s07;

/**
 * P734 创建代价高昂的对象类型
 * @author southday
 * @date 2019/4/9
 */
public class Fat {
    private volatile double d; // 防止编译优化
    private static int counter = 0;
    private final int id = counter++;
    public Fat() {
        // Expensive, interruptible operation
        for (int i = 1; i < 10000; i++)
            d += (Math.PI + Math.E) / (double)i;
    }
    public void operation() {
        System.out.println(this);
    }
    public String toString() {
        return "Fat id: " + id;
    }
}
