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

    /* 对上面的 w[] 进行优化，将空间复杂度减小为 O(1)
     1) 发现我们使用的w[]只有 w[i]，w[i-1]
     2) 而w[i]的求解也只和 w[i-1]（即w[i]的前一个状态）、nums[i]有关
     3) 并且i是递增的，也就是说我们其实只需要设置一个变量就可以替代w[]
     */
    public int maxSubArrayOptimize(int[] nums) {
        int maxEnd = nums[0]; // 以 nums[i] 结尾的最大子数组和
        int maxAll = nums[0]; // nums[0..i] 的最大子数组和
        for (int i = 1; i < nums.length; i++) {
            maxEnd = maxEnd + nums[i] >= nums[i] ? maxEnd + nums[i] : nums[i];
            if (maxAll < maxEnd)
                maxAll = maxEnd;
        }
        return maxAll;
    }

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        LeetCode_0053_Solution_01 o = new LeetCode_0053_Solution_01();
        System.out.println(o.maxSubArray(nums));
        System.out.println(o.maxSubArrayOptimize(nums));
    }
}
