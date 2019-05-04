package southday.java.acm.leetcode.solution;

/* 动态规划法
1）设子数组序列Si = {x1, x2, ..., xi}
2）令w[i]表示以xi结尾的最大连续子数组的和，则有：
w[i] = 0, i=0
     = max(w[i-1]+xi, xi), i>0
3）用maxv记录在遍历求解w[i]过程中连续子数组和的最大值
 */

/**
 * 53. Maximum Subarray https://leetcode.com/problems/maximum-subarray/
 * @author southday
 * @date 2019/5/4
 */
public class LeetCode_0053_Solution_01 {

    public int maxSubArray(int[] nums) {
        int[] w = new int[nums.length];
        int maxv = nums[0];
        w[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            w[i] = w[i-1] + nums[i] >= nums[i] ? w[i-1] + nums[i] : nums[i];
            if (maxv < w[i])
                maxv = w[i];
        }
        return maxv;
    }

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        LeetCode_0053_Solution_01 o = new LeetCode_0053_Solution_01();
        System.out.println(o.maxSubArray(nums));
    }
}
