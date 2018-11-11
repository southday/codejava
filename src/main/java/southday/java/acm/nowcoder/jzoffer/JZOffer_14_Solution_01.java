package southday.java.acm.nowcoder.jzoffer;

/* 链表中倒数第k个结点 
输入一个链表，输出该链表中倒数第k个结点 

使用两个指针，相距k-1，当最右边的移动到末尾，那左边的就是倒数第k个
*/

public class JZOffer_14_Solution_01 {

    public ListNode FindKthToTail(ListNode head,int k) {
        if (head == null) return null;
        ListNode p = head, q = head;
        int cnt = 0;
        while (q != null && cnt < k) {
            q = q.next;
            cnt++;
        }
        if (q == null && cnt < k) return null;
        while (q != null) {
            p = p.next;
            q = q.next;
        }
        return p;
    }
}
