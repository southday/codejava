package southday.java.basic.sort;

/**
 * 交互排序—冒泡排序
 * @author southday
 * @date 2019/4/29
 */
public class BubbleSorter extends Sorter {

    @Override
    public void sort(double[] arr) {
        boolean over = false;
        for (int n = arr.length; !over && n > 1; n--) {
            over = true;
            for (int i = 0; i < n-1; i++) {
                if (arr[i] > arr[i+1]) {
                    Sorter.swap(arr, i, i+1);
                    over = false;
                }
            }
        }
    }

    @Override
    public void sort(int[] arr) {}
}
