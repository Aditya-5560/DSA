
class Solution {
    public int[] findEvenNumbers(int[] digits) {

        //Create frequency array and add frequency of given each digit in that
        int[] freq = new int[10];

        for (int digit : digits) {
            freq[digit]++;
        }

        //Create result array with maximum size possible from given constraints 
        //That is 
        //1. 3 digit
        //2. even

        int[] result = new int[450];
        int size = 0;

        //Take size as counter to add elements in the result

        for (int num = 100; num <= 998; num += 2) {

            //Split each number and add each digit of them in need array
            int a = num / 100;
            int b = (num / 10) % 10;
            int c = num % 10;

            //Create need array to store current number in form of digit frequency
            int[] need = new int[10];
            need[a]++;
            need[b]++;
            need[c]++;

            //to check if yes then add
            boolean valid = true;

            //Compare need with freq and if it doesnt match turn valid flag down
            for (int i = 0; i < 10; i++) {
                if (need[i] > freq[i]) {
                    valid = false;
                    break;
                }
            }

            //if number is vaild add it into ans and shift size pointer 
            if (valid) {
                result[size] = num;
                size++;
            }
        }

        //Now ans is a big array so we create another array of [size] elements and add one one valude from result to ans 
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = result[i];
        }

        //returned ans array
        return ans;
    }
}
