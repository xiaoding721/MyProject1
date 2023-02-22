package com.example.demo;

import com.example.bean.CCTVSearchRequest;
import com.example.bean.RequestData;
import com.example.utils.AES.AESCry;
import com.example.utils.AES.AESCryFile;
import com.example.utils.AES.AESKey;
import com.example.utils.FileUtils;
import com.example.utils.HttpClient;
import com.example.utils.RSA.RSACry;
import com.example.utils.RSA.RSAKey;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
class DemoApplicationTests {
    static ClassLoader classLoader = DemoApplicationTests.class.getClassLoader();
    static AESKey key = new AESKey(new File(Objects.requireNonNull(classLoader.getResource("key/aes.key")).getFile()));

    public static void main(String[] args) throws Exception {

        test1();


    }

    private static String test2(String url) {
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36")
                    .timeout(3000)
                    .post();
            Elements allElements = document.getAllElements();
            for (Element element : allElements) {
                String s = element.className();
                if ("text_box_02".equals(s)) {
                    return element.child(2).toString();
                }
            }
        } catch (Exception e) {
            System.out.println(url + "\n");
        }
        return null;
    }

    private static void test1() throws IOException {
        Map<String, String> parameter = new HashMap<>();
        {
            parameter.put("page", "1");
            parameter.put("qtext", "%E5%B0%8F%E7%86%8A%E7%BB%B4%E5%B0%BC%E5%92%8C%E8%B7%B3%E8%B7%B3%E8%99%8E");
            parameter.put("sort", "relevance");
            parameter.put("pageSize", "20");
            parameter.put("type", "video");
            parameter.put("vtime", "-1");
            parameter.put("datepid", "1");
            parameter.put("channel", "");
            parameter.put("pageflag", "1");
            parameter.put("qtext_str", "%E5%B0%8F%E7%86%8A%E7%BB%B4%E5%B0%BC%E5%92%8C%E8%B7%B3%E8%B7%B3%E8%99%8E");
        }

        String jsonStr = HttpClient.get("https://search.cctv.com/ifsearch.php", parameter);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CCTVSearchRequest cctvSearchRequest = objectMapper.readValue(jsonStr, CCTVSearchRequest.class);

        Map<String, String> urlList = new HashMap<>();
        for (RequestData requestData : cctvSearchRequest.getList()) {
            urlList.put(requestData.getAll_title(), requestData.getUrllink());
        }

        Integer totalpage = cctvSearchRequest.getTotalpage();
        for (int i = 2; i <= totalpage; i++) {
            parameter.put("page", String.valueOf(i));
            String tempJsonStr = HttpClient.get("https://search.cctv.com/ifsearch.php", parameter);
            cctvSearchRequest = objectMapper.readValue(tempJsonStr, CCTVSearchRequest.class);
            for (RequestData requestData : cctvSearchRequest.getList()) {
                urlList.put(requestData.getAll_title(), requestData.getUrllink());
            }
        }

        Map<String, String> stringMap = new HashMap<>();
        for (Map.Entry<String, String> entry : urlList.entrySet()) {
            // builder.append(entry.getKey()).append(";").append(entry.getValue()).append("\n");

            stringMap.put(entry.getKey(), test2(entry.getValue()));
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            builder.append(entry.getKey()).append(";").append(entry.getValue()).append("\n");
        }
        FileUtils.writeTxt(builder.toString());
    }

    static void method9() throws Exception {
        File file = new File("C:\\新建文件夹\\CloudMusic - 副本");
        AESCryFile.aesDecryptFiles(file, key);
    }

    static void method8() throws Exception {
        File file = new File("C:\\新建文件夹\\CloudMusic - 副本");
        AESCryFile.aesEncryptFiles(file, key);
    }

    static void method7() throws Exception {
        AESCryFile.aesEncryptFileForOutput("C://图片1.png", key);
    }

    static void method6() throws Exception {
        AESCryFile.aesDecryptFileForOutput("C://图片1.png.cry", key);
    }

    static void method5() {

    }

    static String method4() throws Exception {
        String rsaPrivateKey = RSAKey.readPrivateKeyToString(new File(classLoader.getResource("key/rsa_private.key").getFile()));
        String encrypt = AESCry.encrypt(rsaPrivateKey, key);
        String decrypt = AESCry.decrypt(encrypt, key);
        return encrypt;
    }

    static void method3() throws Exception {
        File file = new File(classLoader.getResource("key/rsa_aes_private.key").getFile());
        String rsaPrivateKey = RSAKey.readPrivateKeyWithAesDecryptToString(file, key);
        System.out.println(rsaPrivateKey);
    }

    static void method2() throws Exception {
        String rsaPrivateKey = RSAKey.readPrivateKeyToString(new File(classLoader.getResource("key/rsa_private.key").getFile()));
        File file = new File("C://rsa_aes_private.key");
        RSAKey.writeAesEncryptPrivateKeyToFile(file, rsaPrivateKey, key);
    }

    static void method1() throws Exception {
        RSAPublicKey rsaPublicKey = RSAKey.readPublicKey(new File(classLoader.getResource("key/rsa_public.key").getFile()));
        RSAPublicKey rsaPublicKey1 = RSAKey.readPublicKeyByCerFile(new File(classLoader.getResource("key/ca.crt").getFile()));
        String rsaPrivateKey = RSAKey.readPrivateKeyToString(new File(classLoader.getResource("key/rsa_private.key").getFile()));
        String s = RSACry.publicKeyEncrypt("123456", rsaPublicKey1);

        String s2 = AESCry.encrypt(rsaPrivateKey, key);
        System.out.println(System.lineSeparator());
    }


    @SneakyThrows
    static String md5(String context) {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");// 生成一个SHA512加密计算摘要
        messageDigest.update(context.getBytes());// 计算sha512函数
        /*
         * digest()最后确定返回md5 hash值，返回值为8位字符串。
         * 因为md5 hash值是16位的hex值，实际上就是8位的字符
         * BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
         * 一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
         */
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }

}
