package southday.java.acm.other.solution;

/*
题目描述：一个整型数组A[]中，除了一个数字以外，其他数字都出现了n次（0 < n < 100），如何找出这个数？

思想：假设 n = 3, A[] = {1, 1, 1, 2, 2, 2}
1) 把A[]转为二进制看待：{01, 01, 01, 10, 10, 10}，把这些数都加起来
2) 发现在第1位有3个1，第2位也有3个1，也就是说除去那个只出现一次的数后，第i位出现1的个数可以整除3
3) 这时候把只出现一次的数字3加到 A[]中：{01, 01, 01, 10, 10, 11}：
    a) 发现第1位1的次数 % 3 != 0，说明要求的数中第1位为1
    b) 发现第2位1的次数 % 3 != 0，说明要求的数中第2位为1
    c) 因此可以求出这个数的二进制表示为：(11)B => (3)D

注：
如果 n 是 偶数的话，可以使用另一种思路：异或
因为 两个相同的数异或结果为0，所以将arr[]中的每个数都异或，最后的结果就是只出现一次的那个数
 */

/**
 * @author southday
 * @date 2019/5/4
 */
public class OnlyOnceNumber {

    /**
     * 找出只出现一次的数
     * @param arr
     * @param appearTimes 除了只出现一次的数外，其他元素均出现的次数
     */
    public static int findOnlyOnceNumber(int[] arr, int appearTimes) {
        int[] bitCount = new int[32];
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < 32; j++)
                bitCount[j] += ((arr[i] >> j) & 1);
        int ret = 0;
        for (int i = 0; i < 32; i++)
            if (bitCount[i] % appearTimes != 0)
                ret += (1 << i);
        return ret;
    }

    /**
     * 当 appearTimes 为偶数时，使用异或来求解
     * @param arr
     * @param appearTimes 除了只出现一次的数外，其他元素均出现的次数
     * @return
     */
    public static int findOnlyOnceNumberXOR(int[] arr, int appearTimes) {
        if (appearTimes % 2 != 0)
            throw new IllegalArgumentException("appearTimes 应该为2的正整数倍");
        int ret = 0;
        for (int i = 0; i < arr.length; i++)
            ret ^= arr[i];
        return ret;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 1, 2, 4, 2, 4, 4, 1, 3};
        System.out.println(OnlyOnceNumber.findOnlyOnceNumber(arr, 3));
        int[] arr2 = {1, 2, 1, 2, 4, 4, 3};
        System.out.println(OnlyOnceNumber.findOnlyOnceNumberXOR(arr2, 2));
    }
}
