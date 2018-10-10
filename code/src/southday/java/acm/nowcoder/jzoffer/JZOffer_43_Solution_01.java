package southday.java.acm.nowcoder.jzoffer;

/* 左旋转字符串 
题目描述
汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”,
要求输出循环左移3位后的结果，即“XYZdefabc”。是不是很简单？OK，搞定它！
*/

public class JZOffer_43_Solution_01 {
    public String LeftRotateString(String str,int n) {
        if (str == null || "".equals(str)) return "";
        if (n <= 0) return str;
        n %= str.length();
        return str.substring(n) + str.substring(0, n);
    }
    
    public static void main(String[] args) {
        JZOffer_43_Solution_01 o = new JZOffer_43_Solution_01();
        String str = "abcXYZdef";
        System.out.println(o.LeftRotateString(str, 3));
    }
}
