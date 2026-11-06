
class Solution {
    public int countElements(int[] nums) {
        int min = nums[0];
        int max = nums[0];

        // Find minimum and maximum
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        // If all elements are equal
        if (min == max) {
            return 0;
        }

        int count = 0;

        // Count elements strictly between min and max
        for (int num : nums) {
            if (num > min && num < max) {
                count++;
            }
        }

        return count;
    }
}
