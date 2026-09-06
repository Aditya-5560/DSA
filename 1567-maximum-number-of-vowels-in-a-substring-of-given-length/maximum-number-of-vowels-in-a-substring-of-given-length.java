class Solution {
    public int maxVowels(String s, int k) {
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