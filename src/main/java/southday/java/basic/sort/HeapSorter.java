package southday.java.basic.sort;

/**
 * 选择排序—堆排序
 * @author southday
 * @date 2019/4/29
 */
public class HeapSorter extends Sorter {
    // heapSize = arr.length
    private int heapSize = 0;

    @Override
    public void sort(int[] arr) {}

    @Override
    public void sort(double[] arr) {
        heapSize = arr.length;
        buildMaxHeap(arr);
        for (int i = heapSize - 1; i >= 1; i--) {
            Sorter.swap(arr, 0, i);
            heapSize--;
            fixMaxHeap(arr, 0);
        }
    }

    private void buildMaxHeap(double[] arr) {
        for (int i = lastFather(); i >= 0; i--)
            fixMaxHeap(arr, i);
    }
    
    // maintains the max heap
    private void fixMaxHeap(double[] arr, int i) {
        if (i <= lastFather()) {
            int left = leftChild(i);
            int right = rightChild(i);
            int maxIndex;
            maxIndex = arr[left] > arr[i] ? left : i;
            if (right < heapSize && arr[right] > arr[maxIndex])
                maxIndex = right;
            if (maxIndex != i) {
                Sorter.swap(arr, maxIndex, i);
                fixMaxHeap(arr, maxIndex);
            }
        }
    }
    
    private int lastFather() {
        return heapSize/2 - 1;
    }
    
    private int leftChild(int i) {
        return (i << 1) + 1;
    }
    
    private int rightChild(int i) {
        return (i + 1) << 1;
    }
    
}
