package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;
import java.util.Arrays;

/* 调整数组顺序使奇数位于偶数前面 
输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 */

public class JZOffer_13_Solution_01 {
    
    public void reOrderArray(int [] array) {
        int[] copyArray = Arrays.copyOf(array, array.length);
        ArrayList<Integer> evenList = new ArrayList<Integer>();
        int j = 0;
        for (int i = 0; i < copyArray.length; i++) {
            if (copyArray[i] % 2 == 1) {
                array[j++] = copyArray[i];
            } else {
                evenList.add(copyArray[i]);
            }
        }
        for (int a : evenList) {
            array[j++] = a;
        }
    }
}
