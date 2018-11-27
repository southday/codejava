package southday.java.acm.leetcode.solution;

/*
617. Merge Two Binary Trees  https://leetcode.com/problems/merge-two-binary-trees/description/

Given two binary trees and imagine that when you put one of them to cover the other, some nodes of the two trees are overlapped while the others are not.
You need to merge them into a new binary tree. The merge rule is that if two nodes overlap, then sum node values up as the new value of the merged node.
Otherwise, the NOT null node will be used as the node of new tree.

Example 1:
Input:
	Tree 1                     Tree 2
          1                         2
         / \                       / \
        3   2                     1   3
       /                           \   \
      5                             4   7
Output:
Merged tree:
	     3
	    / \
	   4   5
	  / \   \
	 5   4   7

Note: The merging process must start from the root nodes of both trees.

 */

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.TreeNode;

/**
 * Author southday
 * Date   2018/11/27
 */
public class LeetCode_0617_Solution_01 {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null)
            return t2;
        if (t2 == null)
            return t1;
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }

    public static void main(String[] args) {
        String s1 = "1,3,5,#,#,#,2,#,#";
        String s2 = "2,1,#,4,#,#,3,#,7,#,#";
        TreeNode t1 = LeetCodeUtil.generateByPreOrder(s1);
        TreeNode t2 = LeetCodeUtil.generateByPreOrder(s2);
        LeetCodeUtil.printPreOrder(t1);
        LeetCodeUtil.printPreOrder(t2);

        LeetCode_0617_Solution_01 o = new LeetCode_0617_Solution_01();
        TreeNode t3 = o.mergeTrees(t1, t2);
        LeetCodeUtil.printPreOrder(t3);
    }
}
