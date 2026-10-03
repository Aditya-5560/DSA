class Solution {
    public void rotate(int[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        for(int i=0;i<r;i++){
            for(int j=i+1;j<c;j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for(int i=0;i<r;i++){
            int lth = 0;
            int rth = c-1;
            while(lth<rth){
                int temp = matrix[i][lth];
                matrix[i][lth] = matrix[i][rth];
                matrix[i][rth] = temp;
                lth++;
                rth--;
            }
        }
    }
}