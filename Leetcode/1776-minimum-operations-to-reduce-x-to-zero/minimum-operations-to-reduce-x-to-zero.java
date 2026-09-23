class Solution {
    public int minOperations(int[] nums, int x) {
        int totalSum = 0;

        for (int num : nums) {
            totalSum += num;
        }

        int target = totalSum - x;

        if (target < 0) {
            return -1;
        }

        int left = 0;
        int currSum = 0;
        int maxLen = -1;

        for (int right = 0; right < nums.length; right++) {

            currSum += nums[right];

            while (currSum > target) {
                currSum -= nums[left];
                left++;
            }

            if (currSum == target) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }

        if (maxLen == -1) {
            return -1;
        }

        return nums.length - maxLen;
    }
}