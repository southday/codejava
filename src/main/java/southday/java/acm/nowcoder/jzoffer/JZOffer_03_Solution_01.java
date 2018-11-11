package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;

/*  从尾到头打印链表 
输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。 
*/

public class JZOffer_03_Solution_01 {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        // 头插法
        if (listNode == null) {
            return new ArrayList<Integer>();
        }
        ListNode head = new ListNode(0);
        ListNode tmp = null;
        while (listNode != null) {
            tmp = listNode.next;
            listNode.next = head.next;
            head.next = listNode;
            listNode = tmp;
        }
        ArrayList<Integer> lis = new ArrayList<Integer>();
        head = head.next;
        while (head != null) {
            lis.add(head.val);
            head = head.next;
        }
        return lis;
    }
}
