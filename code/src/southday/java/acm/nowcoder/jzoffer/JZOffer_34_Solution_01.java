package southday.java.acm.nowcoder.jzoffer;

/* 第一个只出现一次的字符位置 
在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.
*/

public class JZOffer_34_Solution_01 {
    
    public int FirstNotRepeatingChar(String str) {
        if (str == null || "".equals(str.trim())) return -1;
        char chs[] = str.toCharArray();
        int[] cnt = new int[128];
        int[] seq = new int[128];
        for (int i = 0; i < chs.length; i++) {
            cnt[chs[i]]++;
            seq[chs[i]] = i;
        }
        int p = Integer.MAX_VALUE;
        for (int i = 0; i < 128; i++) {
            if (cnt[i] == 1 && seq[i] < p) {
                p = seq[i];
            }
        }
        return p == Integer.MAX_VALUE ? -1 : p;
    }
    
}
