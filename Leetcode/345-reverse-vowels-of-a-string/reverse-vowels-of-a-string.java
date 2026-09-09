class Solution {
    public String reverseVowels(String s) {
            char[] crr = s.toCharArray();
            int l=0;
            int r=crr.length-1;
            while(l<r){
                while(l<r && !isVovel(crr[l])){
                    l++;
                }
                while(l<r && !isVovel(crr[r])){
                    r--;
                }
                char temp = crr[l];
                crr[l]=crr[r];
                crr[r]=temp;
                l++;
                r--;
            }
            return new String(crr);
        }
        private static boolean isVovel(char c){
            if(c=='a' || c=='A' || c=='e' || c=='E' ||c=='i' || c=='I' ||c=='o' || c=='O' ||c=='u' || c=='U'){
                return true;
            }
            return false;
        }
}