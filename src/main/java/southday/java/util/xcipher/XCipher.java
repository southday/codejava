package southday.java.util.xcipher;

import java.io.File;

/**
 * 加解密工具接口
 * @author southday
 * @date 2017年6月23日
 */
public interface XCipher {
    /**
     * 加密文件
     * @param in
     * @param out
     * @param key
     * @throws Exception
     */
    void encrypt(File in, File out, String key) throws Exception;
    
    /**
     * 解密文件
     * @param in
     * @param out
     * @param key
     * @throws Exception
     */
    void decrypt(File in, File out, String key) throws Exception;

    /**
     * 加密字符串
     * @param plaintext
     * @param key
     * @return
     * @throws Exception
     */
    String encrypt(String plaintext, String key) throws Exception;
    
    /**
     * 解密字符串
     * @param ciphertext
     * @param key
     * @return
     * @throws Exception
     */
    String decrypt(String ciphertext, String key) throws Exception;
}
