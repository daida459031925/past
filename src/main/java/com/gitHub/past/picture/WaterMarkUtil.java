package com.gitHub.past.picture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片添加水印
 */
public class WaterMarkUtil {
    // 水印透明度
    private static float alpha = 0.3f;
    // 水印横向位置
    private static int positionWidth = 50;
    // 水印纵向位置
    private static int positionHeight = 100;
    // 水印文字字体
    private static Font font = new Font("宋体", Font.BOLD, 60);
    // 水印文字颜色
    private static Color color = Color.red;

    /**
     * 给图片添加水印文字
     *
     * @param text       水印文字
     * @param srcImgPath 源图片路径
     * @param targetPath 目标图片路径
     */
    public static void markImage(String text, String srcImgPath, String targetPath,Integer wz) {
        markImage(text, srcImgPath, targetPath, null,wz);
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     *
     * @param text 水印文字
     * @param srcImgPath 源图片路径
     * @param targetPath 目标图片路径
     * @param degree 水印旋转
     * @param wz 水印位置 location 位置： 1、左上角，2、右上角，3、左下角，4、右下角，5、中间
     */
    public static void markImage(String text, String srcImgPath, String targetPath, Integer degree,Integer wz) {

        OutputStream os = null;
        try {
            // 0、图片类型
            String type = srcImgPath.substring(srcImgPath.indexOf(".") + 1, srcImgPath.length());

            // 1、源图片
            Image srcImg = ImageIO.read(new File(srcImgPath));

            int imgWidth = srcImg.getWidth(null);
            int imgHeight = srcImg.getHeight(null);

            BufferedImage buffImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)

            //水印的宽高
            Map<String, Integer> watermarkLength = getWatermarkLength(text, g);
            int syHeight = watermarkLength.get("height");
            int syWidth = watermarkLength.get("width");

            // 如果水印图片高或宽大于目标图片是做的处理，使水印宽或高等于目标图片的宽高，并且等比例缩放
            if (syWidth > imgWidth) {
                syWidth = imgWidth;
                syHeight = (int) ((double)syWidth / syWidth * imgHeight);
            }

            if (syHeight > imgHeight) {
                syHeight = imgHeight;
                syWidth = (int) ((double)syHeight / syHeight * syWidth);
            }

            // 根据位置参数确定坐标位置
            int x = 0, y = 0;
            // location 位置： 1、左上角，2、右上角，3、左下角，4、右下角，5、中间
            switch (wz) {
                case 1:
                    y = syHeight;
                    break;
                case 2:
                    x = imgWidth - syWidth;
                    y = syHeight;
                    break;
                case 3:
                    y = imgHeight - syHeight;
                    break;
                case 4:
                    x = imgWidth - syWidth;
                    y = imgHeight - syHeight;
                    break;
                case 5:
                    x = (imgWidth - syWidth) / 2;
                    y = (imgHeight - syHeight) / 2;
                    break;
                default: break;
            }

            //设置水印的坐标
            g.drawString(text, x, y);  //画出水印
//            g.drawString(text, positionWidth, positionHeight);
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            os = new FileOutputStream(targetPath);
            // ImageIO.write(buffImg, "JPG", os);
            ImageIO.write(buffImg, type.toUpperCase(), os);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给图片添加水印文字、可设置水印文字的旋转角度
     * @param text 水印文字
     * @param inputStream 源图片路径
     * @param outputStream 目标图片路径
     * @param degree 水印旋转
     * @param typeName
     */
    public static void markImageByIO(String text, InputStream inputStream, OutputStream outputStream,
                                     Integer degree, String typeName) {
        try {
            // 1、源图片
            Image srcImg = ImageIO.read(inputStream);

            int imgWidth = srcImg.getWidth(null);
            int imgHeight = srcImg.getHeight(null);
            BufferedImage buffImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 8、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)

            g.drawString(text, positionWidth, positionHeight);
            // 9、释放资源
            g.dispose();
            // 10、生成图片
            ImageIO.write(buffImg, typeName.toUpperCase(), outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String srcImgPath = "/home/sga/图片/2020-05-22 15-01-21 的屏幕截图.png";
        String text = LocalDateTime.now().toString();

        String targerTextPath = "/home/sga/图片/002--1.png";
        String targerTextPath2 = "/home/sga/图片/002--2.png";

        System.out.println("给图片添加水印文字开始...");
        // 给图片添加水印文字
        markImage(text, srcImgPath, targerTextPath,4);
        // 给图片添加水印文字,水印文字旋转-45
        markImage(text, srcImgPath, targerTextPath2, -0,5);
        System.out.println("给图片添加水印文字结束...");

    }

    public static Map<String,Integer> getWatermarkLength(String waterMarkContent, Graphics2D g) {
        Map<String,Integer> map = new HashMap<>();
        FontMetrics fontMetrics = g.getFontMetrics(g.getFont());
        map.put("height",fontMetrics.getHeight());
        map.put("width",fontMetrics.charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length()));
        return map;
    }
}
