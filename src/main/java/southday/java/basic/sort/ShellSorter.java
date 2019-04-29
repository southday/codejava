package southday.java.basic.sort;

/**
 * 插入排序—希尔排序
 * @author southday
 * @date 2019/4/29
 */
public class ShellSorter extends Sorter {

    @Override
    public void sort(double[] arr) {
        int inc = arr.length >> 1;
        while (inc > 0) {
            for (int i = inc; i < arr.length; i += inc) {
                double key = arr[i];
                int j = i - inc;
                for (; j >= 0 && arr[j] > key; j -= inc)
                    arr[j+inc] = arr[j];
                arr[j+inc] = key;
            }
            inc >>= 1;
        }
    }

    @Override
    public void sort(int[] arr) {}
}
