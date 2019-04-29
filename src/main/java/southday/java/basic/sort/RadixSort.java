package southday.java.basic.sort;

/**
 * 基数排序 《算法导论》3rd 8.3基数排序
 * @author southday
 * @date 2019/4/29
 */
public class RadixSort extends Sorter {
    @Override
    public void sort(double[] arr) {}

    /**
     * 基数排序
     * @param arr arr[i] >= 0
     */
    @Override
    public void sort(int[] arr) {
        for (int i = 0, maxv = findMax(arr); maxv > 0; maxv /= 10, i++)
            bubbleSort(arr, i);
    }

    private void bubbleSort(int[] arr, int k) {
        boolean over = false;
        while (!over) {
            over = true;
            for (int i = 0; i < arr.length-1; i++) {
                int p = kthNum(arr[i], k);
                int q = kthNum(arr[i+1], k);
                if (p > q) {
                    Sorter.swap(arr, i, i+1);
                    over = false;
                }
            }
        }
    }

    /**
     * 求数字n的第k位数值（k从0开始），如：
     * 1) n = 1024, k = 0 => 4
     * 2) n = 1024, k = 1 => 20
     * 3) n = 1024, k = 2 => 0
     * 4) n = 1024, k = 3 => 1000
     * 5) n = 1024, k = 4 => 0
     * @param n
     * @param k
     * @return
     */
    private int kthNum(int n, int k) {
        return n % (int)Math.pow(10, k+1) - n % (int)Math.pow(10, k);
    }
}
