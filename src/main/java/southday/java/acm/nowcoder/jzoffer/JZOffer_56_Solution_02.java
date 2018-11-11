package southday.java.acm.nowcoder.jzoffer;

/* 删除链表中重复的结点 
在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5

O(n)算时间复杂度，测试结果显示超时
*/

public class JZOffer_56_Solution_02 {

    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null) return null;
        ListNode pre = pHead, p = pHead, q = pHead.next;
        if (q == null) return pHead;
        while (q != null) {
            while (q != null && q.val == p.val) q = q.next;
            if (q == p.next) {
                if (p != pHead) {
                    pre.next = p;
                    pre = p;
                }
                p = q;            
                q = q.next;
            } else if (q == null) {
                if (p == pHead) return null;
                else p.next = null;
            } else {
                if (p == pHead) {
                    pHead = q;
                    pre = q;
                }
                p = q;
                q = q.next;
            }
        }
        pre.next = p;
        return pHead;
    }
    
    public static ListNode construct(int[] arr) {
        ListNode head = new ListNode(arr[0]);
        ListNode p = head;
        for (int i = 1; i < arr.length; i++) {
            ListNode q = new ListNode(arr[i]);
            p.next = q;
            p = q;
        }
        p.next = null;
        return head;
    }
    
    public static void sop(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println("\n-----------------------------");
    }
    
    public static void main(String[] args) {
//        int[] arr = {1,1,2,2,3,5,5,7};
        int[] arr = {1,3};
//        int[] arr = {1,2,3,3,4,4,5};
//        int[] arr = {1,2,2,2,2,2,3,4,4,4,5,5,5,5,5,5,5,5,5,5,8,9,9,9,9,9,9,22,22,23,34,55};
        ListNode head = construct(arr);
        sop(head);
        JZOffer_56_Solution_02 o = new JZOffer_56_Solution_02();
        ListNode dhead = o.deleteDuplication(head);
        sop(dhead);
    }
}
