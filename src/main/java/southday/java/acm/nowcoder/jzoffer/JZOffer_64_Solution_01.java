package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;
import java.util.PriorityQueue;

/* 滑动窗口的最大值
给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
 {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， 
 {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。

时间复杂度：
1) 初始化：O(lg(size))
2) 接下来的 k = num.length-size，每次循环：
    a) pq.remove(): O(size) + O(lg(size-1))
    b) pq.add(): O(lg(size))
   2) 总的时间复杂度：k*(O(size) + 2*O(lg(size)))
总时间复杂度：k*O(size)
*/

public class JZOffer_64_Solution_01 {
    
    public ArrayList<Integer> maxInWindows(int [] num, int size) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (num == null || size <= 0 || num.length < size) return result;
        /* 可使用lambda表达式简述书写
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        */
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((o1, o2)->(o2.compareTo(o1)));
        for (int i = 0; i < size; i++)
            pq.offer(num[i]);
        result.add(pq.peek());
        for (int i = 0, k = num.length-size; k > 0; i++, k--) {
            pq.remove(num[i]);
            pq.add(num[i+size]);
            result.add(pq.peek());
        }
        return result;
    }
    
    public static void main(String[] args) {
        JZOffer_64_Solution_01 o = new JZOffer_64_Solution_01();
        int num[] = {2,3,4,2,6,2,5,1};
//        int num[] = {2,3};
        for (int a : o.maxInWindows(num, 3)) {
            System.out.print(a + " ");
        }
        System.out.println();
    }
}
