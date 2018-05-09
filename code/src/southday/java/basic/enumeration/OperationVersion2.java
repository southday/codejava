package southday.java.basic.enumeration;

/**
 * <code>Operation</code>枚举类型实现(第二版本)<p>
 * 学自：Effective Java - chapter 6
 * @author southday
 * @date 2018年5月7日
 * @see OperationVersion1
 */
public enum OperationVersion2 {
    PLUS("+") {
        double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        double apply(double x, double y) {
            return x / y;
        }
    };
    
    private final String symbol;
    
    OperationVersion2(String symbol) {
        this.symbol = symbol;
    }
    
    abstract double apply(double x, double y);
    
    @Override
    public String toString() {
        return symbol;
    }
    
    /* 枚举中的switch语句适合于给外部的枚举类型增加特定于常量的行为
     * 例如：假设OperationVersion2枚举不受你的控制，你希望它有一个实例方法来返回每个运算的反运算
     */
    public static OperationVersion2 inverse(OperationVersion2 op) {
        switch (op) {
            case PLUS: return OperationVersion2.MINUS;
            case MINUS: return OperationVersion2.PLUS;
            case TIMES: return OperationVersion2.DIVIDE;
            case DIVIDE: return OperationVersion2.TIMES;
            default: throw new AssertionError("Unknown op: " + op);
        }
    }
    
    
    /* 说明：
     * 在枚举类型中声明一个抽象的apply方法，并在特定于常量的类主体中，用具体的方法覆盖每个常量的抽象apply方法，
     * 这种方法被称作：特定于常量的方法实现
     */
    public static void main(String[] args) {
        double x = 2.0, y = 4.0;
        for (OperationVersion2 op : OperationVersion2.values()) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }
}
