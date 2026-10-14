import java.util.*;

class Solution {
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch == '+' || ch == '-' || ch == '*') {
                List<Integer> left =
                    diffWaysToCompute(expression.substring(0, i));

                List<Integer> right =
                    diffWaysToCompute(expression.substring(i + 1));

                for (int a : left) {
                    for (int b : right) {
                        if (ch == '+') {
                            result.add(a + b);
                        } else if (ch == '-') {
                            result.add(a - b);
                        } else {
                            result.add(a * b);
                        }
                    }
                }
            }
        }

        if (result.isEmpty()) {
            result.add(Integer.parseInt(expression));
        }

        return result;
    }
}
