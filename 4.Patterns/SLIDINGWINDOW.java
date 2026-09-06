import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
public class SLIDINGWINDOW {

//    Beginner
//    1. Maximum sum subarray of size K
//    2. Average of subarrays of size K
//    3. First negative number in every window
//
//    Intermediate
//    4. Longest substring without repeating characters
//    5. Maximum number of vowels in a substring of size K
//    6. Longest substring with at most K distinct characters
//    7. Longest subarray with at most K zeros
//
//    Advanced
//    8. Permutation in String
//    9. Find All Anagrams in a String
//    10. Longest Repeating Character Replacement
//    11. Subarrays with K Different Integers
//    12. Minimum Window Substring
//    13. Sliding Window Maximum

    public class Leetcode{
        public double l643_findMaxAverage(int[] nums, int k) {
            int l=0;
            int sum = 0;
            int max = Integer.MIN_VALUE;
            for(int r=0;r<nums.length;r++){
                sum+=nums[r];
                if(r-l+1==k){
                    max = Math.max(max,sum);
                    sum-=nums[l];
                    l++;
                }
            }
            return (double)max/k;
        }
        public int l1456_maxVowels(String s, int k) {
            int l=0;
            int maxvovel =0;
            int vovel=0;
            for(int r=0;r<s.length();r++){
                if(s.charAt(r)=='a' || s.charAt(r)=='e' || s.charAt(r)=='i' || s.charAt(r)=='o' || s.charAt(r)=='u'){
                    vovel++;
                }
                if(r-l+1==k){
                    maxvovel=Math.max(maxvovel,vovel);
                    if(s.charAt(l)=='a' || s.charAt(l)=='e' || s.charAt(l)=='i' || s.charAt(l)=='o' || s.charAt(l)=='u'){
                        vovel--;
                    }
                    l++;
                }
            }
            return maxvovel;
        }
    }
    public static class GFG {
        public int maxSubarraySum(int[] arr, int k) {
            int l = 0;
            int sum = 0;
            int max = Integer.MIN_VALUE;
            for (int r = 0; r < arr.length; r++) {
                sum += arr[r];
                if (r - l + 1 == k) {
                    max = Math.max(sum, max);
                    sum -= arr[l];
                    l++;
                }
            }
            return max;
        }
        public static List<Integer> firstNegInt(int arr[], int k) {
            List<Integer> ans = new ArrayList<>();
            int l = 0;
            for (int r = 0; r < arr.length; r++) {
                int temp = l;
                if (r - l + 1 == k) {
                    while (temp <= r) {
                        if (arr[temp] < 0) {
                            ans.add(arr[temp]);
                            break;
                        }
                        temp++;
                    }
                    if (temp > r) {
                        ans.add(0);
                    }
                    l++;
                }
            }
            return ans;
        }
    }
    public static void main() {

    }
}

