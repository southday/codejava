package southday.java.basic.sort;

/**
 * 插入排序—直接插入排序
 * @author southday
 * @date 2019/4/29
 */
public class InsertSorter extends Sorter {

    @Override
    public void sort(double[] arr) {
        for (int i = 1; i < arr.length; i++) {
            double key = arr[i];
            int j = i - 1;
            for (; j >= 0 && arr[j] > key; j--)
                arr[j+1] = arr[j];
            arr[j+1] = key;
        }
    }

    @Override
    public void sort(int[] arr) {}
}
