package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;
/* 和为S的两个数字 
题目描述
输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
输出描述:
对应每个测试案例，输出两个数，小的先输出。
*/

public class JZOffer_42_Solution_01 {
    
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (array.length == 0 || sum <= array[0] || array[array.length-1] <= sum/2) return result;
        int i = binSearchFirstGE(array, sum/2);
        if (i == -1) return result;
        int j = i+1, minv = Integer.MAX_VALUE, v = 0;
        int mini = -1, minj = -1;
        while (i >= 0 && j < array.length) {
            v = array[i] + array[j];
            if (v == sum) {
                if (array[i]*array[j] <= minv) {
                    mini = i--;
                    minj = j++;
                }
            } else if (v > sum) {
                i--;
            } else {
                j++;
            }
        }
        if (mini == -1) return result;
        result.add(array[mini]);
        result.add(array[minj]);
        return result;
    }
    
    private int binSearchFirstGE(int array[], int v) {
        if (array.length == 0) return -1;
        int left = 0, right = array.length - 1;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (array[mid] > v) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return array[right] >= v ? right : right < array.length-2 ? right+1 : -1;
    }
    
    public static void main(String[] args) {
        JZOffer_42_Solution_01 o = new JZOffer_42_Solution_01();
        int array[] = {1,2,4,7,11,15};
        int sum = 15;
        for (int n : o.FindNumbersWithSum(array, sum)) {
            System.out.print(n + " ");
        }
    }
}
