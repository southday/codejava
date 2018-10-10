package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;

/* 二叉树中和为某一值的路径 
输入一颗二叉树的跟节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。(注意: 在返回值的list中，数组长度大的数组靠前)
*/

public class JZOffer_24_Solution_01 {
    ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
    ArrayList<Integer> tmp = new ArrayList<Integer>();
    int sum = 0;
    
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        DFS(root, target);
        return result;
    }
    
    private void DFS(TreeNode root, int target) {
        if (root != null) {
            sum += root.val;
            tmp.add(root.val);
            if (sum == target && root.left == null && root.right == null) {
                @SuppressWarnings("unchecked")
                ArrayList<Integer> ls = (ArrayList<Integer>)tmp.clone();
                int i = 0;
                for (int newLen = ls.size(), len = result.size(); i < len; i++) {
                    if (newLen > result.get(i).size()) {
                        break;
                    }
                }
                result.add(i, ls);
                sum -= root.val;
                tmp.remove(tmp.size()-1);
                return;
            }
            DFS(root.left, target);
            DFS(root.right, target);
            sum -= root.val;
            tmp.remove(tmp.size()-1);
            return;
        }
    }
}
