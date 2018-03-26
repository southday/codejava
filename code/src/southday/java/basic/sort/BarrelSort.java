package southday.java.basic.sort;

/**
 * @author southday
 */
public class BarrelSort {
    
    // 'barrel sort' use Linked, but there I use two-dimension array
    // Assume 0 <= arr[i] < 1, i = 1, 2, ..., n
    public static void sort(double[] arr) {
        double[][] barrel = new double[arr.length][arr.length]; // waste space
        int count[] = new int[arr.length];
        
        for (int i = 0, index = 0; i < arr.length; i++) {
            index = (int) arr[i];
            barrel[index][count[index]++] = arr[i];
        }
        for (int i = 0, pos = 0; i < arr.length; i++) {
            if (count[i] != 0) {
                ShellSort.sort(barrel[i]);
                for (int j = 0; j < count[i]; j++) {
                    arr[pos++] = barrel[i][j];
                }
            }// if
        }
    }
}
