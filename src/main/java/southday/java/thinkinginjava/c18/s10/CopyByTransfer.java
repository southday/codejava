package southday.java.thinkinginjava.c18.s10;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 使用transferTo、transferFrom来实现文件拷贝 P554
 * @author southday
 * @date 2019/6/21
 */
public class CopyByTransfer {
    public static void main(String[] args) throws Exception {
        FileChannel
                in = new FileInputStream("data.txt").getChannel(),
                out = new FileOutputStream("data_copy.txt").getChannel();
        // 1) transferTo
//        in.transferTo(0, in.size(), out);
        // or 2) transferFrom
        out.transferFrom(in, 0, in.size());
    }
}
