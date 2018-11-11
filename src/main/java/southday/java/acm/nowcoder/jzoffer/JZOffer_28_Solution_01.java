package southday.java.acm.nowcoder.jzoffer;

/* 数组中出现次数超过一半的数字 
数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。 

思想：
1）假定这个数为v（候选）,那么数组中的元素有两种情况：=v 或者 !=v
2）每当 =v 时，cnt++，!=v时，cnt--
3）当cnt==0时，令array[i+1]为新的v
4）遍历结束，统计数组中v的个数，看是否大于数组长度的一半
*/

public class JZOffer_28_Solution_01 {
    
    public int MoreThanHalfNum_Solution(int [] array) {
        if (array.length == 0) return 0;
        int v = array[0];
        int cnt = 1;
        for (int i = 1; i < array.length; i++) {
            if (cnt == 0) {
                v = array[i];
                cnt = 1;
                continue;
            }
            if (array[i] == v) {
                cnt++;
            } else {
                cnt--;
            }
        }
        cnt = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == v) {
                cnt++;
            }
        }
        return (cnt << 1) > array.length ? v : 0;
    }
}
