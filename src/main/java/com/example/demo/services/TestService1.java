package com.example.demo.services;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class TestService1 {

//    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        TestService1 testService1 = new TestService1();
//
//        File publicKey = new File(testService1.getURL("key/rsa_public.key").getFile());
//        File privateKey = new File(testService1.getURL("key/rsa_private.key").getFile());
//        RSAPublicKey rsaPublicKey = testService1.readPublicKey(publicKey);
//        System.out.println(rsaPublicKey.getPublicExponent());
//        RSAPrivateKey rsaPrivateKey = testService1.readPrivateKey(privateKey);
//        System.out.println(rsaPrivateKey.getPrivateExponent());
//    }
//
//    private URL getURL(String resource){
//        return getClass().getClassLoader().getResource(resource);
//    }

//    public RSAPublicKey readPublicKey(File file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        String key = Files.readString(file.toPath(), Charset.defaultCharset());
//        String publicKeyPEM = key
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replaceAll(System.lineSeparator(), "")
//                .replace("-----END PUBLIC KEY-----", "");
//        byte[] encoded = Base64.decodeBase64(publicKeyPEM);
//
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
//        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
//    }
//
//    public RSAPrivateKey readPrivateKey(File file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        String key = Files.readString(file.toPath(), Charset.defaultCharset());
//
//        String privateKeyPEM = key
//                .replace("-----BEGIN PRIVATE KEY-----", "")
//                .replaceAll(System.lineSeparator(), "")
//                .replace("-----END PRIVATE KEY-----", "");
//
//        byte[] encoded = Base64.decodeBase64(privateKeyPEM);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
//        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
//    }

//    public RSAPublicKey readPublicKey(File file) throws Exception {
//        KeyFactory factory = KeyFactory.getInstance("RSA");
//
//        try (FileReader keyReader = new FileReader(file);
//             PemReader pemReader = new PemReader(keyReader)) {
//
//            PemObject pemObject = pemReader.readPemObject();
//            byte[] content = pemObject.getContent();
//            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
//            return (RSAPublicKey) factory.generatePublic(pubKeySpec);
//        }
//    }
//
//
//    public RSAPrivateKey readPrivateKey(File file) throws Exception {
//        KeyFactory factory = KeyFactory.getInstance("RSA");
//
//        try (FileReader keyReader = new FileReader(file);
//             PemReader pemReader = new PemReader(keyReader)) {
//
//            PemObject pemObject = pemReader.readPemObject();
//            byte[] content = pemObject.getContent();
//            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
//            return (RSAPrivateKey) factory.generatePrivate(privKeySpec);
//        }
//    }
}
