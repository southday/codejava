package southday.java.acm.leetcode.solution;

/*
654. Maximum Binary Tree  https://leetcode.com/problems/maximum-binary-tree/description/
 Given an integer array with no duplicates. A maximum tree building on this array is defined as follow:
    The root is the maximum number in the array.
    The left subtree is the maximum tree constructed from left part subarray divided by the maximum number.
    The right subtree is the maximum tree constructed from right part subarray divided by the maximum number.
Construct the maximum tree by the given array and output the root node of this tree.

Example 1:
Input: [3,2,1,6,0,5]
Output: return the tree root node representing the following tree:

      6
    /   \
   3     5
    \    /
     2  0
       \
        1
Note:
    The size of the given array will be in the range [1,1000].

 */

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.TreeNode;

/**
 * Author southday
 * Date   2018/12/4
 */
public class LeetCode_0654_Solution_01 {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return construct(nums, 0, nums.length-1);
    }

    private TreeNode construct(int[] nums, int left, int right) {
        if (left > right)
            return null;
        int maxi = left;
        for (int i = left+1; i <= right; i++)
            if (nums[maxi] < nums[i])
                maxi = i;
        TreeNode root = new TreeNode(nums[maxi]);
        root.left = construct(nums, left,maxi-1);
        root.right = construct(nums,maxi+1, right);
        return root;
    }

    public static void main(String[] args) {
        LeetCode_0654_Solution_01 o = new LeetCode_0654_Solution_01();
        int nums[] = {3,2,1,6,0,5};
        TreeNode nd = o.constructMaximumBinaryTree(nums);
        LeetCodeUtil.printPreOrder(nd);
    }
}
