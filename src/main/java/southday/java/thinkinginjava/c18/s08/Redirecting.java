package southday.java.thinkinginjava.c18.s08;

import java.io.*;

/* I/O 重定向
 * 1) I/O 重定向操纵的是字节流，不是字符流
 */

/**
 * 标准输入/输出/异常流重定向
 * @author southday
 * @date 2019/6/20
 */
public class Redirecting {
    public static void main(String[] args) throws Exception {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("basic-in.txt"));
        PrintStream out = new PrintStream((new BufferedOutputStream(new FileOutputStream("basic-out.txt"))));
        PrintStream err = new PrintStream(new BufferedOutputStream(new FileOutputStream("basic-err.txt")));

        System.setIn(in);
        System.setOut(out);
        System.setErr(err);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Integer v = Integer.valueOf(line);
                    System.out.println(v);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        } finally {
            br.close();
            System.out.close();
            System.err.close();
        }
    }
}
