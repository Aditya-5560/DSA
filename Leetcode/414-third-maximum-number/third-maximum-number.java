class Solution {
    public int thirdMax(int[] nums) {
        Arrays.sort(nums);

        for (int i = 0; i < nums.length / 2; i++) {
            int temp = nums[i];
            nums[i] = nums[nums.length - i - 1];
            nums[nums.length - i - 1] = temp;
        }

        int i = 0;
        int count = 1;

        while (i < nums.length - 1) {
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                i++;
            }

            i++;

            if (i < nums.length) {
                count++;

                if (count == 3) {
                    return nums[i];
                }
            }
        }

        return nums[0];
    }
}