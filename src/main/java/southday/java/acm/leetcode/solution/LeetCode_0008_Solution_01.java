package southday.java.acm.leetcode.solution;

/**
 * 8. String to Integer (atoi) https://leetcode.com/problems/string-to-integer-atoi/
 * @author southday
 * @date 2019/3/12
 */
public class LeetCode_0008_Solution_01 {
    public int myAtoi(String str) {
        if (str == null)
            return 0;
        char s[] = str.toCharArray();
        int i = 0;
        for (; i < s.length && s[i] == ' '; i++);
        if (i == s.length)
            return 0;
        int sign = 1;
        if (s[i] == '-') {
            sign = -1;
            i++;
        } else if (s[i] == '+') {
            i++;
        }
        int val = 0;
        for (int nval = 0, c = 0; i < s.length; i++, val = nval) {
            c = s[i] - '0';
            if (c < 0 || c > 9)
                break;
            nval = val * 10 + c;
            if (nval / 10 != val)
                return sign == 1 ? (1 << 31) - 1 : -(1 << 31);
        }
        return val * sign;
    }

    public static void main(String[] args) {
        LeetCode_0008_Solution_01 o = new LeetCode_0008_Solution_01();
        int num = 0;
        num = o.myAtoi("42");
        System.out.println(num);
        num = o.myAtoi("   -42");
        System.out.println(num);
        num = o.myAtoi("4193 with words");
        System.out.println(num);
        num = o.myAtoi("words and 987");
        System.out.println(num);
        num = o.myAtoi("-91283472332");
        System.out.println(num);
        num = o.myAtoi("+");
        System.out.println(num);
        num = o.myAtoi("-");
        System.out.println(num);
        num = o.myAtoi("+-2");
        System.out.println(num);
        num = o.myAtoi("-6147483648");
        System.out.println(num);
//        foo();
    }

    public static void foo() {
        int maxvDiv10 = ((1 << 31) - 1) / 10 + 1;
        int minvDiv10 = -(1 << 31) / 10 - 1;
        System.out.println((1 << 31) - 1);
        System.out.println(-(1 << 31));
        System.out.println(maxvDiv10 + ", " + minvDiv10);
        System.out.println(Math.floor(22.3));
        System.out.println(Math.floor(-22.8));
    }
}
