class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();

        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, n);
        Arrays.fill(last, -1);

        // Find first and last occurrence
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';

            first[c] = Math.min(first[c], i);
            last[c] = i;
        }

        List<String> ans = new ArrayList<>();

        int prevEnd = -1;

        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';

            // Only first occurrence can start a valid substring
            if (first[c] != i) {
                continue;
            }

            int end = last[c];
            boolean valid = true;

            // Expand the interval
            for (int j = i; j <= end; j++) {
                int x = s.charAt(j) - 'a';

                // Character occurs before i
                if (first[x] < i) {
                    valid = false;
                    break;
                }

                // Include all occurrences of this character
                end = Math.max(end, last[x]);
            }

            if (!valid) {
                continue;
            }

            // Non-overlapping
            if (i > prevEnd) {
                ans.add(s.substring(i, end + 1));
            } 
            // Overlapping: keep the one ending earlier
            else {
                ans.set(ans.size() - 1, s.substring(i, end + 1));
            }

            prevEnd = end;
        }

        return ans;
    }
}