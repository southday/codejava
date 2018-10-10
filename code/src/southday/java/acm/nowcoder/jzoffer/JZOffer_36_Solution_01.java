package southday.java.acm.nowcoder.jzoffer;

import java.util.HashMap;

/* 两个链表的第一个公共结点 
输入两个链表，找出它们的第一个公共结点。
*/

public class JZOffer_36_Solution_01 {
    
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) return null;
        HashMap<ListNode, Boolean> map = new HashMap<ListNode, Boolean>();
        while (pHead1 != null) {
            map.put(pHead1, true);
            pHead1 = pHead1.next;
        }
        while (pHead2 != null && map.get(pHead2) == null) pHead2 = pHead2.next;
        return pHead2;
    }
}
