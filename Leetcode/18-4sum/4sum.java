class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length-3;i++){//n-3 because after i 3 more pointers
            //Skip 1st pointer duplicates
            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }
            for(int j=i+1;j<nums.length-2;j++){//n-2 because after j 2 more pointers
                //Skip 2nd pointer duplicates
                if(j>i+1 && nums[j]==nums[j-1]){
                    continue;
                }
                //Start general 2 pointer approach
                int l=j+1;
                int r=nums.length-1;
                while(l<r){
                    long sum = (long) nums[i]+nums[j]+nums[l]+nums[r];
                    if(sum==target){
                        ans.add(Arrays.asList(nums[i],nums[j],nums[l],nums[r]));

                        //Skip duplicates in 2 pointer
                        while(l<r && nums[l]==nums[l+1]){
                            l++;
                        }
                        while(l<r && nums[r]==nums[r-1]){
                            r--;
                        }

                        l++;
                        r--;

                    }else if(sum<target){
                        l++;
                    }else{
                        r--;
                    }
                }
            }
        }
        return ans;
    }
}