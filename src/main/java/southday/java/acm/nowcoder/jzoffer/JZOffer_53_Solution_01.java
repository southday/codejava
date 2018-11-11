package southday.java.acm.nowcoder.jzoffer;
/* 表示数值的字符串 
请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。

// 感觉我直接调用现有的转换函数，有点投机取巧，还是要自己写写转换
*/

public class JZOffer_53_Solution_01 {
    
    public boolean isNumeric(char[] str) {
        String s = String.valueOf(str);
        try {
            @SuppressWarnings("unused")
            double d = Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static void main(String[] args) {
        JZOffer_53_Solution_01 o = new JZOffer_53_Solution_01();
        char[] str = "1a3.14".toCharArray();
        System.out.println(o.isNumeric(str));
    }
}
