package southday.java.acm.nowcoder.jzoffer;

import java.util.Comparator;
import java.util.PriorityQueue;

/* 数据流中的中位数
如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取数据的中位数。

使用优先队列（大小根堆来试一试）
关键点：
 大根堆maxq       小根堆minq
----------      -------------
          \    /
  <= A     A  B   >= B
          /    \
----------      -------------

每次insert(num)前要确保 ：
    1) maxq.size == minq.size // 偶数个时，二者元素个数相等
或  2) minq.size == maxq.size + 1 // 奇数个时把多余的1个放到小根堆minq
这样一来，获取中位数时：
奇数个：minq.top;
偶数个：(minq.top + maxq.top) / 2

每次isnert(num)后，可能会打破上面的条件，出现下面的情况：
    1) maxq.size == minq.size + 1 // 打破条件(1) => 这时需要把maxq.top放到minq中
或  2) minq.size == maxq.size + 2 // 打破条件(2) => 这时需要把minq.top放到maxq中
*/

public class JZOffer_63_Solution_02 {
    PriorityQueue<Integer> minq = new PriorityQueue<Integer>();
    PriorityQueue<Integer> maxq = new PriorityQueue<Integer>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    });

    public void Insert(Integer num) {
        if (minq.isEmpty() || num >= minq.peek()) minq.offer(num);
        else maxq.offer(num);
        if (minq.size() == maxq.size()+2) maxq.offer(minq.poll());
        if (maxq.size() == minq.size()+1) minq.offer(maxq.poll());
    }

    public Double GetMedian() {
        return minq.size() == maxq.size() ? (double)(minq.peek()+maxq.peek())/2.0 : (double)minq.peek();
    }
    
    public static void main(String[] args) {
        JZOffer_63_Solution_02 o = new JZOffer_63_Solution_02();
        int arr[] = {2,3,1,4,6,4};
//        int arr[] = {2,3,5,1,4,5,9,3};
        for (int i = 0; i < arr.length; i++) {
            o.Insert(arr[i]);
        }
        System.out.println(o.GetMedian());
    }
}
