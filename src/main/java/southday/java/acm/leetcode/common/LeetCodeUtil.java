package southday.java.acm.leetcode.common;

/**
 * Author southday
 * Date   2018/11/13
 */
public class LeetCodeUtil {

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
}
