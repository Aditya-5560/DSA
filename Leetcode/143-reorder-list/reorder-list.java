/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public void reorderList(ListNode head) {
        //Get Mid
        ListNode slow = head;
        ListNode fast = head;
        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        //Split
        ListNode l1 = head;
        ListNode l2 = slow.next;
        slow.next = null;
        //Reverse second half
        ListNode curr = l2;
        ListNode prev = null;
        ListNode nxt;
        while(curr!=null){
            nxt = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nxt;
        }
        l2 = prev;
        //Add both in ans ll
        while(l1!=null && l2!=null){
            ListNode n1 = l1.next;
            ListNode n2 = l2.next;
            l1.next = l2;
            l2.next = n1;
            l1=n1;
            l2=n2;
        }
    }
}