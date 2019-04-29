package southday.java.basic.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 桶排序 《算法导论》3rd 8.4桶排序
 * @author southday
 * @date 2019/4/29
 */
public class BarrelSorter extends Sorter {
    private static final MyComparator COMPARATOR = new MyComparator();

    /**
     * 桶排序
     * @param arr Assume 0 <= arr[i] < 1, i = 1, 2, ..., n
     */
    @Override
    public void sort(double[] arr) {
        List<Double>[] barrel = new ArrayList[10];
        for (int i = 0; i < barrel.length; i++)
            barrel[i] = new ArrayList<>();
        // 将 arr[i] 放到对应的桶中 barrel[index]
        for (int i = 0, index; i < arr.length; i++) {
            index = (int)(arr[i] * 10); // arr[i] = 0.32 => index = 3
            barrel[index].add(arr[i]);
        }
        // 对每个barrel[i]进行排序
        for (int i = 0; i < barrel.length; i++)
            barrel[i].sort(COMPARATOR);
        // 将 barrel[] 中的数据从桶[0->9]（有序）填充到 arr[0->arr.length-1]
        for (int index = 0, i = 0; index < 10; index++)
            for (double x : barrel[index])
                arr[i++] = x;
    }

    private static class MyComparator implements Comparator<Double> {
        @Override
        public int compare(Double o1, Double o2) {
            return o1.compareTo(o2);
        }
    }

    @Override
    public void sort(int[] arr) {}
}
