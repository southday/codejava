package southday.java.acm.nowcoder.jzoffer;

/* 丑数 
把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含质因子7。 
习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。

我这个算法对于 n = 1500，效率太低，运行时间不满足！
*/

public class JZOffer_33_Solution_01 {
    static final int PRIME_NUM = 50000;
    static int [] primeTable = new int[PRIME_NUM];
    
    static {
        for (int i = 0, q = 7; i < PRIME_NUM; i++, q++) {
            while (!isPrime(q)) q++;
            primeTable[i] = q;
        }
    }
    
    static boolean isPrime(int n) {
        if (n <= 1) return false;
        int sqr = (int)Math.sqrt(n);
        for (int i = 2; i <= sqr; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isUglyNumber(int n) {
        boolean flag = (n % 2 == 0) || (n % 3 == 0) || (n % 5) == 0;
        if (flag == false) return false;
        for (int i = 0; i < PRIME_NUM; i++) {
            if (n % primeTable[i] == 0) {
                return false;
            }
        }
        return true;
    }
    
    public int GetUglyNumber_Solution(int index) {
        if (index <= 6) return index;
        index -= 6;
        int p = 7;
        for (int i = 0; i < index; p++) {
            if (isUglyNumber(p)) {
                i++;
            }
        }
        return --p;
    }

    public static void main(String[] args) {
        JZOffer_33_Solution_01 o = new JZOffer_33_Solution_01();
        System.out.println(o.GetUglyNumber_Solution(11));
    }
}
