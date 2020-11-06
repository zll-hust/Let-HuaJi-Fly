package com.zxl;

import java.awt.*;
import java.util.Random;

import static com.zxl.Missile.createNewMissile;

/**
 * Description:
 * 游戏类，包含所有游戏运行相关内容
 *
 * @encode UTF-8
 */
public class Game {
    public static final int ORIGNALR = 15;//球的大小，要删掉
    public static final int EnemyNr = 5; // 敌人数量
    public static final int MAX = 100;
    public static final int MIN = 10;
    public static volatile boolean gamePlaying; // 是否正在进行游戏
    public static int enemyMovingSpeed = 100;
    public GUI gui;

    public static Random random;

    /*
     * 判断是否碰撞，若未膨胀返回true
     */
    public boolean boom(Role a, Role b) {
        return a.getR() + b.getR() >=
                Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    /*
     * 开始游戏，包括初始化敌人、对敌人进行多线程控制
     */
    public synchronized void startGame(final GUI gui) throws InterruptedException {
        this.gui = gui;

        final Player[] player = {new Player(gui.mouseX, gui.mouseY, ORIGNALR, EnemyNr, "#00FF00", gui, MAX)};
        final Role[] enemies = new Role[EnemyNr];
        gamePlaying = true;
        random = new Random();

        for (int i = 0; i < EnemyNr; i++) {
            if (i < EnemyNr / 4 * 3) { //控制怪物数量，3/4为导弹
                do {
                    enemies[i] = createNewMissile(i, player[0], gui);
                } while (boom(enemies[i], player[0]));
            } else {
                do {
                    enemies[i] = createNewMissile(i, player[0], gui);
                } while (boom(enemies[i], player[0]));
            }
        }
        gui.jf.getContentPane().repaint();


        class playerMovingCircle implements Runnable {
            @Override
            public synchronized void run() {
                System.out.println("player moving");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (gamePlaying && player != null) {
                    player[0].move();
                }
                System.out.println("player done");
            }
        }

        class enemyMoving implements Runnable {
            public synchronized void run() {
                System.out.println("enemies moving");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                while (gamePlaying && player != null) {
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i] == null) {
                            do {
                                enemies[i] = createNewMissile(i, player[0], gui);
                            } while (boom(enemies[i], player[0]));
                        }
                        enemies[i].move();
                    }
                    gui.printAllEnemies();
                    try {
                        Thread.sleep(enemyMovingSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("enemies done");
            }
        }

        class countScore implements Runnable {
            public synchronized void run() {
                System.out.println("counting score");
                while (gamePlaying) {
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i] != null && player != null) {
                            if (boom(enemies[i], player[0])) {
                                //导弹检测，扣除生命值
                                if (enemies[i].type == 2) {
                                    //TODO
                                    gui.jProBar.addValue(-20);
                                }
                                else if (enemies[i].type == 3) {
                                    //TODO
                                }
                                //药剂检测，恢复生命值
                                else if (enemies[i].type == 6) {
                                    //TODO
                                    gui.jProBar.addValue(10);
                                }

                                // 刚碰撞后，会有一秒钟无敌时间
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }

                for (int i = 0; i < enemies.length; i++) {
                    enemies[i] = null;
                }

                player[0] = null;
                gui.jf.getContentPane().setBackground(Color.RED);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                backMain(gui);
            }
        }

        class progressUI implements Runnable {
            public synchronized void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (gui.jProBar.getValue() > 0 && gamePlaying) {
//                    gui.jProBar.addValue(-1);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                gamePlaying = false;
            }
        }

        playerMovingCircle pmc = new playerMovingCircle();
        Thread playerMC = new Thread(pmc);
        enemyMoving em = new enemyMoving();
        Thread eM = new Thread(em);
        countScore cs = new countScore();
        Thread cS = new Thread(cs);
        progressUI pUI = new progressUI();
        Thread tProgress = new Thread(pUI);

        playerMC.start();
        eM.start();
        cS.start();
        tProgress.start();
    }

    /*
     * 游戏结束，退回到主菜单
     */
    public void backMain(GUI gui) {
        gui.jf.getContentPane().setBackground(GUI.initColor);
        gui.jf.setBounds(gui.graphWidth / 2 - 300, gui.graphHeight / 2 - 400, 600, 800);
        gui.exit.setVisible(true);
        gui.start.setVisible(true);
        gui.letHuaJiFly.setText("再试试看吧！");
        gui.letHuaJiFly.setVisible(true);
        gui.letHuaJiFly.setBounds(120, 120, 500, 100);
        gui.jProBar.getjProgressBar().setVisible(false);
        gui.clearRole();
        gui.jf.getContentPane().repaint();
    }
}
