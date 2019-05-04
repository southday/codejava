package southday.java.acm.leetcode.solution;

/* 分治法
1) 取 mid = (left + right) >> 1
2) A[left..right]的最大连续子数组和可能存在以下3种情况：
    a) 在mid左侧：A[left..mid-1]，这时候递归求解 A[left..mid-1]
    b) 在mid右侧：A[mid+1..right]，这时候递归求解 A[mid+id..right]
    c) 包含mid，从两边散开，即：A[i..mid..j]，这时候需要求解以mid为中心，两边散开的最大连续子数组和
3) 同一层次的解为：max{(a), (b), (c)}
 */

/**
 * 53. Maximum Subarray https://leetcode.com/problems/maximum-subarray/
 * @author southday
 * @date 2019/5/4
 */
public class LeetCode_0053_Solution_02 {

    public int maxSubArray(int[] nums) {
        return maxSubArray(nums, 0, nums.length-1);
    }

    private int maxSubArray(int[] nums, int left, int right) {
        if (left >= right)
            return nums[left];
        int mid = (left + right) >> 1;
        int leftSum = maxSubArray(nums, left, mid-1);
        int rightSum = maxSubArray(nums, mid+1, right);
        int crossSum = crossMiddleMaxSubArray(nums, left, right);
        int maxv = leftSum > rightSum ? leftSum : rightSum;
        return maxv > crossSum ? maxv : crossSum;
    }

    private int crossMiddleMaxSubArray(int[] nums, int left, int right) {
        if (left >= right)
            return nums[left];
        int mid = (left + right) >> 1;
        int sum = 0, leftMaxSum = 0, rightMaxSum = 0;
        for (int i = mid-1; i >= left; i--) {
            sum += nums[i];
            if (sum > leftMaxSum)
                leftMaxSum = sum;
        }
        sum = 0;
        for (int i = mid+1; i <= right; i++) {
            sum += nums[i];
            if (sum > rightMaxSum)
                rightMaxSum = sum;
        }
        return leftMaxSum + rightMaxSum + nums[mid];
    }

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        LeetCode_0053_Solution_02 o = new LeetCode_0053_Solution_02();
        System.out.println(o.maxSubArray(nums));
    }
}
