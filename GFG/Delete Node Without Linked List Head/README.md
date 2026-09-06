# Delete Node Without Linked List Head

- Platform: GeeksforGeeks
- Language: class Solution: #Function to delete a node without any reference to head pointer. def deleteNode(self,del_node): #code here del_node.data = del_node.next.data del_node.next = del_node.next.next
- Difficulty: Unknown
- Topics: Expected Complexities, Company Tags, Amazon, Microsoft, Samsung, Visa, Goldman Sachs, Kritikal Solutions
- Runtime: N/A
- Memory: N/A
- Problem URL: https://www.geeksforgeeks.org/problems/delete-without-head-pointer/1
- Synced: 2026-09-06T19:09:39.272Z

## Problem Description

Given a node x in a singly linked list, delete this node without access to the head of the linked list. The driver code will print the updated linked list to verify the deletion. Note: It is guaranteed that x is not the last node of the linked list. Examples: Input: x = 1 Output: 2 Explanation: After deleting 1 from the linked list, only 2 remains. Input: x = 20 Output: 10 -> 4 -> 30 Explanation: After deleting 20 from the linked list, we have remaining nodes as 10, 4, 30.

## Explanation

This solution was accepted on GeeksforGeeks using class Solution: #Function to delete a node without any reference to head pointer. def deleteNode(self,del_node): #code here del_node.data = del_node.next.data del_node.next = del_node.next.next. The detected topics are Expected Complexities, Company Tags, Amazon, Microsoft, Samsung, Visa, Goldman Sachs, Kritikal Solutions. Review the synced source file for the implementation details.
