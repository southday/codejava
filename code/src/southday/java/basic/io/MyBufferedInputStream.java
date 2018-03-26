package southday.java.basic.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class MyBufferedInputStream implements Closeable {
    InputStream in = null;
    private byte[] buf = new byte[1024];
    private int count = 0;
    private int pos = 0;
    private byte b = 0;

    MyBufferedInputStream(InputStream in) {
        this.in = in;
    }

    // 一次读一个字节，从缓冲区(字节数组)获取
    public int MyRead() throws IOException { // 【注意2】为什么这里返回类型是int 而不是byte
        // 通过in对象读取硬盘上的数据，并存储在buf中
        if (count == 0) {
            count = in.read(buf); // 当buf中的数据都取光的时候再读取数据
            if (count < 0) { // 读到文件末尾，返回-1
                return -1;
            }
            pos = 0; // 这时候buf[]中的指针归零
            b = buf[pos];
            pos++;
            count--;
//            return b; // 【注意1】这里如果你这样写：return b;就会挂掉，你可能会花3毫秒复制到一个2kb的文件(源文件7MB多)
            return b & 255;
        } else if (count > 0) {
            b = buf[pos];
            pos++;
            count--;
//            return b; // 【注意1】这里如果你这样写：return b;就会挂掉，你可能会花3毫秒复制到一个2kb的文件(源文件7MB多)
            return b & 255;
        }
        return -1;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        in.close();
    }
}

/* 下面是来解释上面两个【注意】的：
对于【注意1】：
因为被调用的时候是这样的：
    while ((ch = mbis.MyRead()) != -1) {
        bos.write(ch);
    }
而对于一些音频或者视频、图片数据,其二进制可能存在如下形式
    11111111 0000000 11111111 1111111 ...
而   return b; b = buf[pos], 是一个字节8bit
在读取过程中如果刚好有一次读取遇到这8个bit都为1的情况，如：
    b = 11111111 (二进制) -- 转为十进制 为 -1
这样的话，可能还没读取完数据就会因为这个byte而导致读取停止
因此也就会出现：本来文件大小为7MB，而复制完成后得到的复制品大小为2KB或者其他值

对于【注意2】：
因为【注意1】中已经提到：单独一个byte b 可能等于 11111111
那么我们设置返回值为  int  类型 (32bit)
return 的时候 对于一个byte 就会被提升为 int型(相当于4个byte)

                               1111111   十进制为 -1
    ↓  提升为int
    11111111 1111111 1111111 1111111   十进制为 -1
    
既然提升为int后值仍为 -1,那还提升干嘛？？
【提升后还是-1，是因为默认情况下在前面补的是1而不是0，我们只需要把最后一个1111111前面的位都变成0即可】

    1111111 1111111 1111111 1111111    (-1)
&   0000000 0000000 0000000 1111111  & (255)
---------------------------------------------
    0000000 0000000 0000000 1111111       (255)
这样一来，返回值由原来的-1 变成了  255, 就不会出现读取中途停止的情况了

【现在又有一点值得注意】：
MyRead()返回的是一个int(相当于4个byte),
而我们实际读取到的数据只为1个byte，在进行write()操作的时候难道要把4个byte都写入吗？？？
其实不是，其实在进行write()的时候, 只会将int中的最后8bit写入,前面的 24个bit会被隔离
应该在write()方法中存在着强制转型, 将int 强转为  byte
 */
