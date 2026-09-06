# Multiply two numbers represented by Linked Lists

- Platform: GeeksforGeeks
- Language: #include <iostream> using namespace std; // Structure of a linked list node class Node { public: int data; Node *next; Node(int x) { data = x; next = nullptr; } }; // Function to multiply two numbers represented by linked lists int multiplyTwoLists(Node *first, Node *second) { const long long MOD = 1000000007; // Variables to store the numbers represented by the linked lists long long num1 = 0, num2 = 0; // Traverse the first linked list and construct the first number while (first != nullptr) { num1 = num1 * 10 + first->data; first = first->next; } // Traverse the second linked list and construct the second number while (second != nullptr) { num2 = num2 * 10 + second->data; second = second->next; } // Return the product modulo MOD // Note: This multiplication may overflow for very large numbers. return (num1 * num2) % MOD; } int main() { // Create first linked list: 9 -> 4 -> 6 Node *first = new Node(9); first->next = new Node(4); first->next->next = new Node(6); // Create second linked list: 8 -> 4 Node *second = new Node(8); second->next = new Node(4); cout << multiplyTwoLists(first, second) << endl; return 0; }
- Difficulty: Unknown
- Topics: Placement 360 Course Explore, Placement 360 Course, Explore
- Runtime: N/A
- Memory: N/A
- Problem URL: https://www.geeksforgeeks.org/dsa/multiply-two-numbers-represented-linked-lists/
- Synced: 2026-09-06T19:05:50.591Z

## Problem Description

Problem description was not available on the page at sync time.

## Explanation

This solution was accepted on GeeksforGeeks using #include <iostream> using namespace std; // Structure of a linked list node class Node { public: int data; Node *next; Node(int x) { data = x; next = nullptr; } }; // Function to multiply two numbers represented by linked lists int multiplyTwoLists(Node *first, Node *second) { const long long MOD = 1000000007; // Variables to store the numbers represented by the linked lists long long num1 = 0, num2 = 0; // Traverse the first linked list and construct the first number while (first != nullptr) { num1 = num1 * 10 + first->data; first = first->next; } // Traverse the second linked list and construct the second number while (second != nullptr) { num2 = num2 * 10 + second->data; second = second->next; } // Return the product modulo MOD // Note: This multiplication may overflow for very large numbers. return (num1 * num2) % MOD; } int main() { // Create first linked list: 9 -> 4 -> 6 Node *first = new Node(9); first->next = new Node(4); first->next->next = new Node(6); // Create second linked list: 8 -> 4 Node *second = new Node(8); second->next = new Node(4); cout << multiplyTwoLists(first, second) << endl; return 0; }. The detected topics are Placement 360 Course Explore, Placement 360 Course, Explore. Review the synced source file for the implementation details.
