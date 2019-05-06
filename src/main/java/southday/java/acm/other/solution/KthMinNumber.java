package southday.java.acm.other.solution;

/*
题目描述：给定一个数组 arr[] = {1, 8, 4, 3, 19, 6, 9}，找出数组中第k小的数；

通常的解题思路都是O(NlgN)排个序，然后取第k个元素；
但是还有一种更优的方法，叫做“剪枝法”，其借鉴快速排序的原理来选择下一次要处理掉段，而将不必要的段剪掉；
过程如下：
1) 对于数组 arr[]，选择 q = arr.length-1; arr[q] 作为基准数；
2) 使用快速排序的 r = partition(arr, p, q) 来将数组划分为 3 部分：
    [------ A < arr[q] -------] arr[q] [--------- B > arr[q] ----------]
3) a) 若 r - p + 1 == k，说明 arr[q] 就是第k小的数；
   b) 若 r - p + 1 > k，说明第k小的数应该在 arr[q] 的左边，那么我们继续在范围[p, r-1]内搜索第k小的数；
   c) 若 r - p + 1 < k，说明第k小的数在 arr[q] 的右边，那么我们继续在范围[r+1, q]内搜索第 k-(r-p+1) 小的数；
4) 由于每次选择都会剪掉不必要的部分，所以效率会更高；
 */

import java.util.Arrays;

/**
 * 找出数组中第k小的数
 * @author southday
 * @date 2019/5/6
 */
public class KthMinNumber {

    public static int kthMin(int[] arr, int k) {
        if (k <= 0 || k > arr.length)
            throw new IllegalArgumentException("k = " + k + ", arr.length = " + arr.length);
        for (int p = 0, q = arr.length-1; p <= q; ) {
            int r = partition(arr, p, q);
            if (r-p+1 == k) {
                return arr[r];
            } else if (r-p+1 > k) {
                q = r - 1;
            } else {
                k -= (r-p+1);
                p = r + 1;
            }
        }
        throw new RuntimeException("error");
    }

    private static int partition(int[] arr, int p, int q) {
        if (p >= q)
            return p;
        int i = p - 1, j = p;
        int temp;
        for (; j < q; j++) {
            if (arr[j] < arr[q]) {
                temp = arr[j];
                arr[j] = arr[++i];
                arr[i] = temp;
            }
        }
        temp = arr[q];
        arr[q] = arr[++i];
        arr[i] = temp;
        return i;
    }

    public static void main(String[] args) {
        int[] arr = {1, 8, 4, 3, 19, 6, 9};
        int[] cpy = arr.clone();
        Arrays.sort(cpy);
        int k = 3;
        System.out.println(Arrays.toString(cpy));
        System.out.println("kthMin(arr, k): " + KthMinNumber.kthMin(arr, k));
        System.out.println("cpy[k-1]: " + cpy[k-1]);
    }
}
