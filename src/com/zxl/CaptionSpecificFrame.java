package com.zxl;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CaptionSpecificFrame extends JFrame {
    private Image img = null; // 声明图像对象
    private CaptionSpecificPanel captionSpecificPanel = null; // 声明图像面板对象

    public static void main(String args[]) {
        CaptionSpecificFrame frame = new CaptionSpecificFrame();
        frame.setVisible(true);
    }

    public CaptionSpecificFrame() {
        super();
        URL imgUrl = CaptionSpecificFrame.class.getResource("/img/image.jpg");// 获取图片资源的路径
        img = Toolkit.getDefaultToolkit().getImage(imgUrl); // 获取图像资源
        captionSpecificPanel = new CaptionSpecificPanel(); // 创建图像面板对象
        this.setBounds(200, 160, 316, 237); // 设置窗体大小和位置
        this.add(captionSpecificPanel); // 在窗体上添加图像面板对象
        Thread thread = new Thread(captionSpecificPanel);// 创建线程对象
        thread.start();// 启动线程对象
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗体关闭模式
        this.setTitle("字幕从下向上滚动特效"); // 设置窗体标题
    }

    // 创建面板类
    class CaptionSpecificPanel extends JPanel implements Runnable {
        int x = 30;// 存储绘制点的x坐标
        int y = 216;// 存储绘制点的y坐标
        String value = "我爱Java";// 存储绘制的内容
        public void paint(Graphics g) {
            g.clearRect(0, 0, 316, 237);// 清除绘图上下文的内容
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);// 绘制图像
            Font font = new Font("黑体", Font.BOLD, 20);// 创建字体对象
            g.setFont(font);// 指定字体
            g.setColor(Color.RED);// 指定颜色
            g.drawString(value, x, y);// 绘制文本
        }
        public void run() {
            try {
                while (true) { // 读取内容
                    Thread.sleep(100); // 当前线程休眠1秒
                    if (y <= 216 - 50) {// 如果已经向上移动50像素
                        y = 216;// y坐标定位到最下方
                        if (value.equals("我爱Java")) {
                            value = "今天又是努力学习的一天";// 改变绘制的内容
                        } else {
                            value = "我爱Java";// 改变绘制的内容
                        }
                    } else {// 如果还没向上移动到50像素
                        y -= 2;// y坐标上移
                    }
                    repaint();// 调用paint()方法
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}