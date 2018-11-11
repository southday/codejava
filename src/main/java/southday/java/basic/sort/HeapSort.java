package southday.java.basic.sort;

/**
 * @author southday
 */
public class HeapSort {
    /**
     * The element number of array
     */
    private static int heap_size = 0;
    
    public static void sort(double[] arr) {
        heap_size = arr.length;
        buildMaxHeap(arr);
        for (int i = heap_size - 1; i >= 1; i--) {
            Sorter.swap(arr, 0, i);
            heap_size--;
            fixMaxHeap(arr, 0);
        }
    }
    
    private static void buildMaxHeap(double[] arr) {
        for (int i = (heap_size - 1) >> 1; i >= 0; i--)
            fixMaxHeap(arr, i);
    }
    
    // maintains the max heap
    private static void fixMaxHeap(double[] arr, int i) {
        int left = leftChild(i);
        int right = rightChild(i);
        int largest = i;
        
        largest = (left < heap_size && arr[left] > arr[i]) ? left : i;
        if (right < heap_size && arr[right] > arr[largest]) largest = right;
        if (largest != i) {
            Sorter.swap(arr, largest, i);
            fixMaxHeap(arr, largest);
        }
    }
    
    private static int leftChild(int i) {
        return (i << 1) + 1;
    }
    
    private static int rightChild(int i) {
        return (i + 1) << 1;
    }
    
}
