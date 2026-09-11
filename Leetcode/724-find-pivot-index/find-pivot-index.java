class Solution {
    public int pivotIndex(int[] nums) {
        //Find total sum
        int totalsum = 0;
        for(int i=0;i<nums.length;i++){
            totalsum+=nums[i];
        }
        int lsum =0;
        for(int i=0;i<nums.length;i++){
            int rsum= totalsum-lsum-nums[i];//calculate rsum at every step 
            if(lsum==rsum) return i;//compare with lsum
            lsum+=nums[i];//Update lsum every time
        }
        return -1;
    }
}