package com.zxl;

import java.awt.*;
import java.util.Random;

/**
 * Description:
 * 游戏类，包含所有游戏运行相关内容
 *
 * @encode UTF-8
 */
public class Game {
    public static final int ORIGNALR = 15;
    public static final int EnemyNr = 50; // 敌人数量
    public static final int MAX = 100;
    public static final int MIN = 10;
    public static int historyScore = 0; // 历史最高分数
    public static volatile int score = 0; // 当前分数
    public static volatile boolean gamePlaying; // 是否正在进行游戏
    public static volatile boolean lookingScore;
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

        final Player[] player = {new Player(gui.mouseX, gui.mouseY, ORIGNALR, EnemyNr, "#000000", gui, MAX)};
        final Role[] enemies = new Role[EnemyNr];
        score = 0;
        gamePlaying = true;
        random = new Random();

        for (int i = 0; i < EnemyNr; i++) {
            if (i < EnemyNr / 4 * 3) {
                do {
                    enemies[i] = createNewRocket(i);
                } while (boom(enemies[i], player[0]));
            } else {
                do {
                    enemies[i] = createNewPlane(i);
                } while (boom(enemies[i], player[0]));
            }
        }
        gui.jf.getContentPane().repaint();

//        System.out.println("start and score has already unvisible");

        class playerMovingCircle implements Runnable {
            @Override
            public synchronized void run() {
                System.out.println("player moving");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (gamePlaying && player[0] != null) {
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
                while (gamePlaying && player[0] != null) {
                    for (int i = 0; i < enemies.length; i++) {
                        if (enemies[i] == null) {
                            int enermyR;
                            if (i < EnemyNr / 4 * 3) {
                                enermyR = random.nextInt(player[0].getR() - MIN) + MIN;
                            } else {
                                enermyR = random.nextInt(MAX - player[0].getR()) + player[0].getR();
                            }
                            do {
                                enemies[i] = new Role(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                                        - enermyR * 2 - GUI.BOTTOM) + enermyR + GUI.PROGRESSWIDTH, enermyR, i, MyUtils.getRandomColor(random),
                                        gui, random.nextInt(3) + 1, random.nextInt(3) + 1);
                            } while (boom(enemies[i], player[0]));
                            if (i == EnemyNr / 2) {
                                do {
                                    enemies[i] = new Role(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                                            - enermyR * 2 - GUI.BOTTOM) + enermyR + GUI.PROGRESSWIDTH, enermyR, i, "#FFFF00",
                                            gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
                                } while (boom(enemies[i], player[0]));
                            }
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
                        if (enemies[i] != null && player[0] != null) {
                            if (boom(enemies[i], player[0])) {
                                //炸弹检测
                                if (enemies[i].color.equals("#EED5D2")) {
                                    gamePlaying = false;
                                    break;
                                }
                                //药 检测
                                if (enemies[i].color.equals("#6A5ACD")) {
                                    gui.jProBar.addValue(5);
                                    enemies[i] = null;
                                    continue;
                                }
                                //普通球检测
                                if (player[0].getR() > enemies[i].getR()) {
                                    if (i != EnemyNr / 2) {
                                        player[0].resize(1);
                                        score++;
                                        gui.currentScoreLabel.setText("当前成绩：" + score);
                                        if (score != 0 && score % 10 == 0) {
                                            if (enemyMovingSpeed > 40) {
                                                enemyMovingSpeed -= 20;
                                            } else if (enemyMovingSpeed > 20) {
                                                enemyMovingSpeed -= 5;
                                            } else if (enemyMovingSpeed > 1) {
                                                enemyMovingSpeed -= 1;
                                            }
                                            gui.gameLevelLabel.setText("难度等级：" + (100 - enemyMovingSpeed));
                                        }
                                        gui.jProBar.addValue(3);
                                    } else {
                                        player[0].resize(-1 * (player[0].getR() - ORIGNALR));
                                    }
                                    enemies[i] = null;
                                } else {
                                    gamePlaying = false;
                                    break;
                                }
                            }
                        }
                    }
                }

                for (int i = 0; i < enemies.length; i++) {
                    enemies[i] = null;
                }

                if (score > historyScore) {
                    historyScore = score;
                }

                player[0] = null;
                gui.jf.getContentPane().setBackground(Color.RED);
                //gui.jf.getContentPane().repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                backMain(gui);
                System.out.println(score);
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
                    gui.jProBar.addValue(-1);
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
        gui.maxScoreLabel.setText("历史最高：" + historyScore);
        gui.clearRole();
        gui.jf.getContentPane().repaint();
    }

    /*
     * 产生怪物1：导弹
     */
    public Role createNewRocket(int i) {
        int enermyR = 10;
        return new Role(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                - enermyR * 2 - GUI.BOTTOM) + enermyR + GUI.PROGRESSWIDTH, enermyR, i, "#EED5D2",
                gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
    }

    /*
    * 生成怪物2：AI机器人
    */
    public Role createNewPlane(int i) {
        int enermyR = 20;
        return new Role(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                - enermyR * 2 - GUI.BOTTOM) + enermyR + GUI.PROGRESSWIDTH, enermyR, i, MyUtils.getRandomColor(random),
                gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
    }

    /*
     * 生成怪物3：派大星
     */
    public Role createPiBigStar(int i) {
        int enermyR = 50;
        return new Role(random.nextInt(gui.graphWidth - enermyR * 2) + enermyR, random.nextInt(gui.graphHeight
                - enermyR * 2 - GUI.BOTTOM) + enermyR + GUI.PROGRESSWIDTH, enermyR, i, MyUtils.getRandomColor(random),
                gui, random.nextInt(10) + 1, random.nextInt(10) + 1);
    }
}
