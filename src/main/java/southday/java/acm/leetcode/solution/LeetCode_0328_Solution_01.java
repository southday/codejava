package southday.java.acm.leetcode.solution;

/* 328. Odd Even Linked List  https://leetcode.com/problems/odd-even-linked-list/description/
Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are talking about the node number and not the value in the nodes.
You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.

Example 1:
Input: 1->2->3->4->5->NULL
Output: 1->3->5->2->4->NULL

Example 2:
Input: 2->1->3->5->6->4->7->NULL
Output: 2->3->6->7->1->5->4->NULL

Note:
    The relative order inside both the even and odd groups should remain as it was in the input.
    The first node is considered odd, the second node even and so on ...

 */

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.ListNode;

/**
 * Author southday
 * Date   2018/11/21
 */
public class LeetCode_0328_Solution_01 {
    public ListNode oddEvenList(ListNode head) {
        ListNode evenHead = new ListNode(0);
        ListNode oddHead = new ListNode(0);
        ListNode odd = oddHead, even = evenHead;
        odd.next = even.next = head;
        for (int i = 1; head != null; i++, head = head.next) {
            if ((i & 1) == 1) {
                odd.next = head;
                odd = odd.next;
            } else {
                even.next = head;
                even = even.next;
            }
        }
        even.next = null;
        odd.next = evenHead.next;
        return oddHead.next;
    }

    public static void main(String[] args) {
        LeetCode_0328_Solution_01 o = new LeetCode_0328_Solution_01();
        ListNode t1 = LeetCodeUtil.generate(new int[] {1, 2, 3, 4, 5});
        LeetCodeUtil.printListNodes(t1);
        ListNode r1 = o.oddEvenList(t1);
        LeetCodeUtil.printListNodes(r1);
        System.out.println("----------------------------------");
        ListNode t2 = LeetCodeUtil.generate(new int[] {2, 1, 3, 5, 6, 4, 7});
        LeetCodeUtil.printListNodes(t2);
        ListNode r2 = o.oddEvenList(t2);
        LeetCodeUtil.printListNodes(r2);
    }
}
