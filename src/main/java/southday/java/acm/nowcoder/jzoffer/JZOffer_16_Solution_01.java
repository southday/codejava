package southday.java.acm.nowcoder.jzoffer;

/* 合并两个排序的链表 
输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
*/

public class JZOffer_16_Solution_01 {
    
    public ListNode Merge(ListNode list1,ListNode list2) {
        ListNode head = new ListNode(0);
        ListNode p = list1, q = list2, t = head;
        while (p != null && q != null) {
            if (p.val <= q.val) {
                t.next = p;
                p = p.next;
            } else {
                t.next = q;
                q = q.next;
            }
            t = t.next;
        }
        t.next = null;
        if (p != null) t.next = p;
        if (q != null) t.next = q;
        return head.next;
    }
}
