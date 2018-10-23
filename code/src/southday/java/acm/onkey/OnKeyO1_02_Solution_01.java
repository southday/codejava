package southday.java.acm.onkey;

import java.util.ArrayList;

/*
2.Given two integers n and k, return all possible combinations of k numbers out of 1 ... n. 
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

public class OnKeyO1_02_Solution_01 {
    private ArrayList<Integer> ls = new ArrayList<Integer>();
    
    public static void main(String[] args) {
        OnKeyO1_02_Solution_01 o = new OnKeyO1_02_Solution_01();
        o.foo(4, 3);
    }
    
    public void foo(int n, int k) {
        System.out.println("[");
        foo(n, k, 1);
        System.out.println("]");
    }
    
    private void foo(int n, int k, int start) {
        if (n-start+1 < k)
            return;
        for (int i = start; i <= n; i++) {
            ls.add(i);
            if (k == 1)
                print(ls);
            else
                foo(n, k-1, i+1);
            ls.remove(ls.size()-1);
        }
    }
    
    private void print(ArrayList<Integer> ls) {
        System.out.print("    [");
        for (int i = 0, len = ls.size(); i < len; i++) {
            if (i == len-1)
                System.out.print(ls.get(i) + "],\n");
            else
                System.out.print(ls.get(i) + ",");
        }
    }
}
