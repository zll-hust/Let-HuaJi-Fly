package com.zxl.Main;

import com.zxl.GUI.GUI;
import com.zxl.Game.Game;
import com.zxl.Utils.MyUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Description:
 * 主函数运行类，项目入口
 *
 * @encode UTF-8
 */
public class Main {
    public static void main(String[] args) {
        final Game main = new Game();
        final GUI gui = new GUI();
        System.out.println("gui初始化完成！");
        Game.gamePlaying = false;
        GUI.initColor = gui.jf.getContentPane().getBackground();
        Game.historyScore = MyUtils.readRecordFromFile("./res/record.txt");
        gui.jf.getContentPane().repaint();

        // 开始游戏
        gui.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.start.setVisible(false);
                gui.exit.setVisible(false);
                gui.backPic.setVisible(true);
                gui.letHuaJiFly.setVisible(false);
                gui.huaJi.setVisible(false);
                gui.currentScoreLabel.setVisible(true);
                gui.historyScore.setVisible(true);
                gui.huajiBloodLabel.setVisible(true);
                gui.gameLevelLabel.setVisible(true);
                Game.enemyMovingSpeed = 100;
                gui.currentScoreLabel.setText("当前得分：" + Game.score);
                gui.historyScore.setText("历史最高分：" + Game.historyScore);
                gui.huajiBloodLabel.setText("技能栏：闪现 D ，加速 S，喷射眼泪 W");
                gui.gameLevelLabel.setText("难度等级：" + Game.GameLevel);
                gui.jProBar.getjProgressBar().setValue(100);
                gui.jProBar.getjProgressBar().setVisible(true);
                gui.jProBar.reset();
                gui.jProBar2.getjProgressBar().setValue(0);
                gui.jProBar2.getjProgressBar().setVisible(true);
                gui.jf.setExtendedState(JFrame.MAXIMIZED_BOTH);//设置窗口最大化
                try {
                    main.startGame(gui);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // 直接关闭窗口
        gui.jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MyUtils.writeRecordInFile("./res/record.txt", Game.historyScore);
            }
        });

        // 按键退出游戏
        gui.exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyUtils.writeRecordInFile("./res/record.txt", Game.historyScore);
                System.exit(0);
            }
        });
    }
}
