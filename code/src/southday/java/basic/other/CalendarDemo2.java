package southday.java.basic.other;

import java.util.Calendar;

public class CalendarDemo2 {
    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        
        // 【1】 设置时间
        c.set(2015, 8, 15); // 2015年，9月(月份从0开始计),15日
        PrintCalendar(c);
        
        // 【2】给定 时间字段 设置增量
        c.add(Calendar.DAY_OF_MONTH, 19); // 由于时间是连续的，所以这15+19=34 % 30 = 4 (9月有30天),月份就变成10月
        PrintCalendar(c); // 2015年10月4日 星期一
        c.add(Calendar.DAY_OF_WEEK, -5); // 星期1 - 5 = 星期3, 10月4日 - 5 = 9月29日
        PrintCalendar(c); // 2015年9月29日 星期三
        
        // 【5】返回任意一年二月的天数
        sop(GetDayNumberOfFeb(2010)); // 输出2010年二月的天数
        sop(GetDayNumberOfFeb(2000)); // 输出2000年二月的天数
        sop(GetDayNumberOfFeb(2012)); // 输出2012年二月的天数
        sop(GetDayNumberOfFeb(2015)); // 输出2015年二月的天数
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
    
    // 返回 任意一年的 二月 的天数  28/29
    public static int GetDayNumberOfFeb(int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, 2, 1); // year 年  3 月 1 日
        c.add(Calendar.DAY_OF_MONTH, -1); // 3月1日 -1 = 2月 28日/29日
        return c.get(Calendar.DAY_OF_MONTH);
    }
    
    public static void sop(Object obj) {
        System.out.println(obj);
    }
}
