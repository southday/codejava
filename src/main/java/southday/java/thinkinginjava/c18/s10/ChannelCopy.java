package southday.java.thinkinginjava.c18.s10;

/*
1) 一旦调用rand()来告知FileChannel向ByteBuffer存储字节，就必须调用缓冲器上的flip()方法，其设置limit为当前位置；不这样做的话，可能读取到“脏数据”（其他人的数据）
2) buffer.clear()用于清空缓冲器：position -> 0，limit -> capacity，可以为下一次reading做准备
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用Channel来拷贝文件 P553
 * @author southday
 * @date 2019/6/21
 */
public class ChannelCopy {
    private static final int BUFF_SIZE = 100;

    public static void main(String[] args) throws Exception {
        FileChannel in = new FileInputStream("data.txt").getChannel();
        FileChannel out = new FileOutputStream("data_copy.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFF_SIZE);
        while (in.read(buffer) != -1) {
            buffer.flip(); // (1) prepare for writing
            out.write(buffer);
            buffer.clear(); // (2) prepare for reading
        }
    }

    /*
    1.将(1)注释掉，发现：data_copy.txt中没有任何内容，为什么会这样呢？
    2.将(2)注释掉，发现：程序往data_copy.txt中无限写数据（重复data.txt中的完整内容），为什么会这样呢？

    等后面看到实现原理，再来补充
     */

}
