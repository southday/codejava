package southday.java.acm.nowcoder.bytedance.spring2019;

import java.util.Scanner;

/*
题目：
https://www.nowcoder.com/question/next?pid=16516564&qid=362290&tid=30648374

我叫王大锤，是一家出版社的编辑。我负责校对投稿来的英文稿件，这份工作非常烦人，因为每天都要去修正无数的拼写错误。但是，优秀的人总能在平凡的工作中发现真理。我发现一个发现拼写错误的捷径：

1. 三个同样的字母连在一起，一定是拼写错误，去掉一个的就好啦：比如 helllo -> hello
2. 两对一样的字母（AABB型）连在一起，一定是拼写错误，去掉第二对的一个字母就好啦：比如 helloo -> hello
3. 上面的规则优先“从左到右”匹配，即如果是AABBCC，虽然AABB和BBCC都是错误拼写，应该优先考虑修复AABB，结果为AABCC

我特喵是个天才！我在蓝翔学过挖掘机和程序设计，按照这个原理写了一个自动校对器，工作效率从此起飞。用不了多久，我就会出任CEO，当上董事长，迎娶白富美，走上人生巅峰，想想都有点小激动呢！
……
万万没想到，我被开除了，临走时老板对我说： “做人做事要兢兢业业、勤勤恳恳、本本分分，人要是行，干一行行一行。一行行行行行；要是不行，干一行不行一行，一行不行行行不行。” 我现在整个人红红火火恍恍惚惚的……

请听题：请实现大锤的自动校对程序

-------------------------------------------

输入描述:
第一行包括一个数字N，表示本次用例包括多少个待校验的字符串。
后面跟随N行，每行为一个待校验的字符串。

输出描述:
N行，每行包括一个被修复后的字符串。

输入例子1:
2
helloo
wooooooow

输出例子1:

hello
woow
 */

/**
 * 字节跳动，2019春招，笔试题
 * @author southday
 * @date 2020/2/19
 */
public class StringProofreader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < N; i++) {
            System.out.println(solve(scanner.nextLine()));
        }
    }

    private static String solve(String s) {
        if (s == null || s.length() < 3) {
            return s;
        }
        char[] cs = s.toCharArray();
        boolean[] skip = new boolean[cs.length];
        StringBuilder sb = new StringBuilder();
        int x, y, z;
        for (int i = 0, n = cs.length - 1; i <= n;) {
            i = index(skip, i);
            x = index(skip, i + 1);
            y = index(skip, x + 1);
            z = index(skip, y + 1);
            if (x < n && y <= n && cs[i] == cs[x] && cs[x] == cs[y]) {
                i++;
            } else if (x < n && y < n && z <= n && cs[i] == cs[x] && cs[y] == cs[z]) {
                skip[y] = true;
            } else {
                sb.append(cs[i]);
                i++;
            }
        }
        return sb.toString();
    }

    private static int index(boolean[] skip, int i) {
        for (; i < skip.length && skip[i]; i++);
        return i;
    }
}
