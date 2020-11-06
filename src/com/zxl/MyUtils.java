package com.zxl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Description:
 * 工具类，存放一些小函数
 *
 * @encode UTF-8
 */
public class MyUtils {

    /*
    * 获取随机颜色
    */
    public 	static String getRandomColor(Random r) {
        int count=(int) (r.nextInt(18)+1);
        String returnColor;
        switch (count) {
            case 1:
                returnColor = "#00FF00";
                break;
            case 2:
                returnColor = "#00FFFF";
                break;
            case 3:
                returnColor = "#00BFFF";
                break;
            case 4:
                returnColor = "#8A7CDA";
                break;
            case 5:
                returnColor = "#FF00FF";
                break;
            case 6:
                returnColor = "#FF0000";
                break;
            case 7:
                returnColor = "#FA663C";
                break;
            case 8:
                returnColor = "#B5B5B5";
                break;
            case 9:
                returnColor = "#BF487A";
                break;
            case 10:
                returnColor = "#90EE90";
                break;
            case 11:
                returnColor = "#C978E7";
                break;
            case 12: //boom
                returnColor = "#EED5D2";
                break;
            case 13:
                returnColor = "#52EA41";
                break;
            case 14:
                returnColor = "#FF7F00";
                break;
            case 15:
                returnColor = "#8B658B";
                break;
            case 16:
                returnColor = "#7FFFD4";
                break;
            case 17:
                returnColor = "#6A5ACD";
                break;
            case 18:
                returnColor = "#9BCD9B";
                break;
            default:
                returnColor = "#8B0A50";
                break;
        }

        return returnColor;
    }

    /*
    * 获取自定义字体
    */
    public static java.awt.Font getSelfDefinedFont(String filepath, int size){
        java.awt.Font font = null;
        File file = new File(filepath);
        try{
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, file);
            font = font.deriveFont(java.awt.Font.PLAIN, size);
        }catch (FontFormatException e){
            return null;
        }catch (FileNotFoundException e){
            return null;
        }catch (IOException e){
            return null;
        }
        return font;
    }

    /*
    * 读取历史最高分数记录
    */
    public static int readRecordFromFile(String filename) {
        int record = 0;
        try {
            Scanner in = new Scanner(new FileReader(filename));
            if(in.hasNext()) {
                record = in.nextInt();
            }else {
                System.out.println("no record!");
            }
            in.close();
        } catch (FileNotFoundException e) {
            // File not found
            System.out.println("File not found!");
        }
        return record;
    }

    /*
    * 存储历史分数记录，存放在txt文件内
    */
    public static void writeRecordInFile(String filename, int record) {
        File file = new File(filename);
        Writer writer = null;
        StringBuilder outputString = new StringBuilder();
        try {
            outputString.append(record);
            writer = new FileWriter(file, false);
            writer.write(outputString.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * 获得旋转后的透明背景图片
     * 参考：
     * https://blog.csdn.net/heliang7/article/details/7309394
     * https://blog.csdn.net/zixiaomuwu/article/details/51082056
     */
    public static BufferedImage Rotate(Image src, int angel) {
        //Math.abs是为了去除一个报错，不影响代码运行
        int src_width = Math.abs(src.getWidth(null));
        int src_height = Math.abs(src.getHeight(null));
        //计算新图片大小
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);
        //创建图片
        BufferedImage res = new BufferedImage(Math.abs(rect_des.width), Math.abs(rect_des.height), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        //透明
        res = g2.getDeviceConfiguration().createCompatibleImage(src_width, src_height, Transparency.TRANSLUCENT);
        g2 = res.createGraphics();
        //旋转
        g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
        //画图
        g2.drawImage(src, null, null);
        return res;
    }

    /*
    * 旋转函数的辅助函数
    */
    private static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // if angel is greater than 90 degree, we need to do some conversion
        if (angel >= 90) {
            if(angel / 90 % 2 == 1){
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new java.awt.Rectangle(new Dimension(des_width, des_height));
    }
}
