package southday.java.acm.leetcode.solution;

/*
104. Maximum Depth of Binary Tree  https://leetcode.com/problems/maximum-depth-of-binary-tree/description/
Given a binary tree, find its maximum depth.
The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
Note: A leaf is a node with no children.

Example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its depth = 3.
 */

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.TreeNode;

/**
 * Author southday
 * Date   2018/12/4
 */
public class LeetCode_0104_Solution_01 {
    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        int maxd[] = {1};
        DFS(root, 1, maxd);
        return maxd[0];
    }

    private void DFS(TreeNode root, int curDepth, int[] maxd) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            if (curDepth > maxd[0])
                maxd[0] = curDepth;
            return;
        }
        if (root.left != null)
            DFS(root.left, curDepth+1, maxd);
        if (root.right != null)
            DFS(root.right, curDepth+1, maxd);
    }

    public static void main(String[] args) {
        LeetCode_0104_Solution_01 o = new LeetCode_0104_Solution_01();
        String str = "3,9,#,#,20,15,#,#,7,#,#";
        TreeNode root = LeetCodeUtil.generateByPreOrder(str);
        int maxd = o.maxDepth(root);
        System.out.println(maxd);
    }
}
