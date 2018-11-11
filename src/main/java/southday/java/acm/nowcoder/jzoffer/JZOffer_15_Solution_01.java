package southday.java.acm.nowcoder.jzoffer;

/* 反转链表 
输入一个链表，反转链表后，输出新链表的表头。 
*/

public class JZOffer_15_Solution_01 {
    
    public ListNode ReverseList(ListNode head) {
        ListNode newHead = new ListNode(0);
        ListNode q = null;
        newHead.next = null;
        while (head != null) {
            q = head.next;
            head.next = newHead.next;
            newHead.next = head;
            head = q;
        }
        return newHead.next;
    }
}
