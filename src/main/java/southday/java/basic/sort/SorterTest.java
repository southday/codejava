package southday.java.basic.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SorterTest {

    /**
     * 插入排序—直接插入排序
     */
    @Test
    public void testInsertSort() {
        double[] source = {128.23, 0.0, -23.83, 94, 19848.48, 405.984, -85.3};
        double[] target = {-85.3, -23.83, 0.0, 94.0, 128.23, 405.984, 19848.48};
        testSorter(new InsertSorter(), source, target);
    }
    /**
     * 插入排序—希尔排序
     */
    @Test
    public void testShellSort() {
        double[] source = {128.23, 0.0, -23.83, 94, 19848.48, 405.984, -85.3};
        double[] target = {-85.3, -23.83, 0.0, 94.0, 128.23, 405.984, 19848.48};
        testSorter(new ShellSorter(), source, target);
    }

    /**
     * 选择排序—直接选择排序
     */
    @Test
    public void testSelectSort() {
        double[] source = {128.23, 0.0, -23.83, 94, 19848.48, 405.984, -85.3};
        double[] target = {-85.3, -23.83, 0.0, 94.0, 128.23, 405.984, 19848.48};
        testSorter(new SelectSorter(), source, target);
    }
    /**
     * 选择排序—堆排序
     */
    @Test
    public void testHeapSort() {
        double[] source = {128.23, 0.0, -23.83, 94, 19848.48, 405.984, -85.3};
        double[] target = {-85.3, -23.83, 0.0, 94.0, 128.23, 405.984, 19848.48};
        testSorter(new HeapSorter(), source, target);
    }

    /**
     * 交换排序—冒泡排序
     */
    @Test
    public void testBubbleSort() {
        double[] source = {128.23, 0.0, -23.83, 94, 19848.48, 405.984, -85.3};
        double[] target = {-85.3, -23.83, 0.0, 94.0, 128.23, 405.984, 19848.48};
        testSorter(new BubbleSorter(), source, target);
    }
    /**
     * 交换排序—快速排序
     */
    @Test
    public void testQuickSort() {
        double[] source = {128.23, 0.0, -23.83, 94, 19848.48, 405.984, -85.3};
        double[] target = {-85.3, -23.83, 0.0, 94.0, 128.23, 405.984, 19848.48};
        testSorter(new QuickSorter(), source, target);
    }

    /**
     * 归并排序
     */
    @Test
    public void testMergeSort() {
        double[] source = {128.23, 0.0, -23.83, 94, 19848.48, 405.984, -85.3};
        double[] target = {-85.3, -23.83, 0.0, 94.0, 128.23, 405.984, 19848.48};
        testSorter(new MergeSorter(), source, target);
    }

    /**
     * 基数排序
     */
    @Test
    public void testRadixSort() {
        int[] source = {8234, 19, 0, 84, 8, 188392, 9294, 184, 285, 8332, 9852};
        int[] target = {0, 8, 19, 84, 184, 285, 8234, 8332, 9294, 9852, 188392};
        testSorter(new RadixSort(), source, target);
    }

    /**
     * 计数排序
     */
    @Test
    public void testCountSort() {
        int[] source = {8234, 19, 0, 84, 8, 188392, 9294, 184, 285, 8332, 9852};
        int[] target = {0, 8, 19, 84, 184, 285, 8234, 8332, 9294, 9852, 188392};
        System.out.println("Test：CountSorter");
        System.out.println("source: " + Arrays.toString(source));
        int[] result = new CountSorter().sort(source);
        System.out.println("result: " + Arrays.toString(result));
        System.out.println("target: " + Arrays.toString(target));
        Assert.assertArrayEquals(target, result);
    }

    @Test
    public void testBarrelSort() {
        double[] source = {0.232, 0.0, 0.843, 0.194, 0.48, 0.24, 0.30};
        double[] target = {0.0, 0.194, 0.232, 0.24, 0.30, 0.48, 0.843};
        testSorter(new BarrelSorter(), source, target);
    }

    /**
     * 公共测试模版
     * @param sorter
     * @param source 原数组
     * @param target 目标数组（排序后的数组）
     */
    public void testSorter(Sorter sorter, double[] source, double[] target) {
        System.out.println("Test：" + sorter.getClass().getSimpleName());
        System.out.println("source: " + Arrays.toString(source));
        double[] temp = source.clone();
        sorter.sort(temp);
        System.out.println("result: " + Arrays.toString(temp));
        System.out.println("target: " + Arrays.toString(target));
        Assert.assertArrayEquals(target, temp, 0.0001);
    }

    /**
     * 公共测试模版
     * @param sorter
     * @param source 原数组
     * @param target 目标数组（排序后的数组）
     */
    public void testSorter(Sorter sorter, int[] source, int[] target) {
        System.out.println("Test：" + sorter.getClass().getSimpleName());
        System.out.println("source: " + Arrays.toString(source));
        int[] temp = source.clone();
        sorter.sort(temp);
        System.out.println("result: " + Arrays.toString(temp));
        System.out.println("target: " + Arrays.toString(target));
        Assert.assertArrayEquals(target, temp);
    }
}
