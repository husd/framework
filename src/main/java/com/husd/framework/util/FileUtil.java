package com.husd.framework.util;

import com.google.common.collect.Lists;
import com.husd.framework.excel.ExcelCommonBean;
import com.husd.framework.excel.ExcelUtility;

import java.io.*;
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

    /**
     * 向文件末尾添加内容
     *
     * @param fileName 包含路径的文件名称
     * @param content  要写入的内容 注意是追加到文件末尾
     * @return
     */
    public int append2FileEnd(String fileName, String content) {

        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true))) {
            out.write(content);
        } catch (IOException e) {
            // error processing code
            return 0;
        } finally {
        }
        return 1;
    }

    /**
     * 使用NIO进行快速文件拷贝
     */
    public static void fileCopy(File in, File out)
            throws IOException {
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            int maxCount = (64 * 1024 * 1024) - (32 * 1024);
            long size = inChannel.size();
            long position = 0;
            while (position < size) {
                position += inChannel.transferTo(position, maxCount, outChannel);
            }
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    /**
     * 列出文件和目录
     *
     * @param directoryName
     */
    public void showFileAndDict(String directoryName) {
        File dir = new File(directoryName);
        String[] children = dir.list();
        if (children == null) {
            // Either dir does not exist or is not a directory
        } else {
            for (int i = 0; i < children.length; i++) {
                // Get filename of file or directory
                String filename = children[i];
            }
        }

        // It is also possible to filter the list of returned files.
        // This example does not return any files that start with `.'.
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        };
        children = dir.list(filter);

        // The list of files can also be retrieved as File objects
        File[] files = dir.listFiles();

        // This filter only returns directories
        FileFilter fileFilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
        files = dir.listFiles(fileFilter);
    }
}
