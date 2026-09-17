class Solution {
    public int minSumOfLengths(int[] arr, int target) {

        int n = arr.length;
        int INF = 1000000000;

        // best[i] = shortest valid subarray
        // completely within arr[0...i]
        int[] best = new int[n];

        for (int i = 0; i < n; i++) {
            best[i] = INF;
        }

        int left = 0;
        int sum = 0;

        int minLen = INF;
        int answer = INF;

        for (int right = 0; right < n; right++) {

            // Add current element
            sum += arr[right];

            // Shrink window if sum becomes too large
            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            // Found a subarray with target sum
            if (sum == target) {

                int len = right - left + 1;

                // Find another non-overlapping subarray
                // completely before this one
                if (left > 0 && best[left - 1] != INF) {
                    answer = Math.min(
                        answer,
                        len + best[left - 1]
                    );
                }

                // Keep the shortest valid subarray seen so far
                minLen = Math.min(minLen, len);
            }

            // Best valid subarray up to 'right'
            best[right] = minLen;
        }

        return answer == INF ? -1 : answer;
    }
}