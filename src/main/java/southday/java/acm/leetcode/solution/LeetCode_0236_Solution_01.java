package southday.java.acm.leetcode.solution;

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.TreeNode;

/**
 * 官方 Solution<br/>
 * 236. Lowest Common Ancestor of a Binary Tree https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * @author southday
 * @date 2019/3/14
 */
public class LeetCode_0236_Solution_01 {
    private TreeNode ans;

    public LeetCode_0236_Solution_01() {
        this.ans = null;
    }

    private boolean recurseTree(TreeNode currentNode, TreeNode p, TreeNode q) {

        // If reached the end of a branch, return false.
        if (currentNode == null) {
            return false;
        }

        // Left Recursion. If left recursion returns true, set left = 1 else 0
        int left = this.recurseTree(currentNode.left, p, q) ? 1 : 0;

        // Right Recursion
        int right = this.recurseTree(currentNode.right, p, q) ? 1 : 0;

        // If the current node is one of p or q
        int mid = (currentNode == p || currentNode == q) ? 1 : 0;


        // If any two of the flags left, right or mid become True
        if (mid + left + right >= 2) {
            this.ans = currentNode;
        }

        // Return true if any one of the three bool values is True.
        return (mid + left + right > 0);
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Traverse the tree
        this.recurseTree(root, p, q);
        return this.ans;
    }

    public static void main(String[] args) {
        String str = "3,5,6,#,#,2,7,#,#,4,#,#,1,0,#,#,8,#,#";
        TreeNode root = LeetCodeUtil.generateByPreOrder(str);
        TreeNode p = LeetCodeUtil.findTreeNode(root, 5);
        TreeNode q = LeetCodeUtil.findTreeNode(root, 3);
        LeetCode_0236_Solution_01 o = new LeetCode_0236_Solution_01();
        TreeNode lca = o.lowestCommonAncestor(root, p, q);
        System.out.println(lca.val);
    }
}

