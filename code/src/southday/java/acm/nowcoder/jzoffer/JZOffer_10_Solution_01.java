package southday.java.acm.nowcoder.jzoffer;

/* 矩形覆盖 
我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
规律：
target: 0   1   2   3   4   5
n:      0   1   2   3   5   8
类似斐波那契数列
*/

public class JZOffer_10_Solution_01 {
    
    public int RectCover(int target) {
        if (target <= 0) return 0;
        if (target == 1) return 1;
        if (target == 2) return 2;
        int n1 = 1, n2 = 2, n3 = 0;
        for (int i = 3; i <= target; i++) {
            n3 = n1 + n2;
            n1 = n2;
            n2 = n3;
        }
        return n3;
    }
}
