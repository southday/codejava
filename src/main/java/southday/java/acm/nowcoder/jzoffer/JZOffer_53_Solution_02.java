package southday.java.acm.nowcoder.jzoffer;

/* 表示数值的字符串 
请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。

---
首先要搞清楚 java中浮点数的表示规则
使用正则表达式 [eE]表示10为指数的幂
*/

public class JZOffer_53_Solution_02 {
    public boolean isNumeric(char[] str) {
        String s = String.valueOf(str);
//        return s.matches("[\\+-]?[0-9]+(\\.[0-9]+)?([eE][\\+-]?[0-9]+)?");
        // -.123也是合法数值，所以[0-9]*而不是[0-9]+
        return s.matches("[\\+-]?[0-9]*(\\.[0-9]*)?([eE][\\+-]?[0-9]+)?");
    }
    
    public static void main(String[] args) {
        JZOffer_53_Solution_02 o = new JZOffer_53_Solution_02();
        char[] str = "1E+".toCharArray();
        System.out.println(o.isNumeric(str));
    }
}
