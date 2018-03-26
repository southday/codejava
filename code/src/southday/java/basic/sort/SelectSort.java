package southday.java.basic.sort;

/**
 * @author southday 
 */
public class SelectSort {
    public static void sort(double[] arr) {
        int minpos = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minpos]) minpos = j;
            }
            Sorter.swap(arr, minpos, i);
        }
    }
    
    // find the maximum and minimum on the same time
    public static void improveSort(double[] arr) {
        int minpos = 0, maxpos = 0;
        for (int i = 0; i < (arr.length >> 1); i++) {
            for (int j = i + 1; j < arr.length - i; j++) {
                if (arr[j] < arr[minpos]) minpos = j;
                if (arr[j] > arr[maxpos]) maxpos = j;
            }
            Sorter.swap(arr, i, minpos);
            Sorter.swap(arr, arr.length - i - 1, maxpos);
        }
    }
}
