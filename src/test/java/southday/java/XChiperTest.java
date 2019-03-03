package southday.java;

import southday.java.util.xcipher.DESCipher;
import southday.java.util.xcipher.XCipher;

import java.io.File;

/**
 * Author southday
 * Date   2019/2/12
 */
public class XChiperTest {

    public static void main(String[] args) throws Exception {
        XCipher cipher = new DESCipher();

        String key = "key";
        for (int i = 33; i <= 40; i++) {
            File in = new File("F:\\Pictures\\0082018工程实践选题\\01第一批选题\\0" + i + ".mp4");
            File out = new File("F:\\Pictures\\0082018工程实践选题\\01第一批选题\\enc_0" + i + ".mp4");
            File dec = new File("F:\\Pictures\\0082018工程实践选题\\01第一批选题\\dec_0" + i + ".mp4");
            cipher.encrypt(in, out, key);
            cipher.decrypt(out, dec, key);
            System.out.println("0" + i + ".mp4 over...");
        }
    }
}
