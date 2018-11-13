package southday.java.acm.leetcode.solution;

import java.util.ArrayList;
import java.util.List;

/*
77. Combinations https://leetcode.com/problems/combinations/description/
Given two integers n and k, return all possible combinations of k numbers out of 1 ... n. 
Example: 
Input: n = 4, k = 2 Output:
[
    [2,4],
    [3,4],
    [2,3],
    [1,2],
    [1,3],
    [1,4],
]
*/

/**
 * 
 * @author southday
 * @date 2018年10月17日
 */
public class LeetCode_0077_Solution_01 {
    private List<List<Integer>> result = new ArrayList<List<Integer>>();
    private ArrayList<Integer> temp = new ArrayList<Integer>();
    
    public List<List<Integer>> combine(int n, int k) {
        combine(n, k, 1);
        return result;
    } 
    
    @SuppressWarnings("unchecked")
    private void combine(int n, int k, int start) {
        if (n-start+1 < k)
            return;
        for (int i = start; i <= n; i++) {
            temp.add(i);
            if (k == 1)
                result.add((ArrayList<Integer>)temp.clone());
            else
                combine(n, k-1, i+1);
            temp.remove(temp.size()-1);
        }
    }
    
    private void print(List<List<Integer>> result) {
        System.out.println("[");
        for (int i = 0, len1 = result.size(); i < len1; i++) {
            System.out.print("    [");
            List<Integer> ls = result.get(i);
            for (int j = 0, len2 = ls.size(); j < len2; j++) {
                if (j == len2-1)
                    System.out.print(ls.get(j) + "],\n");
                else
                    System.out.print(ls.get(j) + ",");
            }
        }
        System.out.println("]");
    }
    
    public static void main(String[] args) {
        LeetCode_0077_Solution_01 o = new LeetCode_0077_Solution_01();
        List<List<Integer>> ll = o.combine(4, 2);
        o.print(ll);
    }
}
