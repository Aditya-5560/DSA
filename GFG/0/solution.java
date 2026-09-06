/*
 * Platform: LeetCode
 * Problem: 0
 * URL: https://leetcode.com/problems/linked-list-cycle/description/
 * Language: Java
 * Difficulty: Easy
 * Topics: Hash Table, Linked List, Two Pointers, Floyd's Cycle Finding Algorithm
 * Runtime: N/A
 * Memory: N/A
 * Synced: 2026-09-06T20:32:32.076Z
 */

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while(fast!=null && fast.next!=null){
