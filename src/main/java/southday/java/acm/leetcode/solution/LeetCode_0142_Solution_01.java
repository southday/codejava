package southday.java.acm.leetcode.solution;

/* 142. Linked List Cycle II  https://leetcode.com/problems/linked-list-cycle-ii/description/
 Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
Note: Do not modify the linked list.
Follow up:
Can you solve it without using extra space?

解题思路：
1) 快慢指针：fast（1次移动2位），slow（1次移动1位）
2) 如果链表有环则快慢指针总能相遇
3) 设：
    a) 循环开始于 x 结点，快慢指针相遇于 w 结点
    b) 从 head 结点到 x 结点的距离为 A
    c) 从 x 结点到 w 结点的距离为 B
    e) 快指针所走过的路程为 dis(fast) = C
    f) 环的长度为 L
则有：
    a) dis(slow) = A + B
    b) dis(fast) = 2 * dis(slow) <=> C = 2(A+B)
    c) dis(fast) - dis(slow) = n * L (即快指针在环中多走了n圈)
    d) A + B = n * L (即：(A + B) % L = 0)
说明：
    a) 慢指针 slow 继续走 A 的距离，刚好能走完一个完整的环，即到达 x 结点
    b) 表头 head 走 A 的距离，刚好能到达 x 结点
    c) 即：slow 和 head 同时单步走，二者相等时，即为循环开始的结点 x
 */

import southday.java.acm.leetcode.common.LeetCodeUtil;
import southday.java.acm.leetcode.common.ListNode;

/**
 * Author southday
 * Date   2018/11/21
 */
public class LeetCode_0142_Solution_01 {

    public ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                for (; slow != head; slow = slow.next, head = head.next);
                return slow;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LeetCode_0142_Solution_01 o = new LeetCode_0142_Solution_01();
        int arr[] = {-1,-7,7,-4,19,6,-9,-5,-2,-5};
        int cyc = 9;
        ListNode head = LeetCodeUtil.generate(arr);
        ListNode tailNode = LeetCodeUtil.last(head);
        head = LeetCodeUtil.tailConnectTo(head, cyc);
        System.out.println("tailNode.next.val = " + tailNode.next.val + ", index: " + LeetCodeUtil.indexOf(head, tailNode.next));
        ListNode cycNode = o.detectCycle(head);
        System.out.println("cycNode.val = " + cycNode.val + ", index: " + LeetCodeUtil.indexOf(head, cycNode));
    }
}
