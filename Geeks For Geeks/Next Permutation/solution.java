/*
 * Platform: GeeksforGeeks
 * Problem: Next Permutation
 * URL: https://www.geeksforgeeks.org/problems/next-permutation5226/1
 * Language: Java
 * Difficulty: Medium
 * Topics: Infosys, Flipkart, Amazon, Microsoft, FactSet, Hike, MakeMyTrip, Google
 * Runtime: N/A
 * Memory: N/A
 * Synced: 2026-09-07T08:24:28.027Z
 */

class Solution {
    public void nextPermutation(int[] nums) {

        // Step 1: Find the first decreasing element from the right
        int i = nums.length - 2;

        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // Step 2: Find the element just larger than nums[i]
        if (i >= 0) {
            int j = nums.length - 1;

            while (nums[j] <= nums[i]) {
                j--;
            }

            // Swap
            swap(nums, i, j);
        }

        // Step 3: Reverse the part after i
        reverse(nums, i + 1, nums.length - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

}
