package southday.java.acm.nowcoder.jzoffer;

/* 字符串的排列 
题目描述
输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
输入描述:
输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JZOffer_27_Solution_01 {
    char[] chs = null;
    boolean[] vis = null;
    ArrayList<String> result = new ArrayList<String>();
    StringBuilder sb = new StringBuilder();
    HashMap<String, Boolean> map = new HashMap<String, Boolean>();
    
    public ArrayList<String> Permutation(String str) {
        if (str == null || "".equals(str)) {
            return result;
        }
        if (str.length() == 1) {
            result.add(str);
            return result;
        }
        chs = str.toCharArray();
        Arrays.sort(chs);
        vis = new boolean[chs.length];
        for (int i = 0; i < vis.length; i++) {
            vis[i] = false;
        }
        for (int i = 0; i < chs.length; i++) {
            vis[i] = true;
            sb.append(chs[i]);
            create(chs, 2);
            vis[i] = false;
            sb.deleteCharAt(sb.length()-1);
        }
        return result;
    }
    
    private void create(char[] chs, int deep) {
        for (int i = 0; i < chs.length; i++) {
            if (vis[i] == false) {
                vis[i] = true;
                sb.append(chs[i]);
                create(chs, deep+1);
                if (deep == chs.length) {
                    String s = sb.toString();
                    if (map.get(s) == null) {
                        map.put(s, true);
                        result.add(s);
                    }
                }
                vis[i] = false;
                sb.deleteCharAt(sb.length()-1);
            }
        }
    }
    
    public static void main(String[] args) {
        JZOffer_27_Solution_01 o = new JZOffer_27_Solution_01();
        for (String s : o.Permutation("a")) {
            System.out.println(s);
        }
    }
}
