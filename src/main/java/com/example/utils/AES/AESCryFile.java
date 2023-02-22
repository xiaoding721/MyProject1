package com.example.utils.AES;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Security;

public class AESCryFile {

    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final String AES = "AES";

    private static final AESCryFile aesCryFile = new AESCryFile();

    /**
     * 初始向量IV, 初始向量IV的长度规定为128位16个字节, 初始向量的来源为随机生成.
     */
    private static final byte[] KEY_VI = "UFeQaNYyRohRfjWQ".getBytes();

    static {
        Security.setProperty("crypto.policy", "unlimited");
    }

    public static void aesEncryptFiles(File file, AESKey aesKey) throws Exception {
        if (file == null || !file.exists()) return;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        aesEncryptFiles(f, aesKey);
                    } else {
                        aesEncryptFileForInput(f, aesKey);
                    }
                }
            }
        } else {
            aesEncryptFileForInput(file, aesKey);
        }
    }

    public static void aesDecryptFiles(File file, AESKey aesKey) throws Exception {
        if (file == null || !file.exists()) return;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    aesDecryptFiles(f, aesKey);
                }
            }
        } else {
            aesDecryptFileForInput(file, aesKey);
        }
    }

    public static void aesEncryptFileForOutput(String sourceFilePath, AESKey aesKey) throws Exception {
        File sourceFile = new File(sourceFilePath);
        aesCryFile.aesFileForOutput(sourceFile, aesKey, Cipher.ENCRYPT_MODE);
    }

    public static void aesDecryptFileForOutput(String sourceFilePath, AESKey aesKey) throws Exception {
        File sourceFile = new File(sourceFilePath);
        aesCryFile.aesFileForOutput(sourceFile, aesKey, Cipher.DECRYPT_MODE);
    }

    public static void aesEncryptFileForInput(File sourceFile, AESKey aesKey) throws Exception {
        aesCryFile.aesFileForInput(sourceFile, aesKey, Cipher.ENCRYPT_MODE);
    }

    public static void aesDecryptFileForInput(File sourceFile, AESKey aesKey) throws Exception {
        aesCryFile.aesFileForInput(sourceFile, aesKey, Cipher.DECRYPT_MODE);
    }


    /**
     * 通过文件输入流加密文件并输出到指定路径
     * CipherOutputStream进行加密数据
     */
    private void aesFileForOutput(File sourceFile, AESKey aesKey, int mode) throws Exception {
        File destFile = checkFile(sourceFile, mode);
        String key = aesKey.getKey();
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(mode, secretKey, new IvParameterSpec(KEY_VI));
        // 对输出流包装
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destFile);
             CipherOutputStream cipherOut = new CipherOutputStream(out, cipher)) {
            byte[] cache = new byte[1024];
            int nRead;
            while ((nRead = in.read(cache)) != -1) {
                cipherOut.write(cache, 0, nRead);
                cipherOut.flush();
            }
        }
        boolean ignore = sourceFile.delete();
    }

    /**
     * 通过文件输入流加密文件并输出到指定路径
     * CipherInputStream进行加密数据
     */
    private void aesFileForInput(File sourceFile, AESKey aesKey, int mode) throws Exception {
        File destFile = checkFile(sourceFile, mode);
        String key = aesKey.getKey();
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(mode, secretKey, new IvParameterSpec(KEY_VI));
        // 对输入流包装
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destFile);
             CipherInputStream cipherIn = new CipherInputStream(in, cipher)) {
            byte[] cache = new byte[1024];
            int nRead;
            while ((nRead = cipherIn.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
        }
        boolean ignore = sourceFile.delete();
    }

    private File checkFile(File sourceFile, int mode) throws IOException {
        if (mode == Cipher.ENCRYPT_MODE) {
            if (!sourceFile.exists() && !sourceFile.isFile()) {
                throw new IllegalArgumentException("需要加密的源文件不存在");
            }
            File destFile = new File(sourceFile.getPath() + ".cry");
            boolean ignore2 = destFile.createNewFile();
            return destFile;
        } else {
            if (!sourceFile.exists() && !sourceFile.isFile()) {
                throw new IllegalArgumentException("需要解密的源文件不存在");
            }
            File destFile = new File(sourceFile.getPath().replace(".cry", ""));
            boolean ignore2 = destFile.createNewFile();
            return destFile;
        }
    }
}
