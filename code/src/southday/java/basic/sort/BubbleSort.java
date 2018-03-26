package southday.java.basic.sort;
/**
 * @author southday
 */
public class BubbleSort {
    
    public static void sort(double[] arr) {
        boolean over = false;
        while (!over) {
            over = true;
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i + 1] < arr[i]) {
                    Sorter.swap(arr, i, i + 1);
                    over = false;
                }
            } // for
        } // while
    }
}
