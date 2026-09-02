class Solution {
private:
    bool checkequal(int s[], int t[]) {
        for (int i = 0; i < 26; i++) {
            if (s[i] != t[i]) {
                return false;
            }
        }
        return true;
    }

public:
    bool isAnagram(string s, string t) {

        if (s.length() != t.length()) {
            return false;
        }

        int temp1[26] = {0};

        for (int i = 0; i < s.length(); i++) {
            int index = s[i] - 'a';
            temp1[index]++;
        }

        int temp2[26] = {0};

        for (int i = 0; i < t.length(); i++) {
            int index2 = t[i] - 'a';
            temp2[index2]++;
        }

        if (checkequal(temp1, temp2)) {
            return true;
        }
        else {
            return false;
        }
    }
};