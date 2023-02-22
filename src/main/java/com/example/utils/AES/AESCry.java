package com.example.utils.AES;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;


/**
 * @author xxx
 * &#064;date  2020-09-16 11:17
 **/
public class AESCry {

    private static final String AES = "AES";

    /**
     * 初始向量IV, 初始向量IV的长度规定为128位16个字节, 初始向量的来源为随机生成.
     */
    private static final byte[] KEY_VI = "UFeQaNYyRohRfjWQ".getBytes();

    /**
     * 加密解密算法/加密模式/填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";


    static {
        Security.setProperty("crypto.policy", "unlimited");
    }

    /**
     * AES加密
     */
    public static String encrypt(String content, AESKey aesKey) throws Exception {
        String key = aesKey.getKey();
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(KEY_VI));
        // 获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
        byte[] byteEncode = content.getBytes(StandardCharsets.UTF_8);

        // 根据密码器的初始化方式加密
        byte[] byteAES = cipher.doFinal(byteEncode);

        // 将加密后的数据转换为字符串
        return Base64.getEncoder().encodeToString(byteAES);

    }

    /**
     * AES解密
     */
    public static String decrypt(String content, AESKey aesKey) throws Exception {
        String key = aesKey.getKey();
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(KEY_VI));
        // 将加密并编码后的内容解码成字节数组
        byte[] byteContent = Base64.getDecoder().decode(content);
        // 解密
        byte[] byteDecode = cipher.doFinal(byteContent);
        return new String(byteDecode, StandardCharsets.UTF_8);
    }
}