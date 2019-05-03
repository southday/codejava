package southday.java.basic.sort;
/**
 * CounSort is not often be used, because it just for {@code int[]} array.
 * There are <b> n </b> input integers, which every one is in area {@code [0, k]},
 * and k is a integer ({@code k = O(n)})
 *
 * 计数排序 《算法导论》3rd 8.2计数排序
 * @author southday
 * @date 2019/4/29
 */
public class CountSorter extends Sorter {

    @Override
    public void sort(double[] arr) {

    }

    @Override
    public void sort(int[] arr) {
        int maxv = Sorter.findMax(arr);
        int[] cnt = new int[maxv+1]; // [0...mav]
        for (int i = 0; i <= maxv; i++)
            cnt[i] = 0;
        for (int i = 0; i < arr.length; i++)
            cnt[arr[i]]++; // 统计
        for (int i = 1; i <= maxv; i++)
            cnt[i] = cnt[i] + cnt[i - 1]; // 累加，在值i的前面（包括i本身）有 cnt[i] 个元素 <= i
        /* resutl[]角标可取的最大值是cnt[maxv] = arr.length
         * 本来应该是要 new int[arr.length+1]的，但是可以在下面赋值时给 cnt[arr[i]]-1 来达到同样的效果
         */
        int[] result = new int[arr.length];
        for (int i = arr.length-1; i >= 0; i--) {
            /* 这里 cnt[arr[i]-1] 的原因：
             * 1) m = cnt[arr[i]] 表示有 m 个值 <= arr[i] 的元素存在，包括arr[i]本身
             * 2) cnt[arr[i]-1] 可以理解为除去 arr[i]本身，有 m-1 个值 <= arr[i]
             * 3) 我们填充 result[] 是从 0 开始计数，所以 m-1 刚好就是 arr[i] 的位置
             */
            result[cnt[arr[i]]-1] = arr[i];
            cnt[arr[i]]--;
        }
        for (int i = 0; i < arr.length; i++)
            arr[i] = result[i];
    }
}
