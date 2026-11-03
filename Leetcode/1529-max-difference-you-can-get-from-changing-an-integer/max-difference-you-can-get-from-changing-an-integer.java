
class Solution {
    public int maxDiff(int num) {
        String s = String.valueOf(num);

        char[] maxArr = s.toCharArray();

        for (int i = 0; i < maxArr.length; i++) {
            if (maxArr[i] != '9') {
                char target = maxArr[i];

                for (int j = 0; j < maxArr.length; j++) {
                    if (maxArr[j] == target) {
                        maxArr[j] = '9';
                    }
                }
                break;
            }
        }
        
        char[] minArr = s.toCharArray();

        if (minArr[0] != '1') {
            char target = minArr[0];

            for (int i = 0; i < minArr.length; i++) {
                if (minArr[i] == target) {
                    minArr[i] = '1';
                }
            }
        } else {
            for (int i = 1; i < minArr.length; i++) {
                if (minArr[i] != '0' && minArr[i] != '1') {
                    char target = minArr[i];

                    for (int j = 1; j < minArr.length; j++) {
                        if (minArr[j] == target) {
                            minArr[j] = '0';
                        }
                    }
                    break;
                }
            }
        }

        int max = Integer.parseInt(new String(maxArr));
        int min = Integer.parseInt(new String(minArr));

        return max - min;
    }
}
