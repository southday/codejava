package southday.java.acm.leetcode.solution;

/*
700. Search in a Binary Search Tree  https://leetcode.com/problems/search-in-a-binary-search-tree/description/

Given the root node of a binary search tree (BST) and a value. You need to find the node in the BST that the node's value equals the given value.
Return the subtree rooted with that node. If such node doesn't exist, you should return NULL.

For example,
Given the tree:
        4
       / \
      2   7
     / \
    1   3

And the value to search: 2
You should return this subtree:

      2
     / \
    1   3

In the example above, if we want to search the value 5, since there is no node with value 5, we should return NULL.
Note that an empty tree is represented by NULL, therefore you would see the expected output (serialized tree format) as [], not null.

 */

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.TreeNode;

/**
 * Author southday
 * Date   2018/11/27
 */
public class LeetCode_0700_Solution_01 {

    public TreeNode searchBST(TreeNode root, int val) {
        for (; root != null && root.val != val; root = val <= root.val ? root.left : root.right);
        return root;
    }

    public static void main(String[] args) {
        String str = "4,2,1,#,#,3,#,#,7,#,#";
        TreeNode root = LeetCodeUtil.generateByPreOrder(str);
        LeetCodeUtil.printPreOrder(root);

        LeetCode_0700_Solution_01 o = new LeetCode_0700_Solution_01();
        TreeNode result = o.searchBST(root, 2);
        LeetCodeUtil.printPreOrder(result);
    }
}
