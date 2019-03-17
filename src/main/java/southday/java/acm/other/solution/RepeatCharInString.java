package southday.java.acm.other.solution;

/*
 * 判断一个字符串中是否有重复的字符出现，有则返回false，无则返回true，
 * 所有字符均在ASCII码表示范围内，字符串长度不超过3000，
 * 空间复杂度O(1)
 *
 * 2019.03.16，洗完澡出来，舍友给我说了这个题，然后想了个比较奇特解法，特意实现来检验一波；
 * 1) 首先，所有字符在ASCII码表示范围内（0 至 127），所以s.length() > 128则直接false
 * 2) 其次，根据s.length()来计算s中单个字符ASCII码十进制累加和的表示范围；举例如下：
 *   a) 假设s.length = 123，那么相差 diffLen = 128 - 123 = 5；
 *   b) 假设s中的字符都不重复，则这些字符之和要在范围[minSum, maxSum]中；
 *   c) 由公式；n * (n + 1) / 2 可计算出相关数值：minSum，maxSum
 *   d) minSum表示：字符串中除去ASCII码值最大的那5个字符后所累加的和；
 *   e) maxSum表示：字符串中除去ASCII码值最小的那5个字符后所累加的和；
 * 3) 计算s的字符累加和，记为sum，如果 minSum <= sum <= maxSum，则返回true，否则返回false
 *
 * 经过验证，这个解法不可行，因为：
 * 1) 确实可以确保sum不在[minSum, maxSum]范围内的字符串s有重复字符；
 * 2) 但是无法确保sum在[minSum, maxSum]范围内的字符串s无重复字符；
 *    比如：s.length() = 123时，s由123个ASCII[6]的字符构成，其sum也在[minSum, maxSum]范围内；
 *
 * 后来想了先用O(nlg(n))时间复杂度对s排序，然后用O(n)时间遍历，检测相连字符串是否相等；
 * 这个算法的时间复杂度：O(n + nlg(n))；
 *
 * 动态规划好像也不能用，想不出来其他好的解法了；早上舍友跟我说有人用一行代码就做出来了，我一脸懵逼，问了后才知道用的是正则表达式；
 * 我感觉这是没有灵魂的解法，只能说是方法，谈不上算法；
 *
 * 对于解题方法来说，思路还是值得一记，直接看代码的注释：
 */

/**
 * 判断一个字符串中是否有重复的字符出现
 * @author southday
 * @date 2019/3/16
 */
public class RepeatCharInString {
    public boolean noRepeatChar(String s) {
        if (s == null)
            return true;
        if (s.length() > 128)
            return false;
        int slen = s.length();
        int diffLen = 128 - slen;
        int maxSum = (127 * 128 / 2) - ((diffLen - 1) * diffLen / 2);
        int minSum = (slen - 1) * slen / 2;
        System.out.println("[" + minSum + ", " + maxSum + "]");
        int sum = 0;
        for (int i = 0; i < slen; i++) {
            sum += s.charAt(i);
            System.out.println(s.charAt(i) + "(" + ((int)s.charAt(i)) + "), sum = " + sum);
        }
        return sum <= maxSum && sum >= minSum;
    }

    /* 解释：.*(.)(.*\\1).*
     * 字符串s: xxx a xxx a xxx，x表示任意字符，也可能不存在
     * 也就是说：2个相同字符间的位置关系如此
     * 1) .* 匹配 xxx
     * 2) (.) 匹配 a
     * 3) (.*\\1) 匹配 xxx a
     * 4) .* 匹配 xxx
     *
     * 注：\\1 表示对第一个捕获(.)的引用，因为(.)匹配了字符a，所以这里\\1引用后也是字符a
     * 同理，\\2表示对第2个捕获的引用；
     * 第几个捕获是用这些括号的左括号来定义的（第0个引用比较特殊，是整体），比如：(A)(B(C))，
     * \\0: (A)(B(C))
     * \\1: (A)
     * \\2: (B(C))
     * \\3: (C)
     */
    public boolean noRepeatCharRegex(String s) {
        return (s.length() < 128) && !s.matches(".*(.)(.*\\1).*");
    }

    public static void main(String[] args) {
        RepeatCharInString o = new RepeatCharInString();
//        System.out.println(o.noRepeatChar("aeiou"));
//        System.out.println(o.noRepeatChar("BarackObama"));
        System.out.println(o.noRepeatCharRegex("aeiou"));
        System.out.println(o.noRepeatCharRegex("BarackObama"));

//        regexTest();
    }

    public static void regexTest() {
        String s = "abc"; // \\0: abc
        System.out.println(s.matches("(.)(.(.))")); // true
        s = "abcd"; // \\0: abc
        System.out.println(s.matches("(.)(.(.))")); // false

        s = "abca"; // \\1: a
        System.out.println(s.matches("(.)(.(.))\\1")); // true
        s = "abcb"; // \\1: a
        System.out.println(s.matches("(.)(.(.))\\1")); // false

        s = "abcbc"; // \\2: bc
        System.out.println(s.matches("(.)(.(.))\\2")); // true
        s = "abcac"; // \\2: bc
        System.out.println(s.matches("(.)(.(.))\\2")); // false

        s = "abcc"; // \\3: c
        System.out.println(s.matches("(.)(.(.))\\3")); // true
        s = "abcb"; // \\3: c
        System.out.println(s.matches("(.)(.(.))\\3")); // false
    }
}
