package southday.java.acm.leetcode.solution;

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.ListNode;

/*
21. Merge Two Sorted Lists  https://leetcode.com/problems/merge-two-sorted-lists/description/
Merge two sorted linked lists and return it as a new list.
The new list should be made by splicing together the nodes of the first two lists.

Example:
Input: 1->2->4, 1->3->4
Output: 1->1->2->3->4->4

 */

/**
 * Author southday
 * Date   2018/11/13
 */
public class LeetCode_0021_Solution_01 {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode nd = new ListNode(0);
        ListNode p = nd;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }
        p.next = l1 == null ? l2 : l1;
        return nd.next;
    }

    public static void main(String[] args) {
        LeetCode_0021_Solution_01 o = new LeetCode_0021_Solution_01();
        int arr1[] = {1, 2, 4};
        int arr2[] = {1, 3, 4};
        ListNode l1 = LeetCodeUtil.generate(arr1);
        ListNode l2 = LeetCodeUtil.generate(arr2);
        ListNode nd = o.mergeTwoLists(l1, l2);
        LeetCodeUtil.printListNodes(nd);
    }
}
