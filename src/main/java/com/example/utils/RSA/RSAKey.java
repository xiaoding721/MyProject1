package com.example.utils.RSA;

import com.example.utils.AES.AESCry;
import com.example.utils.AES.AESKey;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAKey {

    private static String replacePublicKey(String key) {
        return key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("\r", "")
                .replace("\n", "")
                .replace("-----END PUBLIC KEY-----", "");
    }

    private static String replacePrivateKey(String key) {
        return key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("\r", "")
                .replace("\n", "")
                .replace("-----END PRIVATE KEY-----", "");
    }

    public static RSAPublicKey readPublicKey(File file) throws Exception {
        String key = Files.readString(file.toPath(), Charset.defaultCharset());
        String publicKeyPEM = replacePublicKey(key);
        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static String readPublicKeyToString(File file) throws Exception {
        String key = Files.readString(file.toPath(), Charset.defaultCharset());
        return replacePublicKey(key);
    }

    public static RSAPrivateKey readPrivateKey(File file) throws Exception {
        String key = Files.readString(file.toPath(), Charset.defaultCharset());
        String privateKeyPEM = replacePrivateKey(key);
        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public static String readPrivateKeyToString(File file) throws Exception {
        String key = Files.readString(file.toPath(), Charset.defaultCharset());
        return replacePrivateKey(key);
    }

    public static RSAPrivateKey readPrivateKeyWithAesDecrypt(File file, AESKey aesKey) throws Exception {
        String key = Files.readString(file.toPath(), Charset.defaultCharset());
        String privateKeyPEM = replacePrivateKey(key);
        String decrypt = AESCry.decrypt(privateKeyPEM, aesKey);
        byte[] decode = Base64.getDecoder().decode(decrypt);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decode);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public static String readPrivateKeyWithAesDecryptToString(File file, AESKey aesKey) throws Exception {
        String key = Files.readString(file.toPath(), Charset.defaultCharset());
        String privateKeyPEM = replacePrivateKey(key);
        return AESCry.decrypt(privateKeyPEM, aesKey);
    }

    public static RSAPublicKey readPublicKeyByCerFile(File file) throws CertificateException, FileNotFoundException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream(file));
        PublicKey publicKey = cert.getPublicKey();
        return (RSAPublicKey) publicKey;
    }

    public static String readPublicKeyByCerFileToString(File file) throws CertificateException, FileNotFoundException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream(file));
        PublicKey publicKey = cert.getPublicKey();
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static void writeAesEncryptPrivateKeyToFile(File file, String privateKey, AESKey aesKey) throws Exception {
        if (file == null) {
            throw new NullPointerException();
        }
        String encrypt = AESCry.encrypt(privateKey, aesKey);
        StringBuilder stringBuilder = new StringBuilder("-----BEGIN PRIVATE KEY-----\n");
        int rows = (encrypt.length() + 64 - 1) / 64;
        for (int i = 0; i < rows; i++) {
            if (i == rows - 1) {
                stringBuilder.append(encrypt.substring(i * 64));
            } else {
                stringBuilder.append(encrypt, i * 64, i * 64 + 64).append("\n");
            }
        }
        stringBuilder.append("\n-----END PRIVATE KEY-----");
        if (!file.exists()) {
            boolean ignore = file.createNewFile();
        }
        try (FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(stringBuilder.toString());
        }
    }

}
