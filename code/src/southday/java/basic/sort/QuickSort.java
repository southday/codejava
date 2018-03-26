package southday.java.basic.sort;

/**
 * @author southday 
 */
public class QuickSort {
    
    public static void sort(double[] arr, int head, int tail) {
        if (head < tail) {
            int pos = partition(arr, head, tail);
            sort(arr, head, pos - 1);
            sort(arr, pos + 1, tail);
        }
    }
    
    private static int partition(double[] arr, int head, int tail) {
        int i = head - 1;
        for (int j = head; j < tail; j++) {
            if (arr[j] < arr[tail])
                Sorter.swap(arr, ++i, j);
        }
        Sorter.swap(arr, i + 1, tail);
        return i + 1;
    }
}
