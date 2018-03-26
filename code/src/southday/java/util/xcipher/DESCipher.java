package southday.java.util.xcipher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * DES算法加密工具
 * @author southday
 * @date 2017年6月23日
 */
public class DESCipher implements XCipher {
    private static final String ALGORITHM = "DES";
    private static final String CHARSET = "UTF-8";
    private Cipher cipher = null;
    
    /**
     * 初始化加密工具
     * @param model 加密或解密
     * @param key
     * @throws Exception
     */
    private void initCipher(int model, String key) throws Exception {
        // 创建可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();
        // 根据原始密钥创建DESKeySpec对象
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(CHARSET));
        // 创建密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        // cipher完成解密操作
        this.cipher = Cipher.getInstance(ALGORITHM);
        this.cipher.init(model, secretKey, secureRandom);
    }

    @Override
    public String encrypt(String plaintext, String key) throws Exception {
        this.initCipher(Cipher.ENCRYPT_MODE,key);
        String ciphertext = new String(Base64.encodeBase64(cipher.doFinal(plaintext.getBytes(CHARSET))));
        return ciphertext;
    }

    @Override
    public String decrypt(String ciphertext, String key) throws Exception {
        this.initCipher(Cipher.DECRYPT_MODE, key);
        String plaintext = new String(cipher.doFinal(Base64.decodeBase64(ciphertext.getBytes())), CHARSET);
        return plaintext;
    }
    
    @Override
    public void encrypt(File in, File out, String key) throws Exception {
        this.initCipher(Cipher.ENCRYPT_MODE, key);
        BufferedReader bfr = new BufferedReader(new FileReader(in));
        BufferedWriter bfw = new BufferedWriter(new FileWriter(out));
        String line = null;
        while ((line = bfr.readLine()) != null) {
            String ciphertext = new String(Base64.encodeBase64(cipher.doFinal(line.getBytes(CHARSET))));
            bfw.write(ciphertext);
            bfw.flush();
        }
        bfr.close();
        bfw.close();
    }

    @Override
    public void decrypt(File in, File out, String key) throws Exception {
        this.initCipher(Cipher.DECRYPT_MODE, key);
        BufferedReader bfr = new BufferedReader(new FileReader(in));
        BufferedWriter bfw = new BufferedWriter(new FileWriter(out));
        String line = null;
        while ((line = bfr.readLine()) != null) {
            String plaintext = new String(cipher.doFinal(Base64.decodeBase64(line.getBytes())), CHARSET);
            bfw.write(plaintext);
            bfw.flush();
        }
        bfr.close();
        bfw.close();
    }
    
    public static void main(String[] args) throws Exception {
        String plaintext = "southday";
        String key = "southday";
        DESCipher cipher = new DESCipher();
        String ciphertext = cipher.encrypt(plaintext, key);
        System.out.println(ciphertext);
        System.out.println(cipher.decrypt(ciphertext, key));
        
        File in = new File("C:\\Users\\coco\\Desktop\\xx.txt");
        File out = new File("C:\\Users\\coco\\Desktop\\out.txt");
        File out2 = new File("C:\\Users\\coco\\Desktop\\out2.txt");
        cipher.encrypt(in, out, key);
        cipher.decrypt(out, out2, key);
    }
}
