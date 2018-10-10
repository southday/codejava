package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayDeque;
import java.util.ArrayList;

/* 滑动窗口的最大值
给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
 {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}，
 {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。

使用双端队列dq（保存的是角标），最左端保留滑动窗口最大值的角标，每次移动窗口
1) 判断dq.first是过期，即是否被排除到窗口之外，通过角标比较来判断是否过期
2) 对于新加入的num[i]，从dq队尾开始比较，把所有num[dq.last]比num[i]小的元素都丢弃 dq.pollLast()
3) 把num[dq.first] 加入 result

为什么算法是正确的呢？主要看(2)
对于新加入的num[i]，从dq队尾开始比较，把所有num[dq.last]比num[i]小的元素都丢弃 dq.pollLast()
假设 a, b 均小于 num[i]，如果不丢弃，dq的情况为：{max, a, b, num[i]}
因为窗口是往后滑动的，因此，只要包含a就一定包含num[i]，而求的值最大值，所以a是无用的，可以丢弃。对于b同理。
*/

public class JZOffer_64_Solution_02 {
    
    public ArrayList<Integer> maxInWindows(int [] num, int size) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (num == null || size <= 0 || num.length < size) return result;
        ArrayDeque<Integer> dq = new ArrayDeque<Integer>();
        for (int i = 0; i < size; i++) {
            while (!dq.isEmpty() && num[dq.getFirst()] <= num[i]) dq.pollLast();
            dq.add(i);
        }
        result.add(num[dq.getFirst()]);
        for (int i = 1, j = i+size-1; j < num.length; i++, j++) {
            if (i > dq.getFirst()) dq.pollFirst();
            while (!dq.isEmpty() && num[dq.getLast()] <= num[j]) dq.pollLast();
            dq.add(j);
            result.add(num[dq.getFirst()]);
        }
        return result;
    }
    
    public static void main(String[] args) {
        JZOffer_64_Solution_02 o = new JZOffer_64_Solution_02();
        int num[] = {2,3,4,2,6,2,5,1};
        for (int a : o.maxInWindows(num, 3)) {
            System.out.print(a + " ");
        }
        System.out.println();
    }
}
