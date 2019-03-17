package southday.java.acm.leetcode.solution;

/**
 * 29. Divide Two Integers https://leetcode.com/problems/divide-two-integers/
 * @author southday
 * @date 2019/3/13
 */
public class LeetCode_0029_Solution_01 {
    public int divide(int dividend, int divisor) {
        if (dividend == 0)
            return 0;
        if (dividend == -(1 << 31) && divisor == -1)
            return (1 << 31) - 1;
//        int sign = ((dividend > 0 ? 1 : -1) + (divisor > 0 ? 1 : -1)) == 0 ? -1 : 1;
        int sign = (dividend > 0) ^ (divisor > 0) ? -1 : 1;

        if (dividend > 0)
            dividend = -dividend;
        if (divisor > 0)
            divisor = -divisor;
        int q = 0, d = divisor;
        for (int r = 1; dividend <= divisor;) {
            dividend -= d;
            q += r;
            d += divisor;
            r++;
            for (; d < divisor && dividend > d; d -= divisor, r--);
        }
        return sign == 1 ? q : -q;
    }

    public static void main(String[] args) {
        LeetCode_0029_Solution_01 o = new LeetCode_0029_Solution_01();
        System.out.println(o.divide(10, 3));
        System.out.println(o.divide(7, -3));
        System.out.println(o.divide(Integer.MIN_VALUE, -1));
        System.out.println(o.divide(Integer.MIN_VALUE, 1));
        System.out.println(o.divide(Integer.MIN_VALUE, Integer.MIN_VALUE));
        System.out.println(o.divide(Integer.MIN_VALUE, Integer.MAX_VALUE));
        System.out.println(o.divide(Integer.MAX_VALUE, 1));
        System.out.println(o.divide(Integer.MAX_VALUE, Integer.MIN_VALUE));
        System.out.println(o.divide(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }
}
