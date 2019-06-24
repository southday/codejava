package southday.java.thinkinginjava.c18.s06;

import java.io.*;

/**
 * 使用DataInputStream 和 DataOutputStream 进行格式化读入和写出，可以恢复和存储数据
 * @author southday
 * @date 2019/6/20
 */
public class StoringAndRecoveringData {
    public static void main(String[] args) throws Exception {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("data.txt")));
        dos.writeByte(23);
        dos.writeBoolean(true);
        dos.writeChar('L');
        dos.writeShort(19);
        dos.writeInt(120);
        dos.writeFloat(44.5f);
        dos.writeDouble(23.024);
        dos.writeUTF("southday");
        dos.write(new byte[]{31, 32, 33});
        dos.close();

        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream("data.txt")));
        System.out.println(dis.readByte());
        System.out.println(dis.readBoolean());
        System.out.println(dis.readChar());
        System.out.println(dis.readShort());
        System.out.println(dis.readInt());
        System.out.println(dis.readFloat());
        System.out.println(dis.readDouble());
        System.out.println(dis.readUTF());
        byte[] buf = new byte[1024];
        dis.read(buf);
        for (int i = 0; i < buf.length && buf[i] != '\0'; i++) {
            System.out.print(buf[i] + " ");
        }
        System.out.println();
    }
}
