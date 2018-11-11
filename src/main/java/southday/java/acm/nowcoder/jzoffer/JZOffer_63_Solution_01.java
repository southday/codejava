package southday.java.acm.nowcoder.jzoffer;

import java.util.LinkedList;

/* 数据流中的中位数
如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取数据的中位数。

这题目搞得我很迷，使用Insert()方法读取数据流？这分明是插入数据啊
我用的是插入排序，LinkedList.get(i)时间复杂度O(n)，n为list元素个数，ls.add(index, e)复杂度为O(1)
由于Insert()操作中要多次get(i)，所以Insert()时间复杂度为O(n^2)

这个题还可以使用二叉堆来完成，别人的代码中使用了优先队列，我在JZOffer_63_Solution_02.java中试一试
*/

public class JZOffer_63_Solution_01 {
    LinkedList<Integer> ls = new LinkedList<Integer>();

    public void Insert(Integer num) {
        int i = 0, len = ls.size();
        while (i < len && num > ls.get(i)) i++;
        ls.add(i, num);
    }

    public Double GetMedian() {
        int len = ls.size();
        if (len <= 0) return null;
        if ((len & 1) == 1) { // 奇数
            return (double)ls.get(len>>1);
        } else { // 偶数
            double a = (double)ls.get(len>>1);
            double b = (double)ls.get((len>>1)-1);
            return (a+b)/2.0;
        }
    }
    
    public static void main(String[] args) {
        JZOffer_63_Solution_01 o = new JZOffer_63_Solution_01();
        int arr[] = {2,3,4,6,4};
//        int arr[] = {2,3,5,1,4,5,9,3};
        for (int i = 0; i < arr.length; i++) {
            o.Insert(arr[i]);
        }
        System.out.println(o.GetMedian());
    }
}
