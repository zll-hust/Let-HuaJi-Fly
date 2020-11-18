package com.zxl.GUI;

import com.zxl.Game.Game;
import com.zxl.Roles.Player;
import com.zxl.Roles.Role;
import com.zxl.Roles.Wall;
import com.zxl.Utils.MyUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Description:
 * GUI，游戏整体界面，包括入口处界面
 *
 * @encode UTF-8
 */
public class GUI {
    public final int graphWidth;
    public int graphHeight;
    public final int STARTX = 150; // 开始图标的坐标
    public final int STARTY = 500;
    public final int EXITX = 300; // 结束图标的坐标
    public final int EXITY = 500;
    public final int HUAJIX = 200; // 滑稽的坐标
    public final int HUAJIY = 200;
    public static final int PROGRESSWIDTH = 40;
    public static final int BOTTOM = 70;
    public static final int LabelHeight = 20;

    public int mouseX;
    public int mouseY;
    public Role[] roles = new Role[Game.EnemyNr + Game.BulletNr + Game.TearNr + Game.PiNr + 3];
    public Wall[][] walls ;
    public PaintPanel conn ;// 游戏中的界面
    public JFrame jf; // 初始界面
    public JButton start; // 开始按钮
    public JButton exit; // 结束按钮
    public JLabel huaJi;
    public JLabel currentScoreLabel;
    public JLabel huajiBloodLabel; // 滑稽技能栏
    public JLabel historyScore; // 历史最高得分
    public JLabel score; // 当前得分
    public JLabel gameLevelLabel; // 游戏等级
    public JLabel letHuaJiFly;
    public ProgressUI jProBar; // 生命值
    public ProgressUI jProBar2; // 生命值
    Random rand = new Random();
    Player p;
    public JLabel backPic;
    public static Color initColor;

    public GUI() {
        jf = new JFrame("Let HuaJi Fly!"); // 顶部标题
        Toolkit kit = Toolkit.getDefaultToolkit(); // 自适应屏幕大小
        graphWidth = kit.getScreenSize().width;
        graphHeight = kit.getScreenSize().height - BOTTOM;
        conn = new PaintPanel(roles, walls, this);
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
        currentScoreLabel.setBounds(210, 80, 150, 20);

        historyScore = new JLabel();
        historyScore.setVisible(false);
        historyScore.setFont(font1);
        historyScore.setBounds(360, 80, 150, 20);

        huajiBloodLabel = new JLabel();
        huajiBloodLabel.setVisible(false);
        huajiBloodLabel.setFont(font1);
        huajiBloodLabel.setBounds(510, 80, 500, LabelHeight);

        gameLevelLabel = new JLabel();
        gameLevelLabel.setVisible(false);
        gameLevelLabel.setFont(font1);
        gameLevelLabel.setBounds(50, 80, 100, LabelHeight);

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
        conn.add(historyScore);
        conn.add(huajiBloodLabel);
        conn.add(gameLevelLabel);
        conn.add(letHuaJiFly);
        /*
         * 设置窗口图标
         */
        ImageIcon ig = new ImageIcon("./res/map.jpg");//这里放上你要设置图标图片
        Image im = ig.getImage();
        jf.setIconImage(im);

        /*
         * 设置窗口背景图片
         */
        // 背景图片
        ImageIcon background = new ImageIcon("./res/map.jpg");
        // 把背景图片显示在一个标签里面
        backPic = new JLabel(background);
        // 把标签的大小位置设置为图片刚好填充整个面板
        backPic.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
        conn.setOpaque(false);
        // 把背景图片添加到分层窗格的最底层作为背景
        jf.getLayeredPane().add(backPic, Integer.valueOf(Integer.MIN_VALUE));
        backPic.setVisible(false);

        jProBar = new ProgressUI();
        jProBar.getjProgressBar().setSize(graphWidth, PROGRESSWIDTH);
        jProBar.getjProgressBar().setLocation(0, 0);
        jProBar.getjProgressBar().setVisible(false);
        conn.add(jProBar.getjProgressBar());

        jProBar2 = new ProgressUI();
        jProBar2.getjProgressBar().setSize(graphWidth, PROGRESSWIDTH);
        jProBar2.getjProgressBar().setLocation(0, PROGRESSWIDTH);
        jProBar2.getjProgressBar().setVisible(false);
        conn.add(jProBar2.getjProgressBar());


        //获取鼠标坐标，作为滑稽的运动方向
        jf.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        jf.setFocusable(true);
        jf.setContentPane(conn);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 画出所有角色
     */
    public void printAllEnemies() {
        jf.getContentPane().repaint();
    }

    /**
     * 在图中画出新的角色
     */
    public void updateRole(Role c) {
        if (c != null) {
            if (c.getID() == Game.EnemyNr ||
                    c.getID() == Game.EnemyNr + Game.BulletNr ||
                    c.getID() == Game.EnemyNr + Game.BulletNr + Game.TearNr ||
                    c.getID() == Game.EnemyNr + Game.BulletNr + Game.TearNr + Game.PiNr) {
                roles[c.getID()] = c;
                jf.getContentPane().repaint();
            } else {
                roles[c.getID()] = c;
            }
        }
    }

    /*
     * 在图中去掉某角色
     */
    public void removeRole(Role c) {
        roles[c.getID()] = null;
    }

    /*
     * 游戏结束，清空界面的角色
     */
    public void clearRole() {
        for (int i = 0; i < roles.length; i++) {
            roles[i] = null;
        }
        jf.getContentPane().repaint();
    }

    public Wall[][] getWalls(){
        return walls;
    }

    public void createWalls(){
        this.walls = Wall.createNewWallRandom(this);
        conn.walls = this.walls;
    }

    public void createWalls(int level){
        this.walls = Wall.createNewWall(this,level);
        conn.walls = this.walls;
    }

    public void deleteWalls(){
        this.walls = null;
        conn.walls = this.walls;
    }
}