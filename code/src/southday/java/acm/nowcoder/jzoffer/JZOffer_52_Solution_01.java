package southday.java.acm.nowcoder.jzoffer;

/* 正则表达式匹配 
题目描述
请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。 
在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配

------
虽然我的实现是错的，但我还是不忍心删除这些代码，毕竟一部分测试可以通过
char[] str = "aaa".toCharArray();
char[] pattern = "ab*ac*a".toCharArray();
这个测试用例未通过，因为我的是优先匹配原则，所以对于pattern串中间的'a'，我没有考虑到会因为后期匹配问题，而丢弃该'a'
case通过率为36.67%
*/

public class JZOffer_52_Solution_01 {
    public boolean match(char[] str, char[] pattern) {
        if (str == null || pattern == null) return false;
        String s = String.valueOf(pattern);
        if (str.length != pattern.length && !s.contains("*")) return false;
        // 预处理r[j] == true 表示 cp[j]这个字符在原串pattern上的后一个字符为 *
        boolean[] r = new boolean[pattern.length];
        char[] cp = new char[pattern.length];
        int len = preProccess(pattern, cp, r);
        // 判断
        int i = 0, j = 0;
        while (i < str.length && j < len) {
            if (str[i] == cp[j] || cp[j] == '.') {
                i++;
                j++;
            } else {
                // 搜索前面
                if (j-1 < 0 && r[j] == false) return false;
                if (j-1 >= 0 && r[j-1] == true && cp[j-1] == str[i]) {
                    i++;
                } else if (r[j] == false) {
                    return false;
                } else {
                    // 搜索后面
                    while (j < len && cp[j] != str[i] && cp[j] != '.' && r[j] == true) j++;
                    if (j == len) return false;
                    if (cp[j] == str[i] || cp[j] == '.') {
                        i++;
                        j++;
                    } else {
                        return false;
                    }
                }
            }
        }
        while (j < len && r[j] == true) j++;
        return i == str.length && j == len;
    }

    private int preProccess(char[] pattern, char[] cp, boolean[] r) {
        int cnt = 0;
        for (int i = 0, j = 0; i < pattern.length;) {
            if (pattern[i] == '*') continue;
            cp[j] = pattern[i];
            cnt++;
            if (i+1 < pattern.length && pattern[i+1] == '*') {
                r[j++] = true;
                i += 2;
            } else {
                i++;
                j++;
            }
        }
        return cnt;
    }
    
    public static void main(String[] args) {
        JZOffer_52_Solution_01 o = new JZOffer_52_Solution_01();
        char[] str = "aa".toCharArray();
//        char[] pattern = "a.a".toCharArray();
//        char[] pattern = "ab*ac*a".toCharArray();
        char[] pattern = "a*".toCharArray();
        test(pattern);
        System.out.println(o.match(str, pattern));
    }
    
    public static void test(char[] pattern) {
        JZOffer_52_Solution_01 o = new JZOffer_52_Solution_01();
        char[] cp = new char[pattern.length];
        boolean[] r = new boolean[pattern.length];
        int len = o.preProccess(pattern, cp, r);
        for (int i = 0; i < len; i++) {
            System.out.println(i + ", " + cp[i] + ", " + r[i]);
        }
    }
}
