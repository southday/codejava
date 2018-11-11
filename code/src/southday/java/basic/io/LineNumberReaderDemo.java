package southday.java.basic.io;

import java.io.FileReader;
import java.io.IOException;

/* Reader
 *  |-- BuffereReader
 *         |-- LineNumberReader
 */

public class LineNumberReaderDemo {
    public static void main(String[] args) {
//        FileReader fr = null;
//        LineNumberReader lnr = null;
//        try {
//            fr = new FileReader("E:\\Hello.java");        
//            lnr = new LineNumberReader(fr);
//            String line = null;
//            lnr.setLineNumber(100); // 让行号从100开始
//            while ((line = lnr.readLine()) != null) {
//                System.out.println(lnr.getLineNumber() + ": " + line);
//            }
//            
//        } catch (IOException e) {
//            System.out.println(e.toString());
//        }
        
        // 使用MyLineNumberReader玩玩
        FileReader fr = null;
        MyLineNumberReader mlnr = null;
        try {
            fr = new FileReader("E:\\Hello.java");
            mlnr = new MyLineNumberReader(fr);
            String line = null;
            mlnr.setLineNumber(10);
            while ((line = mlnr.MyReadLine()) != null) {
                System.out.println(mlnr.getLineNumber() + ": " + line);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
