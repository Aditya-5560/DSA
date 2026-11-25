class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        // Quick optimization: if ransomNote is longer, it's impossible to construct
        if (ransomNote.length() > magazine.length()) {
            return false;
        }

        int[] counts = new int[26];

        // Count available characters in magazine
        for (char c : magazine.toCharArray()) {
            counts[c - 'a']++;
        }

        // Subtract required characters for ransomNote
        for (char c : ransomNote.toCharArray()) {
            counts[c - 'a']--;
            if (counts[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }
}
