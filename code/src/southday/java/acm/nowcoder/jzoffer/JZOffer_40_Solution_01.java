package southday.java.acm.nowcoder.jzoffer;

import java.util.HashMap;
import java.util.Map;

/* 数组中只出现一次的数字 
一个整型数组里除了两个数字之外，其他的数字都出现了偶数次。请写程序找出这两个只出现一次的数字。
num1,num2分别为长度为1的数组。传出参数
将num1[0],num2[0]设置为返回结果


*/

public class JZOffer_40_Solution_01 {
    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        for (int i = 0; i < array.length; i++) {
            if (map.get(array[i]) == null) {
                map.put(array[i], true);
            } else {
                map.replace(array[i], false);
            }
        }
        boolean flag = true;
        for (Map.Entry<Integer, Boolean> m : map.entrySet()) {
            if (m.getValue() == true) {
                if (flag) {
                    num1[0] = m.getKey();
                    flag = false;
                } else {
                    num2[0] = m.getKey();
                    break;
                }
            }
        }
    }
}
