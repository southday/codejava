package southday.java.acm.other.solution;

/* 分治策略求解 最大子数组问题 《算法导论》第3版 P39
1）给定数组A[low..high]，数组中央为mid，那么其最大子数组的A[i..j]只能为如下3种情况之一：
    a）完全位于子数组A[low..mid]，即：low <= i <= j <= mid；
    b）完全位于子数组A[mid+1..high]，即：mid < i <= j <= high；
    c）跨越了中点，即：low <= i <= mid < j <= high；
2）可以在O(n)的时间下求出跨域中点的最大子数组和 crossSum；
3）然后通过递归求解左子数组和右子数组的最大子数组和 leftSum rightSum，当问题规模为1（即：low == high）时，直接返回A[low]；
4）比较3者的大小，返回最大者（作为当前问题规模的最大子数组和）；

递归式 T(n) = 2T(n/2) + O(n)， n > 1
            = O(1)，           n = 1
总时间复杂度为：O(nlgn)
 */

/**
 * 最大子数组和 - 分治算法
 * Author southday
 * Date   2019/1/4
 */
public class MaxSubArraySumDAC {
    private class Node {
        public int sum;
        public int start;
        public int end;

        public Node(int sum, int start, int end) {
            this.sum = sum;
            this.start = start;
            this.end = end;
        }
    }

    public Node maxSubArraySum(int A[]) {
        return maxSubArraySum(A, 0, A.length-1);
    }

    private Node maxSubArraySum(int A[], int low, int high) {
        if (low >= high)
            return new Node(A[low], low, low);
        int mid = (low + high) >> 1;
        Node lsn = maxSubArraySum(A, low, mid); // left sum node
        Node rsn = maxSubArraySum(A, mid+1, high); // right sum node
        Node csn = maxCrossSubArraySum(A, low, high); // cross sum node
        if (lsn.sum > rsn.sum)
            return lsn.sum > csn.sum ? lsn : csn;
        else
            return rsn.sum > csn.sum ? rsn : csn;
    }

    // O(n)
    private Node maxCrossSubArraySum(int A[], int low, int high) {
        int leftSum = Integer.MIN_VALUE;
        int mid = (low + high) >> 1;
        int sum = 0, start = 0, end = 0;
        for (int i = mid; i >= low; i--) {
            sum += A[i];
            if (sum > leftSum) {
                leftSum = sum;
                start = i;
            }
        }
        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid+1; i <= high; i++) {
            sum += A[i];
            if (sum > rightSum) {
                rightSum = sum;
                end = i;
            }
        }
        return new Node(leftSum+rightSum, start, end);
    }

    public static void main(String[] args) {
        MaxSubArraySumDAC o = new MaxSubArraySumDAC();
        int A[] = {13, -3, -25, 20, 3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        Node rt = o.maxSubArraySum(A);
        System.out.println("sum = " + rt.sum + ", start = " + rt.start + ", end = " + rt.end);
    }

}
