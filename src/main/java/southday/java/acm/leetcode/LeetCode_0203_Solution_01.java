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
        ListNode p = new ListNode(0);
        p.next = head;
        head = p;
        while (head.next != null)
            if (head.next.val == val)
                head.next = head.next.next;
            else
                head = head.next;
        return p.next;
    }
    
    public ListNode generate(int[] ls) {
        ListNode p = new ListNode(0);
        ListNode q = p;
        for (int a : ls) {
            p.next = new ListNode(a);
            p = p.next;
        }
        p.next = null;
        return q.next;
    }
    
    public void print(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        LeetCode_0203_Solution_01 o = new LeetCode_0203_Solution_01();
        int[] arr = {1, 2, 4, 5, 4, 6};
        ListNode head = o.generate(arr);
        o.print(head);
        ListNode nh = o.removeElements(head, 4);
        o.print(nh);
    }
    
}
