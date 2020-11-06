package com.zxl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Description:
 * GUI，游戏整体界面，包括入口处界面
 *
 * @encode UTF-8
 */
public class GUI {
    public final int graphWidth;
    public final int graphHeight;
    public final int STARTX = 150; // 开始图标的坐标
    public final int STARTY = 500;
    public final int EXITX = 300; // 结束图标的坐标
    public final int EXITY = 500;
    public final int HUAJIX = 200; // 滑稽的坐标
    public final int HUAJIY = 200;
    public static final int PROGRESSWIDTH = 40;
    public static final int BOTTOM = 70;

    public int mouseX;
    public int mouseY;
    public Role[] roles = new Role[Game.EnemyNr + 3];
    public PaintPanel conn = new PaintPanel(roles, "paintPanel");// 游戏中的界面
    public JFrame jf; // 初始界面
    public JButton start;
    public JButton exit;
    public JLabel huaJi;
    public JLabel currentScoreLabel;
    public JLabel maxScoreLabel;
    public JLabel gameLevelLabel;
    public JLabel letHuaJiFly;
    public ProgressUI jProBar; // 生命值

    public static Color initColor;

    public GUI() {
        jf = new JFrame("Let HuaJi Fly!"); // 顶部标题
        Toolkit kit = Toolkit.getDefaultToolkit(); // 自适应屏幕大小
        graphWidth = kit.getScreenSize().width;
        graphHeight = kit.getScreenSize().height - BOTTOM;

        jf.setBounds(graphWidth / 2 - 300, graphHeight / 2 - 400, 600, 800); // 设定位置

        jf.setLayout(null); // 清空布局管理器
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        conn.setLayout(null);
        start = new JButton();
        start.setBounds(STARTX, STARTY, 200, 70);
        exit = new JButton();
        exit.setBounds(EXITX, EXITY, 200, 70);

        start.setIcon(new ImageIcon("./res/start.png"));
        start.setMargin(new Insets(0, 0, 0, 0));//将边框外的上下左右空间设置为0
        start.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0
        start.setBorderPainted(false);//不打印边框
        start.setBorder(null);//除去边框
        start.setFocusPainted(false);//除去焦点的框
        start.setContentAreaFilled(false);//除去默认的背景填充


        exit.setIcon(new ImageIcon("./res/exit.png"));
        exit.setMargin(new Insets(0, 0, 0, 0));//将边框外的上下左右空间设置为0
        exit.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0
        exit.setBorderPainted(false);//不打印边框
        exit.setBorder(null);//除去边框
        exit.setFocusPainted(false);//除去焦点的框
        exit.setContentAreaFilled(false);//除去默认的背景填充

        Font font1 = MyUtils.getSelfDefinedFont("./res/hkww.ttc", 18);
        Font font2 = MyUtils.getSelfDefinedFont("./res/font.ttf", 60);

        currentScoreLabel = new JLabel();
        currentScoreLabel.setVisible(false);
        currentScoreLabel.setFont(font1);
        currentScoreLabel.setBounds(10, 40, 200, 20);

        maxScoreLabel = new JLabel();
        maxScoreLabel.setVisible(false);
        maxScoreLabel.setFont(font1);
        maxScoreLabel.setBounds(10, 60, 200, 20);

        gameLevelLabel = new JLabel();
        gameLevelLabel.setVisible(false);
        gameLevelLabel.setFont(font1);
        gameLevelLabel.setBounds(10, 80, 200, 20);

        letHuaJiFly = new JLabel("让滑稽飞");
        letHuaJiFly.setVisible(true);
        letHuaJiFly.setFont(font2);
        letHuaJiFly.setBounds(200, 120, 500, 100);
        letHuaJiFly.setForeground(Color.decode("#8A2BE2"));

        huaJi = new JLabel();
        huaJi.setBounds(HUAJIX, HUAJIY, 300, 300);
        huaJi.setIcon(new ImageIcon("./res/normal2.png"));
        huaJi.setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0
        huaJi.setBorder(null);//除去边框

        conn.add(start);
        conn.add(exit);
        conn.add(huaJi);
        conn.add(currentScoreLabel);
        conn.add(maxScoreLabel);
        conn.add(gameLevelLabel);
        conn.add(letHuaJiFly);

        jProBar = new ProgressUI();
        jProBar.getjProgressBar().setSize(graphWidth, PROGRESSWIDTH);
        jProBar.getjProgressBar().setLocation(0, 0);
        jProBar.getjProgressBar().setVisible(false);
        conn.add(jProBar.getjProgressBar());

        jf.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY() - 20;
            }
        });
        jf.setContentPane(conn);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     * 画出所有角色
     */
    public void printAllEnemies() {
        jf.getContentPane().repaint();
    }

    /*
     * 在图中画出新的角色
     */
    public void updateRole(Role c) {
        if (c != null) {
            if (c.getID() == Game.EnemyNr) {
                roles[c.getID()] = c;
                jf.getContentPane().repaint();
            } else {
                roles[c.getID()] = c;
            }
        }
    }

    /*
    * 游戏结束，清空界面的角色
    */
    public void clearRole(){
        for(int i = 0; i < roles.length; i++){
            roles[i] = null;
        }
        jf.getContentPane().repaint();
    }
}