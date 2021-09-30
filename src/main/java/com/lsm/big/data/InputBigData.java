package com.lsm.big.data;

import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

public class InputBigData {
    public static void main(String[] args) throws Exception {
        String line = System.getProperty("line.separator");
        File file = new File("/Users/xiaosi/develop/big-data.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        for (int i = 0; i < 5500000; i++) {
            fileWriter.write(UUID.randomUUID().toString().replace("-", "") + "," + i);
            fileWriter.write(line);
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
