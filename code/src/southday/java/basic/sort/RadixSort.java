package southday.java.basic.sort;

/**
 * @author southday
 */
public class RadixSort {
    static int d = 0;
    /**
     * 
     * @param arr
     * @param d The digit of Maximum in arr[]
     */
    public static void sort(int[] arr, int d) {
        RadixSort.d = d;
        for (int k = 1; k <= d; k++) {
            insertSort(arr, k);
        }
    }

    private static void insertSort(int[] arr, int k) {
        int p1 = 0, p2 = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j > 0; j--) { // get the kth number of arr[j] or arr[j - 1]
                p1 = arr[j] % (int) Math.pow(10, k) - arr[j] % (int) Math.pow(10, k - 1);
                p2 = arr[j - 1] % (int) Math.pow(10, k) - arr[j - 1] % (int) Math.pow(10, k - 1);
                if (p1 < p2) {
                    Sorter.swap(arr, j, j - 1);
                }
            }
        }
    }
}
