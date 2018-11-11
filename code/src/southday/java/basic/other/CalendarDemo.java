package southday.java.basic.other;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarDemo {
    public static void main(String[] args) {
        Date d = new Date();
        sop(d);
        // 【1】只想获取年份，可以使用SimpleDateFormat，如下
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(d); // 获取到的year 是字符串，如果相对year进行加减操作还需要转换为int
        sop(year);

        // 【2】 使用 Calendar 类
        Calendar c = Calendar.getInstance();
        sop(c); // 会列出很多信息，用单独的 字段 来表示，比如：YEAR=2015,MONTH=8,WEEK_OF_YEAR=38
        // 而我们只需要它的年，那我们就可以这么写：
        sop(c.get(Calendar.YEAR) + "年");
        // 与Runtime 中exec方法的到的 Process
        // 引用一样，这里的Calendar.getInstance()得到的也只是Calendar的实现类
        sop(c.getClass().getName()); // java.util.GregorianCalendar

        // 【3】运行这段代码的当前时间：2015年9月15日 14:52:37 星期2
        sop(c.get(Calendar.YEAR) + "年"); // 2015年
        sop(c.get(Calendar.MONTH) + "月"); // 8月（计算机是从0开始计算月的，所以这里获得到月份后还需要+1）
        sop(c.get(Calendar.DAY_OF_MONTH) + "日"); // 15日
        sop("星期" + c.get(Calendar.DAY_OF_WEEK)); // 星期3（3表示这周的第三天，而我们一周是从周日开始计时的，所以一周的第3天是星期2）

        // 【4】可以使用***查表法***来转一步进而获取月份和星期
        PrintCalendar(c);
        
    }

    public static void PrintCalendar(Calendar c) {
        // 【4】可以使用***查表法***来转一步进而获取月份和星期
        String[] month = { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
                "九月", "十月", "十一月", "十二月" };
        String[] week = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        sop("------------ 查表法 ----------------");
        int index_month = c.get(Calendar.MONTH);
        int index_week = c.get(Calendar.DAY_OF_WEEK);
        sop(c.get(Calendar.YEAR) + "年");
        sop(month[index_month]);
        sop(c.get(Calendar.DAY_OF_MONTH) + "日");
        sop(week[index_week]);
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
