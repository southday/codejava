package southday.java.basic.sort;

/**
 * 插入排序—希尔排序
 * @author southday
 * @date 2019/4/29
 */
public class ShellSorter extends Sorter {

    @Override
    public void sort(double[] arr) {
        for (int inc = arr.length/2; inc > 0; inc /= 2) {
            for (int i = inc; i < arr.length; i++) {
                double key = arr[i];
                int j = i - inc;
                for (; j >= 0 && arr[j] > key; j -= inc)
                    arr[j+inc] = arr[j];
                arr[j+inc] = key;
            }
        }
    }

    @Override
    public void sort(int[] arr) {}
}
