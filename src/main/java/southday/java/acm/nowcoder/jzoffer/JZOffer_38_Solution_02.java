package southday.java.acm.nowcoder.jzoffer;

import java.util.LinkedList;
import java.util.Queue;

/* 二叉树的深度 
输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。

使用层次遍历求解
*/

public class JZOffer_38_Solution_02 {
    
    public int TreeDepth(TreeNode root) {
        if (root == null) return 0;
        int level = 0;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        TreeNode r = root, p = null;
        while (!queue.isEmpty()) {
            TreeNode t = queue.poll();
            if (t.left != null) {
                queue.add(t.left);
                p = t.left;
            }
            if (t.right != null) {
                queue.add(t.right);
                p = t.right;
            }
            if (t == r) {
                level++;
                r = p;
            }
        }
        return level; 
    }
}
