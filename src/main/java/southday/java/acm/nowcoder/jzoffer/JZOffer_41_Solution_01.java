package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;

/* 和为S的连续正数序列 
题目描述
小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。但是他并不满足于此,
他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。
现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
输出描述:
输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序

*/

public class JZOffer_41_Solution_01 {
    
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (sum < 3) return result;
        int end = sum/2 + (sum % 2 == 0 ? 0 : 1);
        ArrayList<Integer> buf = new ArrayList<Integer>();
        buf.add(1); buf.add(2);
        for (int i = 1, j = 2, v = 3; j <= end;) {
            if (v < sum) {
                v += ++j;
                buf.add(j);
            } else if (v > sum) {
                v -= i++;
                buf.remove(0);
            } else {
                @SuppressWarnings("unchecked")
                ArrayList<Integer> copyList = (ArrayList<Integer>) buf.clone();
                result.add(copyList);
                v -= i++;
                buf.remove(0);
                v += ++j;
                buf.add(j);
            }
        }
        return result;
    }
    
}
