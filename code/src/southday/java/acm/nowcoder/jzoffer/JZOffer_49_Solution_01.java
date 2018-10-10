package southday.java.acm.nowcoder.jzoffer;

/* 把字符串转换成整数 
题目描述
将一个字符串转换成一个整数(实现Integer.valueOf(string)的功能，但是string不符合数字要求时返回0)，要求不能使用字符串转换整数的库函数。
 数值为0或者字符串不是一个合法的数值则返回0。
输入描述:
输入一个字符串,包括数字字母符号,可以为空
输出描述:
如果是合法的数值表达则返回该数字，否则返回0

示例1
输入
+2147483647
    1a33
输出
2147483647
    0
*/

public class JZOffer_49_Solution_01 {

    public int StrToInt(String str) {
        if ("0".equals(str) || !isLegal(str)) return 0;
        char[] chs = str.toCharArray();
        int over = 0, sum = 0;
        if (chs[0] == '+' || chs[0] == '-') over = 1;
        for (int i = chs.length-1, j = 1; i >= over; i--, j *= 10) {
            sum += ((int)chs[i] - 48) * j;
        }
        return chs[0] == '-' ? -sum : sum;
    }

    private boolean isLegal(String str) {
        if (str == null || "".equals(str.trim())) return false;
        char[] chs = str.toCharArray();
        if ('+' != chs[0] && '-' != chs[0] && ('0' > chs[0] || chs[0] > '9')) return false;
        for (int i = 1; i < chs.length; i++) {
            if (chs[i] < '0' || chs[i] > '9') return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        JZOffer_49_Solution_01 o = new JZOffer_49_Solution_01();
        System.out.println(o.StrToInt("+s234234324"));
        System.out.println((int)'0');
    }
}
