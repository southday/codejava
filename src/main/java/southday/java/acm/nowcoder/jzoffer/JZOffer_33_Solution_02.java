package southday.java.acm.nowcoder.jzoffer;

/* 丑数 
把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含质因子7。 
习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。

根据丑数的定义，丑数 p = 2^x * 3^y * 5^z
x, y, z分别从0开始
*/

public class JZOffer_33_Solution_02 {
   
    public int GetUglyNumber_Solution(int index) {
        if (index <= 0) return 0;
        int[] idx = new int[3];
        idx[0] = idx[1] = idx[0] = 0;
        int[] result = new int[index];
        result[0] = 1;
        int cnt = 1, temp = 0;
        while (cnt < index) {
            temp = min(result[idx[0]]*2, result[idx[1]]*3, result[idx[2]]*5);
            // 防止相同
            /* (2, 3, 5)
             * (4, 3, 5)
             * (4, 6, 5)
             * (6, 6, 5)
             * (6, 6, 10)
             * (8, 9, 10)
             */
            if (temp == result[idx[0]]*2) idx[0]++;
            if (temp == result[idx[1]]*3) idx[1]++;
            if (temp == result[idx[2]]*5) idx[2]++;
            result[cnt++] = temp;
        }
        return result[cnt-1];
    }
    
    private int min(int a, int b, int c) {
        if (a < b) {
            return a < c ? a : c;
        } else {
            return b < c ? b : c;
        }
    }

    public static void main(String[] args) {
        JZOffer_33_Solution_02 o = new JZOffer_33_Solution_02();
        System.out.println(o.GetUglyNumber_Solution(10));
    }
}
