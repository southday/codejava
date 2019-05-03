package southday.java.basic.sort;

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
        int i = p, j = q + 1, pos = p;
        double[] arrClone = arr.clone();
        while (i <= q && j <= r) {
            if (arrClone[i] < arrClone[j])
                arr[pos++] = arrClone[i++];
            else
                arr[pos++] = arrClone[j++];
        }
        // only one for{} be executed
        for (; i <= q; arr[pos++] = arrClone[i++]);
        for (; j <= r; arr[pos++] = arrClone[j++]);
    }
}
