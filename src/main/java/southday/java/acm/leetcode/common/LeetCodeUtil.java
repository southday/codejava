package southday.java.acm.leetcode.common;

/**
 * Author southday
 * Date   2018/11/13
 */
public class LeetCodeUtil {
    public static final String NULL_SIGN = "#";
    public static final String SEPARATOR = ",";

    /**
     * 根据数组array[]生成链表
     * @param array
     * @return 链表首结点（不含头结点）
     */
    public static ListNode generate(int[] array) {
        ListNode p = new ListNode(0);
        ListNode q = p;
        for (int a : array) {
            p.next = new ListNode(a);
            p = p.next;
        }
        p.next = null;
        return q.next;
    }

    /**
     * 打印ListNode
     * @param head
     */
    public static void printListNodes(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * 将链表尾节点tail连接到链中某一节点(cycNode)
     * @param head
     * @param cyc cycNode的角标(从1开始)
     * @return
     */
    public static ListNode tailConnectTo(ListNode head, int cyc) {
        if (head == null)
            return null;
        ListNode tail = head;
        ListNode cycNode = null;
        for (int i = 1; tail.next != null; tail = tail.next, i++)
            if (cyc == i)
                cycNode = tail;
        tail.next = cycNode;
        return head;
    }

    /**
     * 返回一个指定结点nd在某个链表中的位置(角标)
     * @param head
     * @param nd
     * @return 返回index(从1开始)
     */
    public static int indexOf(ListNode head, ListNode nd) {
        for (int i = 1; head != null; i++, head = head.next)
            if (head == nd)
                return i;
        return -1;
    }

    /**
     * 返回末尾结点
     * @param head
     * @return
     */
    public static ListNode last(ListNode head) {
        if (head == null)
            return null;
        for (; head.next != null; head = head.next);
        return head;
    }

    /**
     * 通过先序遍历来生成二叉树
     * @param str 前序遍历的字符串，用","隔开，如："1,3,5,#,#,#,2,#,#"
     * @return
     */
    public static TreeNode generateByPreOrder(String str) {
        if (str == null || "".equals(str.trim()))
            return null;
        int index[] = {0};
        String preOrder[] = str.split(SEPARATOR);
        return generateByPreOrder(preOrder, index);
    }

    private static TreeNode generateByPreOrder(String preOrder[], int index[]) {
        if (index[0] >= preOrder.length)
            return null;
        if (NULL_SIGN.equals(preOrder[index[0]])) {
            index[0]++;
            return null;
        }
        TreeNode nd = new TreeNode(Integer.parseInt(preOrder[index[0]++]));
        nd.left = generateByPreOrder(preOrder, index);
        nd.right = generateByPreOrder(preOrder, index);
        return nd;
    }

    /**
     * 打印二叉树的先序遍历
     * @param t
     */
    public static void printPreOrder(TreeNode t) {
        printPreOrder1(t);
        System.out.println();
    }

    /**
     * 打印二叉树的先序遍历
     * @param t
     */
    private static void printPreOrder1(TreeNode t) {
        if (t == null)
            System.out.print(NULL_SIGN + " ");
        else {
            System.out.print(t.val + " ");
            printPreOrder1(t.left);
            printPreOrder1(t.right);
        }
    }
}
