package com.example.utils.AES;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AESKey {
    private final String aesKey;

    /**
     * 密钥, 256位32个字节
     */
    public static final String DEFAULT_SECRET_KEY = "ByNZKeisCvJqx73REPJpVgCAWdl3FXZH";

    public AESKey(String key) {
        try {
            if (key == null || key.length() != 32) {
                key += DEFAULT_SECRET_KEY;
                aesKey = fillKey(key);
            } else {
                aesKey = key;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public AESKey(File file) {
        try {
            String key = Files.readString(file.toPath(), Charset.defaultCharset());
            if (key == null || key.length() != 32) {
                key += DEFAULT_SECRET_KEY;
                aesKey = fillKey(key);
            } else {
                aesKey = key;
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String fillKey(String aesKey) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");// 生成一个SHA512加密计算摘要
        messageDigest.update(aesKey.getBytes());// 计算sha512函数
        /*
         * digest()最后确定返回md5 hash值，返回值为8位字符串。
         * 因为md5 hash值是16位的hex值，实际上就是8位的字符
         * BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
         * 一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
         */
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }

    public String getKey() {
        return this.aesKey;
    }


}
