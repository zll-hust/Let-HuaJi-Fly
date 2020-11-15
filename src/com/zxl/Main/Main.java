package com.zxl.Main;

import com.zxl.GUI;
import com.zxl.Game;

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
                gui.huajiBlood.setVisible(true);
                gui.gameLevelLabel.setVisible(true);
                Game.enemyMovingSpeed = 100;
                gui.huajiBlood.setText("当前技能：闪现，加速，喷射眼泪");
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
