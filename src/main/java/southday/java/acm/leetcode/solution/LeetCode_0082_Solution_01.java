package southday.java.acm.leetcode.solution;

/* 82. Remove Duplicates from Sorted List II https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

Example 1:
Input: 1->2->3->3->4->4->5
Output: 1->2->5

Example 2:
Input: 1->1->1->2->3
Output: 2->3
 */

import southday.java.acm.leetcode.common.ListNode;

/**
 * @author southday
 * @date 2019/6/11
 */
public class LeetCode_0082_Solution_01 {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return null;
        ListNode rt = new ListNode(1);
        ListNode p = head, q = head.next, r = rt;
        while (p != null && q != null) {
            if (p.val != q.val) {
                r.next = p;
                r = r.next;
                p = q;
                q = q.next;
            } else {
                for (; q != null && q.next != null && q.val == q.next.val; q = q.next);
                if (q != null) {
                    p = q.next;
                    if (p != null) {
                        q = p.next;
                    }
                }
            }
        }
        r.next = p;
        return rt.next;
    }
}
