class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        int[] dp = new int[n + 1];

        for (int i = k; i <= n; i++) {

            // Don't take a palindrome ending at i - 1
            dp[i] = dp[i - 1];

            // Check palindrome of length k
            if (isPalindrome(s, i - k, i - 1)) {
                dp[i] = Math.max(dp[i], dp[i - k] + 1);
            }

            // Check palindrome of length k + 1
            if (isPalindrome(s, i - k - 1, i - 1)) {
                dp[i] = Math.max(dp[i], dp[i - k - 1] + 1);
            }
        }

        return dp[n];
    }

    private boolean isPalindrome(String s, int l, int r) {
        if (l < 0) {
            return false;
        }

        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }

            l++;
            r--;
        }

        return true;
    }
}