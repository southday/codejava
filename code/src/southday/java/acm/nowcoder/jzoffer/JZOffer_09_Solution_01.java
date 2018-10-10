package southday.java.acm.nowcoder.jzoffer;

/* 变态跳台阶 
一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。

规律：
target:  0   1   2   3   4   5  ...
n:       0   1   2   4   8   16 ... 
*/

public class JZOffer_09_Solution_01 {

    public int JumpFloorII(int target) {
        if (target <= 0) return 0;
        return 1 << (target - 1);
    }
}
