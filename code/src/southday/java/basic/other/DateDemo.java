package southday.java.basic.other;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DateDemo {
    public static void main(String[] args) {
        Date d = new Date();
        System.out.println(" time => " + d); // 打印的时间看不懂
        
        // 将模式封装到SimpleDateFormat对象中
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日   E  HH时mm分ss秒");
        // 使用format方法让  模式sdf 格式化指定Date对象
        String time = sdf.format(d);
        System.out.println(" time => " + time);
        
    }
}
