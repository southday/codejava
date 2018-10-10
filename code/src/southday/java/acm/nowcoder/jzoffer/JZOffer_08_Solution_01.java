package southday.java.acm.nowcoder.jzoffer;

/* 跳台阶 
一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
通过找规律，可以发现，结果为斐波那契数列
*/

public class JZOffer_08_Solution_01 {
    
    public int JumpFloor(int target) {
        if (target == 0) return 0;
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
