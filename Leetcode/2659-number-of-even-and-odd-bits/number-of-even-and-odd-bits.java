class Solution {
    private int[] tobinary(int n)
    {
        if(n == 0)
        {
            return new int[]{0};
        }
        int[] temp = new int[32];
        int i = 0;
        while(n != 0)
        {
            temp[i] = n % 2;
            n/=2;
            i++;
        }
        int res[] = new int[i];
        for(int j=0; j<i; j++)
        {
            res[j] = temp[i-1-j];
        }
        return res;
    }
    public int[] evenOddBit(int n) 
    {
        int[] b = tobinary(n);
        int[] ans = new int[2];
        for(int i=0; i<b.length; i++)
        {
            if(b[i] == 1)
            {
                int pos = b.length - 1 - i;
                if(pos % 2 == 0)
                {
                    ans[0]++;
                }
                else
                {
                    ans[1]++;
                }
            }
        }
        return ans;
    }
}