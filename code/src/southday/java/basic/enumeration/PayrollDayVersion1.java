package southday.java.basic.enumeration;

/**
 * <code>PayrollDay</code>枚举类型实现(第一版本)<p>
 * 学自：Effective Java - chapter 6
 * @author southday
 * @date 2018年5月7日
 * @see PayrollDayVersion2
 */
public enum PayrollDayVersion1 {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    
    private static final int HOURS_PER_SHIFT = 8;
    
    double pay(double hoursWorked, double payRate) {
        double basePay = hoursWorked * payRate;
        double overtimePay; // 计算超时加班费
        switch (this) {
            case SATURDAY: case SUNDAY:
                overtimePay = hoursWorked * payRate / 2;
            default: // Weekdays
                overtimePay = hoursWorked <= HOURS_PER_SHIFT ?
                        0 : (hoursWorked - HOURS_PER_SHIFT) * payRate / 2;
            break;
        }
        return basePay + overtimePay;
    }
    
    /* 说明：
     * 1.特定于常量的方法实现有个美中不足的地方，它们使得在枚举常量中共享代码变得更加困难，
     * 如：OperationVersion2中的apply(double x, double y)实现简单，
     *   而对于本例，如果每个枚举常量都去实现自己的pay，会产生相当数量的样板代码，降低可读性，
     *   而且代码中（周1-周5）的计算方法是一致的，（周6，7）的方法是一致的，因此会产生大量冗余代码
     * 
     * 2.从维护角度来看，该实现同样存在OperationVerson1中所描述的问题
     * 
     * 解决方法，参看 PayrollDayVersion2.java
     */
}
