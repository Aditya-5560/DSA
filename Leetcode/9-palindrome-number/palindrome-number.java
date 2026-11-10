class Solution {
    public boolean isPalindrome(int x) {
        if(x<0) return false;
        
        int no = 0;
        int original = x;
        while(x != 0)
        {
            int digit = x % 10;
            no = no * 10 + digit;
            x /= 10;
        }
        if(no == original)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
}