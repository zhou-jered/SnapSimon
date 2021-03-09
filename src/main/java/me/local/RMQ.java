package me.local;

public class RMQ {

    int[][] dp = null;

    public void init(int[] nums, int size) {
        int power2Size = minimum2PowerGreatEqualThan(size);
        dp = new int[nums.length][power2Size];
        for (int i = 0; i < nums.length; i++) {
            dp[i][0] = nums[i];
        }
        for (int j = 1; ((int) Math.pow(2, j)) <= nums.length; j++) {
            for (int i = 0; i < nums.length; i++) {
                int nextHalfIdx = i + ((int) Math.pow(2, j - 1));
                if (nextHalfIdx >= nums.length) {
                    break;
                }
                dp[i][j] = Math.min(dp[i][j - 1], dp[nextHalfIdx][j - 1]);
            }
        }
    }

    /**
     * both start&end inclusive
     * make sure start <= end <= num.length
     *
     * @param start
     * @param end
     * @return
     */
    public int query(int start, int end) {
        System.out.println("query ;" + start + ":" + end);
        if (start == end) {
            return dp[start][0];
        }
        int targetPowIdx = 0;
        while (start + ((int) Math.pow(2, targetPowIdx + 1)) < end) {
            targetPowIdx++;
        }
        int powEndIdx = start + ((int) Math.pow(2, targetPowIdx));
        if (powEndIdx == end) {
            return dp[start][targetPowIdx];
        } else {
            return Math.min(dp[start][targetPowIdx], query(powEndIdx, end));
        }
    }


    private int minimum2PowerGreatEqualThan(int num) {
        if ((num & (num - 1)) == 0) {
            return num;
        }
        int n = num;
        int offset = 1;
        while ((n >> offset) > 0) {
            num = (num | (num >> offset));
            offset++;
        }
        return num + 1;
    }

}
