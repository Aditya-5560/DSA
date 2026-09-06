# Permute Arrays for Sum Threshold

- Platform: GeeksforGeeks
- Language: class Solution { public: static bool comparator (int a , int b) { return a > b ; // sort in decending order } bool isPossible(int k, vector<int> &arr1, vector<int> &arr2) { // Your code goes here if(arr1.size() > arr1.size()) return false; sort(arr1.begin(),arr1.end());// sort in acending order sort(arr2.begin() ,arr2.end() , comparator); // sort in decending order for(int i= 0 ; i < arr1.size() ; i++){ if(arr1[i] + arr2[i] < k ) return false; } return true; } };
- Difficulty: Unknown
- Topics: Expected Complexities, Topic Tags, Arrays, Sorting, Related Articles
- Runtime: N/A
- Memory: N/A
- Problem URL: https://www.geeksforgeeks.org/problems/permutations-in-array1747/1
- Synced: 2026-09-06T20:38:51.903Z

## Problem Description

Given two arrays a[], b[], and an integer k, find if it is possible to permute both arrays such that for every index i, arr1[i] + arr2[i] ≥ k. Examples: Input: k = 10, a[] = [2, 1, 3], b[] = [7, 8, 9]. Output: true Explanation: Permutation arr1[] = [1, 2, 3] and arr2[] = [9, 8, 7] satisfy the condition arr1[i] + arr2[i] >= k Input: k = 5, a[] = [1, 2, 2, 1], b[] = [3, 3, 3, 4]. Output: false Explanation: Since any permutation won't give the answer. Constraints: 1 ≤ arr.size() ≤ 105 0 ≤ k ≤ 105 0 ≤ arr1[i], arr2[i]≤ 2*105

## Explanation

This solution was accepted on GeeksforGeeks using class Solution { public: static bool comparator (int a , int b) { return a > b ; // sort in decending order } bool isPossible(int k, vector<int> &arr1, vector<int> &arr2) { // Your code goes here if(arr1.size() > arr1.size()) return false; sort(arr1.begin(),arr1.end());// sort in acending order sort(arr2.begin() ,arr2.end() , comparator); // sort in decending order for(int i= 0 ; i < arr1.size() ; i++){ if(arr1[i] + arr2[i] < k ) return false; } return true; } };. The detected topics are Expected Complexities, Topic Tags, Arrays, Sorting, Related Articles. Review the synced source file for the implementation details.
