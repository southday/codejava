package southday.java.acm.leetcode;

import org.junit.Before;
import org.junit.Test;
import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.TreeNode;

/**
 * @author southday
 * @date 2019/3/15
 */
public class LeetCodeUtilTest {
    TreeNode root = null;

    @Before
    public void init() {
//        String str = "3,5,6,#,#,2,7,#,#,4,#,#,1,0,#,#,8,#,#";
//        String str = "3,5,6,#,#,2,7,#,#,#,1,0,#,#,8,#,#";
        String str = "3,5,6,#,#,2,7,#,#,#,1,#,8,#,#";
        root = LeetCodeUtil.generateByPreOrder(str);
    }

    @Test
    public void preOrderTest() {
        System.out.print("preOrder: ");
        LeetCodeUtil.preOrderNonRec(root);
        System.out.println();
    }

    @Test
    public void inOrderTest() {
        System.out.print("inOrder: ");
        LeetCodeUtil.inOrderNonRec(root);
        System.out.println();
    }

    @Test
    public void postOrderTest() {
        System.out.print("postOrder: ");
        LeetCodeUtil.postOrderNonRec(root);
        System.out.println();
    }
}
