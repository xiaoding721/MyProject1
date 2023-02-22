package com.example.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileUtils {

    public static void writeTxt(String str) throws IOException {
        // 1. 使用File类打开一个文件；
        File file = new File("c:" + File.separator + "test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        // 2. 通过字节流或字符流的子类指定输出的位置
        Writer fw = new FileWriter(file);
        // 3. 进行写操作
        fw.write(str);
        // 4. 关闭输出
        fw.close();
    }
}
