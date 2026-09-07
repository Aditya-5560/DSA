/*
 * Platform: GeeksforGeeks
 * Problem: Reverse Array
 * URL: https://www.geeksforgeeks.org/problems/reverse-an-array/1
 * Language: Java
 * Difficulty: Easy
 * Topics: Bloomberg, Facebook, TCS, Adobe, Google, Infosys, Capgemini, Morgan Stanley
 * Runtime: 1.1 s
 * Memory: N/A
 * Synced: 2026-09-07T07:02:54.609Z
 */

class Solution {
    public void reverseArray(int arr[]) {
        // code here
        int l= 0;
        int r=arr.length-1;
        while(l<r){
            int temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            l++;
            r--;
        }
    }
}
