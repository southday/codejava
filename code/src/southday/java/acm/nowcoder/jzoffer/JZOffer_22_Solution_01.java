package southday.java.acm.nowcoder.jzoffer;

/* 从上往下打印二叉树 
从上往下打印出二叉树的每个节点，同层节点从左至右打印。
*/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class JZOffer_22_Solution_01 {
    
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode n = queue.poll();
            result.add(n.val);
            if (n.left != null) {
                queue.add(n.left);
            }
            if (n.right != null) {
                queue.add(n.right);
            }
        }
        return result;
    }
}
