package southday.java.acm.nowcoder.jzoffer;

/* 正则表达式匹配 
题目描述
请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。 
在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配

看来还是得使用递归：
源串用s表示，模式串用p表示，i指向待匹配的s串最左端，j指向待匹配的p串最左端，注意边界检测
1) p[j+1] != '*'
    a) if p[j] == s[i] || p[j] == '.' => i++, j++
    b) if p[j] != s[i] => return false
2) p[j+1] == '*' && (p[j] == s[i] || p[j] == '.')
    a) p往后移2位，忽略此次p[j]的匹配，即：match(s,i,p,j+2);  => 相当于p[j]匹配0个s[i]
||  b) p往后移2位，s往后移1位，即：match(s,i+1,p,j+2); => 相当于p[j]只匹配1个s[i]
||  c) p不动，s往后移1位，因为*可以重复0次或多次，即：match(s,i+1,p,j); => 相当于p[j]想要匹配大于1个s[i]
*/

public class JZOffer_52_Solution_02 {
    public boolean match(char[] str, char[] pattern) {
        if (str == null || pattern == null) return false;
        return match(str, 0, pattern, 0);
    }
    
    private boolean match(char[] s, int i, char[] p, int j) {
        int slen = s.length, plen = p.length;
        if (i == slen && j == plen) return true;
        if (i < slen && j == plen) return false;
        if (i < slen) { // j < plen
            if (j+1 == plen || p[j+1] != '*') {
                if (p[j] == s[i] || p[j] == '.') return match(s, i+1, p, j+1);
                else return false;
            } else if (p[j] == s[i] || p[j] == '.') {
                return match(s, i, p, j+2) // p[j]匹配0个s[i]
                        || match(s, i+1, p, j+2) // p[j]只匹配1个s[i]
                        || match(s, i+1, p, j); // p[j]期待匹配多个s[i]
            } else return match(s, i, p, j+2); // 跳过p[j]*不匹配
        } else { // j < plen
            if (j+1 < plen && p[j+1] == '*') return match(s, i, p, j+2);
            else return false;
        }
    }
    
    public static void main(String[] args) {
        JZOffer_52_Solution_02 o = new JZOffer_52_Solution_02();
        char[] str = "aa".toCharArray();
//        char[] pattern = "a.a".toCharArray();
        char[] pattern = "ab*a*c*a".toCharArray();
//        char[] pattern = "a*".toCharArray();
        System.out.println(o.match(str, pattern));
    }
}

