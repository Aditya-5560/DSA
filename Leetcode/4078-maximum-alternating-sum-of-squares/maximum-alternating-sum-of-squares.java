
class Solution {
    public long maxAlternatingSum(int[] nums) {
        int max = 0;
        long total = 0;

        for (int x : nums) {
            int v = Math.abs(x);
            max = Math.max(max, v);
            total += (long) v * v;
        }

        int[] freq = new int[max + 1];

        for (int x : nums) {
            freq[Math.abs(x)]++;
        }

        long smallest = 0;
        int need = nums.length / 2;

        for (int v = 0; need > 0; v++) {
            int take = Math.min(freq[v], need);
            smallest += (long) take * v * v;
            need -= take;
        }

        return total - 2 * smallest;
    }
}