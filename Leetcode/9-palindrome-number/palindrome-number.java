class Solution {
    public boolean isPalindrome(int x) {
        String original = String.valueOf(x);
        String reverse = new StringBuilder(original).reverse().toString();
        if(reverse.equals(original)) return true;
        return false;
    }
}