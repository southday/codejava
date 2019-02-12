package southday.java.thinkinginjava.c20.s03;

/**
 * Author southday
 * Date   2019/2/11
 */
@ExtractInterface("IMultiplier")
public class Muliplier {
    public int mulitply(int x, int y) {
        int total = 0;
        for (int i = 0; i < x; i++)
            total = add(total, y);
        return total;
    }

    private int add(int x, int y) {
        return x + y;
    }

    public static void main(String[] args) {
        Muliplier m = new Muliplier();
        System.out.println("11 * 6 = " + m.mulitply(11, 16));
    }
}
/*
在当前目录下执行命令：> apt -s . -nocompile -factory InterfaceExtratorProcessorFactory.java Multiplier.java
通过apt工具，使用InterfaceExtractorProcessorFactory来创建IMultiplier.java，最后结果如下：
package southdya.java.thinkingkinjava.c20.s03;
public interface IMutiplier {
    public int multiply(int x, int y);
}
 */
