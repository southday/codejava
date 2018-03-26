package southday.java.basic.sort;

/* Sort    Time complexity                           Space complexity    Stabilization
 *           average       best         worst
 * select     O(n^2)       O(n^2)       O(n^2)         O(1)                N
 * heap       O(nlog2n)    O(nlog2n)    O(nlog2n)      O(1)                N    
 * 
 * insert     O(n^2)       O(n)         O(n^2)         O(1)                Y
 * shell      O(n^1.3)     O(n)         O(n^2)         O(1)                N
 * 
 * bubble     O(n^2)       O(n)         O(n^2)         O(1)                Y
 * quick      O(nlog2n)    O(nlog2n)    O(n^2)         O(nlog2n)           N
 * 
 * merger     O(nlog2n)    O(nlog2n)    O(nlog2n)      O(n)                Y
 * radix      O(d(r+n))    O(d(n+r*d))  O(d(r+n))      O(r*d+n)            Y
 * (In BarrelSort, r for the radix of keys, d for length, n for the number of keys)
 */

public class Sorter {
    
    public static void swap(double[] arr, int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }    
}
