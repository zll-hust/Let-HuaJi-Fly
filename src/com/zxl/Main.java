package com.zxl;

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


        gui.jf.getContentPane().repaint();

        // 开始游戏
        gui.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.start.setVisible(false);
                gui.exit.setVisible(false);
                gui.letHuaJiFly.setVisible(false);
                gui.huaJi.setVisible(false);
                gui.currentScoreLabel.setVisible(true);
                gui.maxScoreLabel.setVisible(true);
                gui.gameLevelLabel.setVisible(true);
                Game.enemyMovingSpeed = 100;
                gui.gameLevelLabel.setText("难度等级：" + (100 - Game.enemyMovingSpeed));
                gui.jProBar.getjProgressBar().setValue(100);
                gui.jProBar.getjProgressBar().setVisible(true);
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
                //TODO
            }
        });

        // 按键退出游戏
        gui.exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
                System.exit(0);
            }
        });
    }
}
