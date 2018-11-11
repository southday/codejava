package southday.java.basic.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/* 因为BuffereReader中已经有现成的方法可以使用，
 * 所以我们可以通过extends BuffereReader 来借用一些方法
 * 其实 LineNumberReader就是这样做的，它是个装饰类d
 * 不过LineNumberReader类没有直接使用 readline()方法，而是复写了
 */

public class MyLineNumberReader extends BufferedReader {
    private int LineNumber = 0;

    MyLineNumberReader(Reader r) {
        super(r);
    }

    public String MyReadLine() throws IOException {
        LineNumber++;
        return super.readLine();
        /*
        StringBuilder sb = new StringBuilder();
        int ch = 0;
        while ((ch = r.read()) != -1) {
            if (ch == '\r')
                continue;
            if (ch == '\n')
                return sb.toString();
            else
                sb.append((char) ch);
        }
        if (sb.length() != 0) {
            return sb.toString();
        }
        return null; // 读到文件末尾 返回null
        */
    }

    public void setLineNumber(int LineNumber) {
        this.LineNumber = LineNumber;
    }

    public int getLineNumber() {
        return this.LineNumber;
    }
    
    /*
    public void Myclose() throws IOException {
        r.close();
    }
    */
}
