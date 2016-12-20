package com.husd.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class FileUtil {

    public static void download(HttpServletRequest request, HttpServletResponse response,
            String filePath, String realName) {
        File file = new File(filePath);
        download(request, response, file);
    }

    public static void download(HttpServletRequest request, HttpServletResponse response,
            File file) {

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setContentType("application/octet-stream");
            String userAgent = request.getHeader("User-Agent");

            String filePath = file.getAbsolutePath();
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
            String rtn = encodeByBrowseType(userAgent, fileName);
            response.setHeader("Content-disposition", "attachment; " + rtn);
            response.setHeader("Content-Length", String.valueOf(file.length()));

            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            // ignore this exception
        } finally {
            try {
                bis.close();
            } catch (IOException e1) {
            }
            try {
                bos.close();
            } catch (IOException e1) {
            }
            file.delete();
        }
    }

    private static String encodeByBrowseType(String userAgent, String fileName)
            throws UnsupportedEncodingException {

        String newName = URLEncoder.encode(fileName, "UTF-8");
        // 默认按IE的方式来编码
        if (StringUtils.isBlank(userAgent)) {
            return "filename=\"" + newName + "\"";
        }
        String rtn = "";
        userAgent = userAgent.toLowerCase();
        // IE浏览器
        if (userAgent.indexOf("msie") != -1) {
            rtn = "filename=\"" + newName + "\"";
        }
        // Opera浏览器只能采用filename*
        else if (userAgent.indexOf("opera") != -1) {
            rtn = "filename*=UTF-8''" + newName;
        }
        // Safari浏览器，只能采用ISO编码的中文输出
        else if (userAgent.indexOf("safari") != -1) {
            rtn = "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
        }
        // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
        else if (userAgent.indexOf("applewebkit") != -1) {
            newName = MimeUtility.encodeText(fileName, "UTF8", "B");
            rtn = "filename=\"" + newName + "\"";
        }
        // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
        else if (userAgent.indexOf("mozilla") != -1) {
            rtn = "filename*=UTF-8''" + newName;
        }
        return rtn;
    }
}
