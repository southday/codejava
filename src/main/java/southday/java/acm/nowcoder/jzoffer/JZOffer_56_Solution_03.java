package southday.java.acm.nowcoder.jzoffer;

/* 删除链表中重复的结点 
在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5

使用递归，测试通过
*/

public class JZOffer_56_Solution_03 {

    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) return pHead;
        ListNode q = pHead.next;
        while (q != null) {
            while (q != null && q.val == pHead.val) q = q.next;
            if (q == null) return null;
            if (q == pHead.next) {
                pHead.next = deleteDuplication(q);
                break;
            } else {
                return deleteDuplication(q);
            }
        }
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
//        int[] arr = {1,3};
//        int[] arr = {1,2,3,3,4,4,5};
//        int[] arr = {1,2,2,2,2,2,3,4,4,4,5,5,5,5,5,5,5,5,5,5,8,9,9,9,9,9,9,22,22,23,34,55};
        int[] arr = {1,1,1};
        ListNode head = construct(arr);
        sop(head);
        JZOffer_56_Solution_03 o = new JZOffer_56_Solution_03();
        ListNode dhead = o.deleteDuplication(head);
        sop(dhead);
    }
}

