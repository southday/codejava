package southday.java.acm.other.solution;

/* 使用动态规范化方法求解
1）设子数组序列Si = {x1, x2, ..., xi}
2）令w[i]表示以xi结尾的最大连续子数组的和，则有：
w[i] = 0, i=0
     = max(w[i-1]+xi, xi), i>0
3）用maxv记录在遍历求解w[i]过程中连续子数组和的最大值
 */

/** 最大子数组和 - 动态规划算法
 * Author southday
 * Date   2019/1/10
 */
public class MaxSubArraySumDP {

    public int maxSubArray(int A[]) {
        int[] w = new int[A.length];
        int maxv = A[0];
        w[0] = A[0];
        for (int i = 1; i < w.length; i++) {
            w[i] = w[i - 1] + A[i] >= A[i] ? w[i - 1] + A[i] : A[i];
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
    public int maxSubArrayOptimize(int[] A) {
        int maxEnd = A[0]; // 以 A[i] 结尾的最大子数组和
        int maxAll = A[0]; // A[0..i] 的最大子数组和
        for (int i = 1; i < A.length; i++) {
            maxEnd = maxEnd + A[i] >= A[i] ? maxEnd + A[i] : A[i];
            if (maxAll < maxEnd)
                maxAll = maxEnd;
        }
        return maxAll;
    }

    public static void main(String[] args) {
        MaxSubArraySumDP o = new MaxSubArraySumDP();
        int A[] = {0, 13, -3, -25, 20, 3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        System.out.println("sum = " + o.maxSubArray(A));
        System.out.println("sum = " + o.maxSubArrayOptimize(A));
    }
}
