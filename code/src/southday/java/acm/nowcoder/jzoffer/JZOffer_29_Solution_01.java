package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;
import java.util.Arrays;

/* 最小的K个数 
输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
*/

public class JZOffer_29_Solution_01 {
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (k < 0 || k > input.length) return result;
        Arrays.sort(input);
        for (int i = 0; i < input.length && k > 0; i++, k--) {
            result.add(input[i]);
        }
        return result;
    }
}
