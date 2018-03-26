package southday.java.basic.sort;
/**
 * @author southday
 */
public class ShellSort {
    
    public static void sort(double[] arr) {
        int inc = arr.length >> 1;
        while (inc > 0) {
            for (int i = 0; i < arr.length; i += inc) {
                for (int j = i; j >= inc; j -= inc) {
                    if (arr[j] < arr[j - inc])
                        Sorter.swap(arr, j, j - inc);
                    else break;
                }
            }
            inc >>= 1;
        } // while
    }
}
