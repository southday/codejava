package southday.java.acm.nowcoder.jzoffer;

/* 数字在排序数组中出现的次数 
统计一个数字在排序数组中出现的次数。
*/

public class JZOffer_37_Solution_01 {
    
    public int GetNumberOfK(int [] array , int k) {
        if (array.length == 0) return 0;
        int p = findIndex(array, k);
        if (p == -1) return 0;
        int cnt = 1;
        for (int i = p+1; i < array.length; i++) {
            if (array[i] == k) cnt++;
            else break;
        }
        for (int i = p-1; i >= 0; i--) {
            if (array[i] == k) cnt++;
            else break;
        }
        return cnt;
    }
    
    private int findIndex(int[] array, int k) {
        int len = array.length;
        int left = 0, right = len-1, mid = 0;
        while (left <= right) {
            mid = (left + right) >> 1;
            if (array[mid] == k) {
                return mid;
            } else if (array[mid] > k) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
