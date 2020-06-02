package com.husd.framework.util;

import com.google.common.collect.Lists;
import com.husd.framework.excel.ExcelCommonBean;
import com.husd.framework.excel.ExcelUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author hushengdong
 * @date 2020/6/2
 */
public class FileUtil {

    /**
     * 把字符串写入到文件内容中去
     *
     * @param content
     * @param name
     * @throws IOException
     */
    public static void writeNIO(String content, String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(name, true);
        FileChannel channel = fos.getChannel();
        ByteBuffer buf = ByteBuffer.wrap(content.getBytes());
        buf.put(content.getBytes());
        buf.flip();
        channel.write(buf);
        channel.close();
        fos.close();
    }

    public List<String> readFrom(String path) {

        String fileName = this.getClass().getClassLoader().getResource(path).getPath();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    public List<ExcelCommonBean> readExcelFrom(String path) {

        String fileName = this.getClass().getClassLoader().getResource(path).getPath();
        String[] properties = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        try {
            ExcelUtility<ExcelCommonBean> utility = ExcelUtility
                    .newImportBuilder(new File(fileName), ExcelCommonBean.class, properties)
                    .maxUploadSum(1000)
                    .build();
            return utility.readExcel();
        } catch (Exception e) {
        }
        return Lists.newArrayList();
    }

    public static boolean isExists(File file) {

        return file != null && file.exists();
    }
}
