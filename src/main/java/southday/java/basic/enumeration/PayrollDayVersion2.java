package southday.java.basic.enumeration;

/**
 * <code>PayrollDay</code>枚举类实现(第二版本)<p>
 * 学自：Effective Java - chapter 6
 * @author southday
 * @date 2018年5月7日
 * @see PayrollDayVersion1
 */
public enum PayrollDayVersion2 {
    MONDAY(PayType.WEEKDAY), TUESDAY(PayType.WEEKDAY),
    WEDNESDAY(PayType.WEEKDAY), THURSDAY(PayType.WEEKDAY),
    FRIDAY(PayType.WEEKDAY),
    SATURDAY(PayType.WEEKEND), SUNDAY(PayType.WEEKEND);
    
    private final PayType payType;
    
    PayrollDayVersion2(PayType payType) {
        this.payType = payType;
    }
    
    double pay(double hoursWorked, double payRate) {
        return payType.pay(hoursWorked, payRate);
    }
    
    // 策略枚举
    private enum PayType {
        WEEKDAY {
            double overtimePay(double hrs, double payRate) {
                return hrs <= HOURS_PER_SHIFT ? 0 : (hrs - HOURS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            double overtimePay(double hrs, double payRate) {
                return hrs * payRate / 2;
            }
        };
        
        private static final int HOURS_PER_SHIFT = 8;
        
        abstract double overtimePay(double hrs, double payRate);
        
        double pay(double hoursWorked, double payRate) {
            double basePay = hoursWorked * payRate;
            return basePay + overtimePay(hoursWorked, payRate);
        }
    }
    
    /* 说明：
     * 1.我们真正想要的是，每当添加一个新的枚举常量时，就强制选择一种加班报酬策略
     * 2.我们可以将加班工资计算移到一个私有的嵌套枚举中，将这个策略枚举的实例传到PayrollDayVersion2枚举的构造器中，
     *   之后PayrollDayVersion2枚举将加班工资计算委托给策略枚举
     * 
     */
}
