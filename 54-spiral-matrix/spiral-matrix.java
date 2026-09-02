class Solution {
    public List<Integer> spiralOrder(int[][] nums) {
        int startrow = 0;
        int startcol = 0;
        int endrow = nums.length-1;
        int endcol = nums[0].length-1;
        List<Integer> ans = new ArrayList<>();
        while(startrow<=endrow && startcol<=endcol){
            //Top
            for(int j=startcol;j<=endcol;j++){
                ans.add(nums[startrow][j]);
            }
            //Right
            for(int i=startrow+1;i<=endrow;i++){
                ans.add(nums[i][endcol]);
            }
            //Bottom
            for(int j=endcol-1;j>=startcol;j--){
                if(startrow==endrow){
                    break;
                }
                ans.add(nums[endrow][j]);
            }
            //Left
            for(int i=endrow-1;i>=startrow+1;i--){
                if(startcol==endcol){
                    break;
                }
                ans.add(nums[i][startcol]);
            }
            startrow++;
            startcol++;
            endrow--;
            endcol--;
        }
        return ans;
    }
}