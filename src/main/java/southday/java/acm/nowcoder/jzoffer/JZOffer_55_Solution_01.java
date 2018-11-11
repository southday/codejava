package southday.java.acm.nowcoder.jzoffer;

import java.util.HashMap;

/* 链表中环的入口结点 
 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
*/

public class JZOffer_55_Solution_01 {
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null) return null;
        HashMap<ListNode, Boolean> map = new HashMap<ListNode, Boolean>();
        while (pHead != null) {
            if (map.get(pHead) != null) {
                return pHead;
            } else {
                map.put(pHead, true);
                pHead = pHead.next;
            }
        }
        return null;
    }
}
