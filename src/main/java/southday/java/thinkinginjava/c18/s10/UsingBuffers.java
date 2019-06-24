package southday.java.thinkinginjava.c18.s10;

/* Buffer中4个索引：mark（标记）<= position（位置）<= limit（界限）<= capacity（容量）
 */

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * 使用CharBuffer交换相邻字符
 * @author southday
 * @date 2019/6/21
 */
public class UsingBuffers {
    public static void symmetricScramble(CharBuffer cb) {
        while (cb.hasRemaining()) {
            cb.mark();
            char c1 = cb.get();
            if (cb.hasRemaining()) {
                char c2 = cb.get();
                cb.reset();
                cb.put(c2).put(c1);
            }
        }
    }

    public static void main(String[] args) {
//        char[] data = "UsingBuffers".toCharArray();
        char[] data = "aUsingBuffers".toCharArray();
        ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
        CharBuffer cb = bb.asCharBuffer();
        cb.put(data);
        System.out.println(cb.rewind());
        symmetricScramble(cb);
        System.out.println(cb.rewind());
        symmetricScramble(cb);
        System.out.println(cb.rewind());
    }
}
