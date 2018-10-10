package southday.java.acm.nowcoder.jzoffer;

import java.util.HashMap;

/* 删除链表中重复的结点 
在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5

该算法运行超时
*/

public class JZOffer_56_Solution_01 {

    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null) return null;
        HashMap<ListNode, Boolean> map = new HashMap<ListNode, Boolean>();
        ListNode p = pHead;
        while (p != null) {
            if (map.get(p) == null) {
                map.put(p, false);
            } else {
                map.put(p, true);
            }
        }
        p = pHead;
        while (p != null && map.get(p) == true) p = p.next;
        if (p == null) return null;
        pHead = p;
        ListNode q = p.next;
        while (q != null) {
            while (q != null && map.get(q) == true) q = q.next;
            if (q == null) return pHead;
            p.next = q;
            p = q;
            q = q.next;
        }
        return pHead;
    }
}
