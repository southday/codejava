package southday.java.basic.sort;

/**
 * 归并排序
 * @author southday
 * @date 2019/4/29
 */
public class MergerSorter extends Sorter {

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
    
    // p <= p <= r, you can also use 'piquet' to realize merge()
    private void merge(double[] arr, int p, int q, int r) {
        // merge [p, q] and [q + 1, r] -> [p, r]
        int i = p, j = q + 1;
        double[] arr_clone = arr.clone();
        for (int pos = p; pos <= r; pos++) {
            if (i <= q && j <= r) {
                if (arr_clone[i] < arr_clone[j]) {
                    arr[pos] = arr_clone[i]; i++;
                } else {
                    arr[pos] = arr_clone[j]; j++;
                } 
            // if there don't use arr_clone[],
            // for arr[pos++] = arr[j++](and i also), maybe some values could be cover
            } else if (i > q) {
                while (pos <= r)
                    arr[pos++] = arr_clone[j++];
            } else {
                while (pos <= r)
                    arr[pos++] = arr_clone[i++];
            }
        }
    }
}
