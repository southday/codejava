package southday.java.thinkinginjava.c18.s06;

/* RandomAccessFile
 * 1) 使用 RandomAccessFile，类似于组合使用DataInputStream和DataOutputStream，因为它实现了相同的接口：DataInput和DataOutput；
 * 2) 使用 RandomAccessFile，必须知道文件排版，这样才能正确操作它
 * 3) RandomAccessFile 与 I/O继承层次结构的其他部分实现了分离，不支持装饰；
 * 4) 不支持只写 "w"格式，mode只能为：r, rw, rws, rwd
 *   "r"     Open for reading only. Invoking any of the write methods of the resulting object will cause an IOException to be thrown.
 *   "rw"    Open for reading and writing. If the file does not already exist then an attempt will be made to create it.
 *   "rws"   Open for reading and writing, as with "rw", and also require that every update to the file's content or metadata be written synchronously to the underlying storage device.
 *   "rwd"   Open for reading and writing, as with "rw", and also require that every update to the file's content be written synchronously to the underlying storage device.
 *
 * 什么情况下要使用 RandomAccessFile 呢？
 * 1) 使用多线程下载大文件（格式已知），将文件划分为多个块，多个子线程独立下载后再整合；
 */

import java.io.RandomAccessFile;

/**
 * RandomAccessFile
 * @author southday
 * @date 2019/6/20
 */
public class UsingRandomAccessFile {
    public static void main(String[] args) throws Exception {
        RandomAccessFile writer = new RandomAccessFile("data.txt", "rw");
        writer.writeDouble(12.0); // 第0个8B
        writer.writeDouble(13.0); // 第1个8B
        writer.writeDouble(14.0); // 第2个8B
        writer.writeUTF("southday");
        writer.close();

        RandomAccessFile rw = new RandomAccessFile("data.txt", "rw");
        rw.seek(2*8); // 跳到第2个8Byte开始的地方（从0开始计数），即：14.0
        rw.writeDouble(15.0);
        rw.close();

        display("data.txt");
    }

    public static void display(String data) throws Exception {
        RandomAccessFile r = new RandomAccessFile(data, "r");
        for (int i = 0; i < 3; i++)
            System.out.println(r.readDouble());
        System.out.println(r.readUTF());
        r.close();
    }
}
