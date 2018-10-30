package southday.java.acm.leetcode;

import southday.java.acm.leetcode.common.ListNode;

/* 
203. Remove Linked List Elements https://leetcode.com/problems/remove-linked-list-elements/description/

Remove all elements from a linked list of integers that have value val.

Example:
Input:  1->2->6->3->4->5->6, val = 6
Output: 1->2->3->4->5

*/

/*
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

/**
 * @author southday
 * @date 2018年10月30日
 */
public class LeetCode_0203_Solution_01 {
    public ListNode removeElements(ListNode head, int val) {
        ListNode q = new ListNode(0);
        q.next = head;
        ListNode p = q;
        while (p.next != null)
            if (p.next.val == val)
                p.next = p.next.next;
            else
                p = p.next;
        return q.next;
    }
}
