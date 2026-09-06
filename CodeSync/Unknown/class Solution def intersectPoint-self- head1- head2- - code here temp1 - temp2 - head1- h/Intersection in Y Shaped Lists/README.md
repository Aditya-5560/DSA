# Intersection in Y Shaped Lists

- Platform: GeeksforGeeks
- Language: class Solution: def intersectPoint(self, head1, head2): # code here temp1 , temp2 = head1, head2 while temp1 != temp2: temp1 = temp1.next temp2 = temp2.next if temp1 == temp2: return temp1 # change pointers to opposite heads if temp1 is None: temp1 = head2 if temp2 is None: temp2 = head1 return temp1
- Difficulty: Unknown
- Topics: Expected Complexities, Company Tags, VMWare, Flipkart, Accolite, Amazon, Microsoft, Snapdeal
- Runtime: N/A
- Memory: N/A
- Problem URL: https://www.geeksforgeeks.org/problems/intersection-point-in-y-shapped-linked-lists/1
- Synced: 2026-09-06T18:10:41.593Z

## Problem Description

Given the heads of two non-empty singly linked lists, head1 and head2, return the node where the two linked lists intersect. It is guaranteed that an intersection always exists. Note: The custom input contains a non-empty list common. Initially, head1 and head2 do not share any node. The last node of each list is then connected to the head of common, creating an intersection at the first node of common. Examples: Input: head1: 10 -> 15 -> 30, head2: 3 -> 6 -> 9 -> 15 -> 30 Output: 15 Explanation: From the image, it is clear that the common part is 15 -> 30, and its starting node is 15. Input: head1: 4 -> 1 -> 8 -> 5, head2: 5 -> 6 -> 1 -> 8 -> 5 Output: 1 Explanation: From the image, it is clear that the common part is 1 -> 8 -> 5, and its starting node is 1. Constraints: 2 ≤ total number of nodes ≤ 2*105 -104 ≤ node->data ≤ 104

## Explanation

This solution was accepted on GeeksforGeeks using class Solution: def intersectPoint(self, head1, head2): # code here temp1 , temp2 = head1, head2 while temp1 != temp2: temp1 = temp1.next temp2 = temp2.next if temp1 == temp2: return temp1 # change pointers to opposite heads if temp1 is None: temp1 = head2 if temp2 is None: temp2 = head1 return temp1. The detected topics are Expected Complexities, Company Tags, VMWare, Flipkart, Accolite, Amazon, Microsoft, Snapdeal. Review the synced source file for the implementation details.
