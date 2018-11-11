package southday.java.acm.nowcoder.jzoffer;

/* 二叉搜索树的后序遍历序列 
输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。

二叉查找树（Binary Search Tree），（又：二叉搜索树，二叉排序树）它或者是一棵空树，
或者是具有下列性质的二叉树： 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值； 
若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值； 它的左、右子树也分别为二叉排序树。 
*/

public class JZOffer_23_Solution_01 {
    
    public boolean VerifySquenceOfBST(int [] sequence) {
        // 按照BST的定义，一颗BST可以是空树，所以sequence=[]时，应该返回true，而网站上要求返回false
        return sequence.length == 0 ? false : check(sequence, 0, sequence.length-1);
    }
    
    private boolean check(int[] sequence, int left, int right) {
        if (left >= right) {
            return true;
        }
        int root = sequence[right];
        int i = left;
        while (i < right && sequence[i] < root) i++;
        int j = i;
        while (j < right && sequence[j] > root) j++;
        if (j == right) {
            return check(sequence, left, i-1) && check(sequence, i, right-1);
        } else {
            return false;
        }
    }
}
