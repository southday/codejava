package southday.java.util.xcipher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
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
    private static final int BUFFER_SIZE = 1024;
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
        InputStream ins = new FileInputStream(in);
        OutputStream outs = new FileOutputStream(out);
        CipherInputStream cins = new CipherInputStream(ins, cipher);
        byte[] buf = new byte[BUFFER_SIZE];
        int len = 0;
        while ((len = cins.read(buf)) > 0) {
            outs.write(buf, 0, len);
        }
        cins.close();
        ins.close();
        outs.close();
    }

    @Override
    public void decrypt(File in, File out, String key) throws Exception {
        this.initCipher(Cipher.DECRYPT_MODE, key);
        InputStream ins = new FileInputStream(in);
        OutputStream outs = new FileOutputStream(out);
        CipherOutputStream cos = new CipherOutputStream(outs, cipher);
        byte[] buf = new byte[BUFFER_SIZE];
        int len = 0;
        while ((len = ins.read(buf)) > 0) {
            cos.write(buf, 0, len);
        }
        cos.close();
        outs.close();
        ins.close();
    }
    
    public static void main(String[] args) throws Exception {
        String plaintext = "hello world!";
        String key = "hello world!";
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
