package southday.java.basic.sort;

/**
 * @author southday
 */
public class InsertSort {
    
    public static void sort(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1])
                    Sorter.swap(arr, j, j - 1);
                else break;
            }
        } // for
    }
}
