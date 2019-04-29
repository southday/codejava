package southday.java.basic.sort;

/**
 * 选择排序—直接选择排序
 * @author southday
 * @date 2019/4/29
 */
public class SelectSorter extends Sorter {

    @Override
    public void sort(double[] arr) {
        for (int i = 0, minp; i < arr.length - 1; i++) {
            minp = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[minp])
                    minp = j;
            Sorter.swap(arr, minp, i);
        }
    }

    @Override
    public void sort(int[] arr) {}
}
