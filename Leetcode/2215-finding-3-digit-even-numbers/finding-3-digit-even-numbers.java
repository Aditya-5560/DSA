
class Solution {
    public int[] findEvenNumbers(int[] digits) {
        int[] freq = new int[10];

        for (int digit : digits) {
            freq[digit]++;
        }

        int[] result = new int[450];
        int size = 0;

        for (int num = 100; num <= 998; num += 2) {
            int a = num / 100;
            int b = (num / 10) % 10;
            int c = num % 10;

            int[] need = new int[10];
            need[a]++;
            need[b]++;
            need[c]++;

            boolean valid = true;

            for (int i = 0; i < 10; i++) {
                if (need[i] > freq[i]) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                result[size++] = num;
            }
        }

        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = result[i];
        }

        return ans;
    }
}
