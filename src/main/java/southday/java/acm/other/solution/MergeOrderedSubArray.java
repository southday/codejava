package southday.java.acm.other.solution;

/*
题目描述：
数组 a[0, mid-1] 和 a[mid, n-1] 是各自有序的（非降序）。对数组 a[0, n-1]的两个子有序段进行合并，得到 a[0, n-1]整体有序；
要求空间复杂度为 O(1)

空间复杂度要求为 O(1)，那我们就不能用归并方法来解决了；
思路1：使用插入排序，将后半部分 a[mid, n-1] 中的元素一个一个插入到 a[0, mid-1] 中，但是这样的时间复杂度 O(n^2)
思路1中虽然达到了空间复杂度O(1)的要求，但是没有利用 子数组各自有序 的条件，所以不是最优的解法；

思路2：
1) 遍历 a[0, mid-1] 中的元素，找到第一个比 a[mid] 大的元素 a[i]，将二者交换；
2) 交换后的 a[mid] 可能会破坏 a[mid, n-1] 的有序性质，所以需要进行调整，这里借助插入排序的思想将 a[i] 插入到 a[mid, n-1] 中合适的位置；
3) 经过(2)的维护后，a[mid]始终是a[mid,n-1]中最小的元素，继续(1)的操作，从下一个位置(i+1)开始寻找第一个大于 a[mid] 的数...
4) 直到角标 i > mid-1 时结束算法；

引申：对于下面的问题，都可以使用类似的解法来解决；
1) 如果数组中有两个子有序段都按降序排列，将2个有序段合并；
2) 如果其中一个子有序段按升序排列，另一个子有序段按降序排列，可以首先对其中一个子有序段进行逆序，然后采取上面介绍的方法解决；

 */

import java.util.Arrays;

/**
 * 对数组的两个子有序段进行合并
 * @author southday
 * @date 2019/5/6
 */
public class MergeOrderedSubArray {

    public static void merge(int[] arr, int mid) {
        for (int i = 0; i < mid; i++) {
            if (arr[i] > arr[mid]) {
                int temp = arr[mid];
                arr[mid] = arr[i];
                arr[i] = temp;
                adjustToSorted(arr, mid);
            }
        }
    }

    private static void adjustToSorted(int[] arr, int mid) {
        int i = mid + 1, key = arr[mid];
        for (; i < arr.length && key > arr[i]; i++) {
            int temp = arr[i-1];
            arr[i-1] = arr[i];
            arr[i] = temp;
        }
        arr[i-1] = key;
    }

    public static void main(String[] args) {
        int[] arr = {1, 5, 6, 7, 9, 2, 4, 8, 10, 13, 14};
        int[] copy = arr.clone();
        Arrays.sort(copy);
        System.out.println(Arrays.toString(copy));
        MergeOrderedSubArray.merge(arr, 5);
        System.out.println(Arrays.toString(arr));
    }
}
