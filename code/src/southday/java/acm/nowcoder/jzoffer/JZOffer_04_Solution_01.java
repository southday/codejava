package southday.java.acm.nowcoder.jzoffer;

/* 重建二叉树 
输入某二叉树的前序遍历和中序遍历的结果，
请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
*/

public class JZOffer_04_Solution_01 {
    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        int len = pre.length;
        return constructBinaryTree(pre, 0, len-1, in, 0, len-1);
     }
    
     private TreeNode constructBinaryTree(int[] pre, int pl, int pr, int[] in, int il, int ir) {
        if (pl == pr || il == ir) {
            TreeNode root = new TreeNode(pre[pl]);
            root.left = null;
            root.right = null;
            return root;
        } else if (pl > pr || il > ir) {
            return null;
        }
        int r = il, val = pre[pl];
        while (r <= ir && val != in[r]) r++;
        TreeNode root = new TreeNode(pre[pl]);
        TreeNode leftChild = constructBinaryTree(pre, pl+1, pl+r-il, in, il, r-1);
        TreeNode rightChild = constructBinaryTree(pre, pl+r-il+1, pr, in, r+1, ir);
        root.left = leftChild;
        root.right = rightChild;
        return root;
     }
}
