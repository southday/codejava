package southday.java.basic.sort;

/**
 * 交换排序—快速排序
 * @author southday
 * @date 2019/4/29
 */
public class QuickSorter extends Sorter {

    @Override
    public void sort(int[] arr) {}

    @Override
    public void sort(double[] arr) {
        sort(arr, 0, arr.length-1);
    }

    private void sort(double[] arr, int head, int tail) {
        if (head < tail) {
            int pos = partition(arr, head, tail);
            sort(arr, head, pos - 1);
            sort(arr, pos + 1, tail);
        }
    }
    
    private int partition(double[] arr, int head, int tail) {
        int i = head - 1;
        for (int j = head; j < tail; j++) {
            if (arr[j] < arr[tail])
                swap(arr, ++i, j);
        }
        Sorter.swap(arr, i + 1, tail);
        return i + 1;
    }
}
