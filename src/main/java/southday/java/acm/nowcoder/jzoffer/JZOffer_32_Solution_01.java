package southday.java.acm.nowcoder.jzoffer;

import java.util.ArrayList;
import java.util.Comparator;

/* 把数组排成最小的数 
输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。 
*/


public class JZOffer_32_Solution_01 {
    
    static class Cmp implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            char[] chs1 = o1.toCharArray();
            char[] chs2 = o2.toCharArray();
            int i = 0, len1 = o1.length(), len2 = o2.length();
            while (i < len1 && i < len2 && chs1[i] == chs2[i]) i++;
            if (i == len1 && i == len2) {
                return 0;
            } else if (i == len1 && i < len2) {
                return chs1[0] <= chs2[len2-1] ? -1 : 1;
            } else if (i == len2 && i < len1){
                return chs1[len1-1] <= chs2[0] ? -1 : 1;
            } else {
                return chs1[i] <= chs2[i] ? -1 : 1;
            }
        }
        
    }
    
    public String PrintMinNumber(int [] numbers) {
        if (numbers.length == 0) return "";
        ArrayList<String> ls = new ArrayList<String>();
        for (int i = 0; i < numbers.length; i++) {
            ls.add(Integer.toString(numbers[i]));
        }
        ls.sort(new Cmp());
        StringBuilder sb = new StringBuilder();
        for (String s : ls) {
            sb.append(s);
//            sb.append(s + ",");
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        JZOffer_32_Solution_01 o = new JZOffer_32_Solution_01();
        int numbers[] = {3334,3,3333332};
//        int numbers[] = {3, 32, 321};
//        int numbers[] = {3, 1245}; // 这组数据说明我的程序是错的
        System.out.println(o.PrintMinNumber(numbers));
    }
}
