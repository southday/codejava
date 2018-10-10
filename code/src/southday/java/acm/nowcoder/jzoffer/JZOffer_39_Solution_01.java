package southday.java.acm.nowcoder.jzoffer;

/* 平衡二叉树 
输入一棵二叉树，判断该二叉树是否是平衡二叉树。
它是一 棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
*/

public class JZOffer_39_Solution_01 {
    
    public boolean IsBalanced_Solution(TreeNode root) {
        return isAVLTree(root) < 0 ? false : true;
    }
    
    // 返回当前节点的高度，如果不是AVL树则返回-1
    private int isAVLTree(TreeNode root) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                return 1;
            }
            int lh = 0, rh = 0;
            if (root.left != null) {
                lh = isAVLTree(root.left);
            }
            if (root.right != null) {
                rh = isAVLTree(root.right);
            }
            if (lh == -1 || rh == -1) return -1;
            if (lh == rh || lh-1 == rh || lh+1 == rh) {
                return lh > rh ? lh+1 : rh+1;
            } else {
                return -1;
            }
        }
        return 0;
    }
}
