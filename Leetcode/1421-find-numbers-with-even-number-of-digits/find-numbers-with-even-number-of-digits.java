class Solution {
    private int digits(int no){
        int count = 0;
        while(no != 0)
        {
            no /= 10;
            count++;
        }
        return count;
    }
    public int findNumbers(int[] nums) {
        int count = 0;
        for(int i : nums){
            if(digits(i)%2 == 0)
            {
                count++;
            }
        }
        return count;
    }
}