package southday.java.acm.nowcoder.jzoffer;

/* 二叉树的深度 
输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。

使用深度优先遍历求解
*/

public class JZOffer_38_Solution_01 {
    int maxDepth = 0;
    
    public int TreeDepth(TreeNode root) {
        DFS(root, 1);
        return maxDepth;
    }
    
    private void DFS(TreeNode root, int depth) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                if (depth > maxDepth) maxDepth = depth;
                return;
            }
            if (root.left != null) DFS(root.left, depth+1);
            if (root.right != null) DFS(root.right, depth+1);
        }
    }
}
