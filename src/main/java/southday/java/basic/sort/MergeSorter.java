package southday.java.basic.sort;

import java.util.Arrays;

/**
 * 归并排序
 * @author southday
 * @date 2019/4/29
 */
public class MergeSorter extends Sorter {

    @Override
    public void sort(int[] arr) {}

    @Override
    public void sort(double[] arr) {
        sort(arr, 0, arr.length-1);
    }

    // [p, r]
    private void sort(double[] arr, int p, int r) {
        if (p < r) {
            int q = (p + r) >> 1;
            sort(arr, p, q);
            sort(arr, q + 1, r);
            merge(arr, p, q, r);
        }
    }
    
    private void merge(double[] arr, int p, int q, int r) {
        // merge [p, q] and [q + 1, r] -> [p, r]
        double[] L = Arrays.copyOfRange(arr, p, q+1);
        double[] R = Arrays.copyOfRange(arr, q+1, r+1);
        int i = 0, j = 0;
        while (i < L.length && j < R.length) {
            if (L[i] < R[j])
                arr[p++] = L[i++];
            else
                arr[p++] = R[j++];
        }
        // only one for{} be executed
        for (; i < L.length; arr[p++] = L[i++]);
        for (; j < R.length; arr[p++] = R[j++]);
    }
}
