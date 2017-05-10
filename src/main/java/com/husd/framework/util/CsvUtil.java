package com.husd.framework.util;

import au.com.bytecode.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Created by hushengdong on 3/22/17.
 */
public class CsvUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtil.class);

    public static boolean write2Csv(File file, List<String[]> data) {

        if (file == null || data == null || data.size() == 0) {
            return false;
        }
        CSVWriter csvWriter = null;
        try {
            csvWriter
                    = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GBK")));
            csvWriter.writeAll(data);
            return true;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("write to csv error:", e);
        } catch (FileNotFoundException e) {
            LOGGER.error("write to csv error:", e);
        } finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
                //ignore
            }
        }
        return false;
    }
}
