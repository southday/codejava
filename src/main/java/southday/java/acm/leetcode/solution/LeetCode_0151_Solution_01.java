package southday.java.acm.leetcode.solution;

/**
 * 151. Reverse Words in a String https://leetcode.com/problems/reverse-words-in-a-string/
 * @author southday
 * @date 2019/3/12
 */
public class LeetCode_0151_Solution_01 {
    public String reverseWords(String s) {
        if (s == null)
            return null;
        char[] cs = s.toCharArray();
        char[] vs = new char[cs.length + 1];
        int offset = 0;
        int f1 = cs.length - 1;
        int e1 = f1;
        for (; e1 >= 0;) {
            if (cs[e1] == ' ') {
                e1--;
            } else {
                f1 = e1 - 1;
                for (; f1 >= 0 && cs[f1] != ' '; f1--) ;
                for (int i = f1 + 1; i <= e1; i++, offset++)
                    vs[offset] = cs[i];
                vs[offset++] = ' ';
                e1 = f1 - 1;
            }
        }
        return String.valueOf(vs, 0, (offset > 0) ? offset - 1 : 0);
    }

    public static void main(String[] args) {
        LeetCode_0151_Solution_01 o = new LeetCode_0151_Solution_01();
        String b = null;
        b = o.reverseWords("the sky is blue");
        System.out.println(b);
        b = o.reverseWords("  hello world!  ");
        System.out.println(b);
        b = o.reverseWords("a good   example");
        System.out.println(b);
        b = o.reverseWords(" ");
        System.out.println(b);
    }
}
