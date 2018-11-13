package southday.java.acm.leetcode;

/*
876. Middle of the Linked List  https://leetcode.com/problems/middle-of-the-linked-list/description/

Given a non-empty, singly linked list with head node head, return a middle node of linked list.
If there are two middle nodes, return the second middle node.

Example 1:

Input: [1,2,3,4,5]
Output: Node 3 from this list (Serialization: [3,4,5])
The returned node has value 3.  (The judge's serialization of this node is [3,4,5]).
Note that we returned a ListNode object ans, such that:
ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, and ans.next.next.next = NULL.

Example 2:

Input: [1,2,3,4,5,6]
Output: Node 4 from this list (Serialization: [4,5,6])
Since the list has two middle nodes with values 3 and 4, we return the second one.

Note:
    The number of nodes in the given list will be between 1 and 100.

 */

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.ListNode;

import java.util.ArrayList;

/**
 * Author southday
 * Date   2018/11/13
 */
public class LeetCode_0876_Solution_01 {

    public ListNode middleNode(ListNode head) {
        ArrayList<ListNode> ls = new ArrayList<>();
        while (head != null) {
            ls.add(head);
            head = head.next;
        }
        return ls.get(ls.size()>>1);
    }

    public static void main(String[] args) {
        LeetCode_0876_Solution_01 o = new LeetCode_0876_Solution_01();
        int arr[] = {1, 2, 3, 4 ,5};
        ListNode nd = LeetCodeUtil.generate(arr);
        ListNode p = o.middleNode(nd);
        LeetCodeUtil.printListNodes(p);

        int arr2[] = {1, 2, 3, 4, 5, 6};
        ListNode nd2 = LeetCodeUtil.generate(arr2);
        ListNode p2 = o.middleNode(nd2);
        LeetCodeUtil.printListNodes(p2);
    }
}
