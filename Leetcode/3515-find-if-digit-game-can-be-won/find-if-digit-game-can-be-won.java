class Solution {
    public boolean canAliceWin(int[] nums) {
        int single = 0;
        int doub = 0;

        for (int num : nums) {
            if (num < 10) {
                single += num;
            } else {
                doub += num;
            }
        }

        return single != doub;
    }
}
