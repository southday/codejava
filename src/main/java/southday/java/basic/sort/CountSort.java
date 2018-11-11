package southday.java.basic.sort;
/**
 * CounSort is not often be used, because it just for {@code int[]} array.
 * There are <b> n </b> input integers, which every one is in area {@code [0, k]},
 * and k is a integer ({@code k = O(n)})
 * 
 * @author southday
 */
public class CountSort {
    
    /**
     * Require: <b> {@code 0 <= arr[i] <= k, k = O(n)} </b>
     * @param arr
     * @param results
     * @param k
     */
    public static void sort(int[] arr, int[] results, int k) {
        int[] counts = new int[++k]; // [0...k]
        for (int i = 0; i < k; i++) counts[i] = 0;
        for (int i = 1; i < arr.length; i++) counts[arr[i]]++;
        // counts[i] now contains the number of elements equal to i
        for (int i = 1; i < k; i++) counts[i] = counts[i] + counts[i - 1];
        // counts[i] now contains the number of elements less than or equal to i
        for (int i = 1; i < arr.length; i++) {
            results[counts[arr[i]]] = arr[i];
            counts[arr[i]]--;
        }
    }
}
