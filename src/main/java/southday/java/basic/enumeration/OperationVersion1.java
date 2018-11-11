package southday.java.basic.enumeration;

/**
 * <code>Operation</code>枚举类实现(第一版本)<p>
 * 学自：Effective Java - chapter 6
 * @author southday
 * @date 2018年5月7日
 * @see OperationVersion2
 */
public enum OperationVersion1 {
    PLUS, MINUS, TIMES, DIVIDE;
    
    double apply(double x, double y) {
        switch (this) {
            case PLUS: return x + y;
            case MINUS: return x - y;
            case TIMES: return x * y;
            case DIVIDE: return x / y;
        }
        throw new AssertionError("Unknown op: " + this);
    }
    
    /* 说明：
     * 1.如果没有throw语句，它将不能进行编译
     * 2.如果添加了新的枚举常量，却忘记给switch添加相应的条件，枚举仍然可编译，但你无法运行新的运算
     * 
     * 有一中更好的方法可以将不同的行为与每个枚举常量关联起来，参看 OperationVersion2.java
     */
    public static void main(String[] args) {
        System.out.println(OperationVersion1.PLUS.apply(2.0, 3.0));
    }
}
