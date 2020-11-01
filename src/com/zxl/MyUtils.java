package com.zxl;

import java.awt.*;
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
}
