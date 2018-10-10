package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/* 把二叉树打印成多行
从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
*/

public class JZOffer_60_Solution_01 {
    @SuppressWarnings("unchecked")
    ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (pRoot == null) return result;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(pRoot);
        TreeNode r = pRoot, q = pRoot;
        ArrayList<Integer> ls = new ArrayList<Integer>();
        while (!queue.isEmpty()) {
            TreeNode t = queue.poll();
            ls.add(t.val);
            if (t.left != null) {
                queue.add(t.left);
                q = t.left;
            }
            if (t.right != null) {
                queue.add(t.right);
                q = t.right;
            }
            if (t == r) {
                result.add((ArrayList<Integer>)ls.clone());
                ls.clear();
                r = q;
            }
        }
        return result;
    }
}
