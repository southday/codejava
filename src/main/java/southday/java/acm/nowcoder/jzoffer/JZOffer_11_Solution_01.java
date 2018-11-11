package southday.java.acm.nowcoder.jzoffer;

/* 二进制中1的个数 
输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。 
if n < 0
以无符号的二进制看待：
B(n) + B(n补) = 2^32
*/

public class JZOffer_11_Solution_01 {
    
    public static void main(String[] args) {
        System.out.println(NumberOf1(-5));
    }
    
    public static  int NumberOf1(int n) {
        if (n == 0) return 0;
        long m = n > 0 ? (long)n : ((long)1 << 32) + n;
        int cnt = 0;
        while (m != 0) {
            if (m % 2 == 1) {
                cnt++;
            }
            m /= 2;
        }
        return cnt;
    }
}
