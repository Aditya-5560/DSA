class Solution {
    public int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        //Skip all spaces at end
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }
        //Count the word upto next blank space
        int count = 0;
        while (i >= 0 && s.charAt(i) != ' ') {
            count++;
            i--;
        }
        return count;
    }
}