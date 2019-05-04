package southday.java.basic.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class SorterTester {
    private static final Random rand = new Random(47);

    private enum OddEven {
        ODD, EVEN
    }

    /**
     * 插入排序—直接插入排序
     */
    @Test
    public void testInsertSort() {
        testSortDouble(new InsertSorter());
    }
    /**
     * 插入排序—希尔排序
     */
    @Test
    public void testShellSort() {
        testSortDouble(new ShellSorter());
    }

    /**
     * 选择排序—直接选择排序
     */
    @Test
    public void testSelectSort() {
        testSortDouble(new SelectSorter());
    }
    /**
     * 选择排序—堆排序
     */
    @Test
    public void testHeapSort() {
        testSortDouble(new HeapSorter());
    }

    /**
     * 交换排序—冒泡排序
     */
    @Test
    public void testBubbleSort() {
        testSortDouble(new BubbleSorter());
    }
    /**
     * 交换排序—快速排序
     */
    @Test
    public void testQuickSort() {
        testSortDouble(new QuickSorter());
    }

    /**
     * 归并排序
     */
    @Test
    public void testMergeSort() {
        testSortDouble(new MergeSorter());
    }

    /**
     * 基数排序
     */
    @Test
    public void testRadixSort() {
        int[] source1 = {8234, 19, 0, 84, 8, 188392, 9294, 184, 285, 8332, 9852};
        testSort(new RadixSorter(), source1);
        int[] source2 = {8234, 19, 0, 84, 8, 188392, 9294, 184, 285, 8332};
        testSort(new RadixSorter(), source2);
    }

    /**
     * 计数排序
     */
    @Test
    public void testCountSort() {
        int[] source1 = {8234, 19, 0, 84, 8, 188392, 9294, 184, 285, 8332, 9852};
        testSort(new CountSorter(), source1);
        int[] source2 = {8234, 19, 0, 84, 8, 18392, 9294, 184, 285, 8332};
        testSort(new CountSorter(), source2);
    }

    /**
     * 桶排序
     */
    @Test
    public void testBarrelSort() {
        double[] source1 = {0.232, 0.0, 0.843, 0.194, 0.48, 0.24, 0.30};
        testSort(new BarrelSorter(), source1);
        double[] source2 = {0.232, 0.0, 0.843, 0.194, 0.48, 0.24, 0.30, 0.92};
        testSort(new BarrelSorter(), source2);
    }

    /**
     * 测试 int[]
     * @param sorter
     */
    public static void testSortInt(Sorter sorter) {
        testSort(sorter, generateIntArray(OddEven.ODD));
        testSort(sorter, generateIntArray(OddEven.EVEN));
    }

    /**
     * 测试 double[]
     * @param sorter
     */
    public static void testSortDouble(Sorter sorter) {
        testSort(sorter, generateDoubleArray(OddEven.ODD));
        testSort(sorter, generateDoubleArray(OddEven.EVEN));
    }

    /**
     * 随机生成 double[]
     * @param oddOrEven
     * @return
     */
    private static double[] generateDoubleArray(OddEven oddOrEven) {
        double[] arr = oddOrEven == OddEven.ODD ? new double[19] : new double[18];
        for (int i = 0; i < arr.length; i++)
            arr[i] = rand.nextDouble()*100.0 - 20.0;
        return arr;
    }

    /**
     * 随机生成 int[]
     * @param oddOrEven
     * @return
     */
    private static int[] generateIntArray(OddEven oddOrEven) {
        int[] arr = oddOrEven == OddEven.ODD ? new int[19] : new int[18];
        for (int i = 0; i < arr.length; i++)
            arr[i] = rand.nextInt(50) - 20;
        return arr;
    }

    /**
     * 测试 int[]
     * @param sorter
     * @param arr
     */
    public static void testSort(Sorter sorter, int[] arr) {
        int[] expected = arr.clone();
        System.out.println(sorter.getClass().getSimpleName() + ", arr.length = " + arr.length);
        Arrays.sort(expected);
        System.out.println("Original: " + Arrays.toString(arr));
        System.out.println("excepted: " + Arrays.toString(expected));
        sorter.sort(arr);
        System.out.println("result:   " + Arrays.toString(arr));
        Assert.assertArrayEquals(expected, arr);
    }

    /**
     * 测试 double[]
     * @param sorter
     * @param arr
     */
    public static void testSort(Sorter sorter, double[] arr) {
        double[] expected = arr.clone();
        System.out.println(sorter.getClass().getSimpleName() + ", arr.length = " + arr.length);
        Arrays.sort(expected);
        System.out.println("Original: " + Arrays.toString(arr));
        System.out.println("excepted: " + Arrays.toString(expected));
        sorter.sort(arr);
        System.out.println("result:   " + Arrays.toString(arr));
        Assert.assertArrayEquals(expected, arr, 0.0001);
    }
}
