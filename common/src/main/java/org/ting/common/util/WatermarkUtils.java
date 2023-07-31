//package org.ting.common.util;
//
//import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//
///**
// * 图片水印
// *
// * @author ting
// * @version 1.0
// * @date 2023/7/3
// */
//public class WatermarkUtils {
//    /**
//     * 水印位置
//     */
//    public enum Position {
//        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, MIDDLE;
//    }
//
//    /**
//     * 添加水印
//     * todo 位置暂未实现
//     *
//     * @param txt
//     * @param position
//     */
//    public static void addWatermark(String txt, Position position, String tarImgPath) {
//        File file = new File("D:\\tupian\\1b81ced2f7c1c8abde12f066f46ed1ad.jpeg");
//        try (FileInputStream inputStream = new FileInputStream(file);
//             FileOutputStream outImgStream = new FileOutputStream(tarImgPath)) {
//            Image image = ImageIO.read(file);
//            int height = image.getHeight(null);
//            int width = image.getWidth(null);
//            // 1.创建图片缓存对象
//            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//            // 2.创建图形
//            Graphics2D graphics = bufferedImage.createGraphics();
//            // 3.绘画将原来的图片绘制到图形中
//            graphics.drawImage(image, 0, 0, width, height, null);
//            // 4.添加水印
//            //根据图片的背景设置水印颜色
//            graphics.setColor(new Color(0, 0, 255, 128));
//            //设置字体  画笔字体样式为微软雅黑，加粗，文字大小为60pt
//            graphics.setFont(new Font("微软雅黑", Font.BOLD, 50));
//            // 水印透明度
//            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, CharacterType.ALPHA));
//            // 5.设置水印的位置和文字
//            // 获取水印的长度
//            int watermarkLength = watermarkLength(txt, graphics);
//            int finalWidth = width - watermarkLength;
//            graphics.drawString(txt, (width / 2), height / 2);
//            graphics.dispose();
//            // 6输出图片
//            ImageIO.write(bufferedImage, "png", outImgStream);
//            System.out.println("添加水印完成");
//            outImgStream.flush();
//        } catch (Exception e) {
//            System.out.println("添加水印失败," + e.getCause());
//        }
//
//
//    }
//
//    /**
//     * 获取水印的长度
//     */
//
//    private static int watermarkLength(String txt, Graphics2D graphics) {
//        return graphics.getFontMetrics(graphics.getFont()).charsWidth(txt.toCharArray(), 0, txt.length());
//    }
//
//    public static void main(String[] args) {
//        WatermarkUtils.addWatermark("水印----", Position.TOP_LEFT, "C:\\Users\\EDY\\Pictures\\test-水印.png");
//    }
//}
