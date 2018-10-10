package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/* 按之字形顺序打印二叉树 
请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，第三行按照从左到右的顺序打印，其他行以此类推。
*/

public class JZOffer_59_Solution_01 {
    @SuppressWarnings("unchecked")
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if (pRoot == null) return result;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(pRoot);
        TreeNode r = pRoot, q = pRoot;
        boolean direction = true; // true 左至右 |  false 右至左
        Stack<Integer> stack = new Stack<Integer>();
        ArrayList<Integer> ls = new ArrayList<Integer>();
        while (!queue.isEmpty()) {
            TreeNode t = queue.poll();
            if (direction == true) { // 左至右
                ls.add(t.val);
            } else { // 右至左
                stack.push(t.val);
            }
            if (t.left != null) {
                queue.add(t.left);
                q = t.left;
            }
            if (t.right != null) {
                queue.add(t.right);
                q = t.right;
            }
            if (t == r) { // 遍历完一层
                if (direction == true) {
                    result.add((ArrayList<Integer>)ls.clone());
                    ls.clear();
                } else {
                    ArrayList<Integer> tmp = new ArrayList<Integer>();
                    while (!stack.isEmpty()) {
                        tmp.add(stack.pop());
                    }
                    result.add(tmp);
                }
                direction = direction ? false : true;
                r = q;
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        boolean f = true;
        f = f? false : true;
    }
}
