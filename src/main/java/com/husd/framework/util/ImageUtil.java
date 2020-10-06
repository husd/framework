package com.husd.framework.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class ImageUtil {

    /**
     * 创建图片的缩略图
     */
    private void createThumbnail(String filename, int thumbWidth, int thumbHeight, int quality, String outFilename)
            throws InterruptedException, FileNotFoundException, IOException {
        // load image from filename
//        Image image = Toolkit.getDefaultToolkit().getImage(filename);
//        MediaTracker mediaTracker = new MediaTracker(new Container());
//        mediaTracker.addImage(image, 0);
//        mediaTracker.waitForID(0);
//        // use this to test for errors at this point: System.out.println(mediaTracker.isErrorAny());
//
//        // determine thumbnail size from WIDTH and HEIGHT
//        double thumbRatio = (double) thumbWidth / (double) thumbHeight;
//        int imageWidth = image.getWidth(null);
//        int imageHeight = image.getHeight(null);
//        double imageRatio = (double) imageWidth / (double) imageHeight;
//        if (thumbRatio < imageRatio) {
//            thumbHeight = (int) (thumbWidth / imageRatio);
//        } else {
//            thumbWidth = (int) (thumbHeight * imageRatio);
//        }
//
//        // draw original image to thumbnail image object and
//        // scale it to the new size on-the-fly
//        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
//        Graphics2D graphics2D = thumbImage.createGraphics();
//        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
//
//        // save thumbnail image to outFilename
//        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFilename));
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
//        quality = Math.max(0, Math.min(quality, 100));
//        param.setQuality((float) quality / 100.0f, false);
//        encoder.setJPEGEncodeParam(param);
//        encoder.encode(thumbImage);
//        out.close();
    }

    /**
     * 抓屏程序
     *
     * @param fileName
     * @throws Exception
     */
    public static void captureScreen(String fileName) throws Exception {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        ImageIO.write(image, "png", new File(fileName));
    }

    public static void main(String[] args) throws Exception {
        ImageUtil.captureScreen("a");
    }
}
