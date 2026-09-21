class Solution {
    public long[] resultArray(int[] nums, int k) {

        long[] dp = new long[k];
        long[] ans = new long[k];

        for (int num : nums) {

            long[] next = new long[k];

            // Start a new subarray
            next[num % k]++;

            // Extend previous subarrays
            for (int r = 0; r < k; r++) {
                int newRemainder = (int)((long) r * num % k);
                next[newRemainder] += dp[r];
            }

            dp = next;

            // All subarrays ending at this index
            for (int r = 0; r < k; r++) {
                ans[r] += dp[r];
            }
        }

        return ans;
    }
}