package com.husd.framework.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author hushengdong
 */
public class VerificationCode {

    private Logger logger = LoggerFactory.getLogger(VerificationCode.class);

    /**
     * 获取图片
     *
     * @param re
     * @param response
     * @param pic_addr 图片路径(数据库中的字段)
     */
    @RequestMapping("showimage")
    public void showImage(HttpServletRequest re, HttpServletResponse response, String pic_addr) {
        // pic为读取到图片的存储路径（数据库中存储的字段值）
        response.setContentType("image/*");
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(pic_addr);
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            logger.error("图片查询出错", e);
        } finally {
            try {
                if (fis != null)
                    fis.close();
                os.close();
            } catch (IOException e) {
                // ignore it
            }
        }
    }

    @RequestMapping("/query/donot/set/default/vendor/count")
    public void queryDonotSetDefaultVendor(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        // pic为读取到图片的存储路径（数据库中存储的字段值）
        response.setContentType("image/*");
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            BufferedImage image = getDonotSetVenderCountImage("234");
            ImageIO.write(image, "jpg", os);
            os.flush();
        } catch (Exception e) {
            logger.error("图片查询出错", e);
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
                os.close();
            } catch (IOException e) {
                // ignore it
            }
        }
    }

    public BufferedImage getDonotSetVenderCountImage(String count) {// 得到图片的方法
        int w = 8 * count.length();
        int h = 20;
        BufferedImage img = CreateImage(w, h);
        Graphics gps = img.getGraphics();// 获取当前图片的画笔
        // 开始画东西
        for (int i = 0; i < 1; i++) {
            gps.setColor(new Color(0, 0, 0));
            gps.drawString(count, w / 4 * i, h - 5);// 宽度让其不满图片
        }
        return img;
    }

    public BufferedImage CreateImage(int w, int h) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics gps = img.getGraphics();
        gps.setColor(new Color(240, 240, 240));
        gps.fillRect(0, 0, w, h);
        return img;
    }

    public static void main(String args[]) throws IOException {
        VerificationCode c = new VerificationCode();
        BufferedImage image = c.getDonotSetVenderCountImage("1234");
        File file = new File("D:/test.jpg");
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream os = new FileOutputStream(file);
        ImageIO.write(image, "jpg", os);
    }


}
