package com.zxl.Game;

import com.zxl.GUI.GUI;
import com.zxl.Roles.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;


/**
 * Description:
 * 游戏类，包含所有游戏运行相关内容
 *
 * @encode UTF-8
 */
public class Game {
    public static final int EnemyNr = 15; // 敌人数量
    public static final int BulletNr = 15; // 子弹数量
    public static final int TearNr = 100; // 眼泪数量
    public static final int PiNr = 16; // 痞老板数量
    public static volatile boolean gamePlaying; // 是否正在进行游戏
    public static int playerMovingSpeed = 70;  // 滑稽运动速度
    public static int enemyMovingSpeed = 100; // 敌人运动速度（线程sleep时间）
    public static int PlayerShootingSpeed = 200; // 滑稽射击速度
    public static int BeforeBossShooting = 1000; // 派大星发射前摇
    public static int BossShootingSpeed = 500; // 派大星发射速度
    public static int bulletShootingSpeed = 1000; // AI机器人发射子弹速度
    public GUI gui;

    public static int GameLevel = 0; // 游戏关卡，随时间变化变化
    public static int historyScore = 0;
    public static int score = 0;

    public final int FlashTimes = 2;   //闪现技能最大使用次数
    public final int SpeedTimes = 1;   //加速技能最大使用次数
    public final int CryTimes = 1;   //喷水技能最大使用次数

    public int speedTimeCal = 0; // 加速技能计时器

    public int speedUsedTimes = 0; // 加速技能还可以使用的次数
    public int flashUsedTimes = 0; // 加速还可以使用的次数
    public int cryUsedTimes = 0; // 喷水还可以使用的次数

    public static Random random;

    /**
     * 判断是否碰撞，若未碰撞返回true
     */
    public boolean boom(Role a, Role b) {
        return a.getR() + b.getR() >=
                Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    /**
     * 开始游戏，包括初始化敌人、对敌人进行多线程控制
     */
    public synchronized void startGame(final GUI gui) throws InterruptedException {
        this.gui = gui;
        final Role[] roles = new Role[EnemyNr + BulletNr + TearNr + PiNr];
        gui.createWalls(GameLevel);
        final Wall[][] walls = gui.getWalls();
        final Player[] player = {new Player(gui.mouseX, gui.mouseY, roles.length, gui, walls)};

        gamePlaying = true;
        random = new Random();

        // 创建怪物
        for (int i = 0; i < EnemyNr; i++)
            createRoles(i, roles, player);

        gui.jf.getContentPane().repaint();

        // 滑稽移动
        class playerMoving implements Runnable {
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
                    try {
                        Thread.sleep(playerMovingSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("player done");
            }
        }

        // 控制角色移动
        class enemyMoving implements Runnable {
            public synchronized void run() {
                System.out.println("roles moving");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                while (gamePlaying && player != null) {
                    // 处理怪物移动
                    for (int i = 0; i < EnemyNr; i++) {
                        if (roles[i] == null) {
                            createRoles(i, roles, player);
                        }
                        roles[i].move();
                    }
                    // 处理子弹移动
                    for (int i = EnemyNr; i < EnemyNr + BulletNr; i++) {
                        Bullet b = (Bullet) roles[i];
                        if (b != null) {
                            if (b.moveForecast()) {
                                gui.removeRole(b);
                                roles[i] = null;
                            } else {
                                b.move();
                            }
                        }
                    }

                    // 处理眼泪/痞老板移动
                    for (int i = EnemyNr + BulletNr; i < EnemyNr + BulletNr + TearNr + PiNr; i++) {
                        if (roles[i] != null) {
                            roles[i].move();
                        }
                    }

                    gui.printAllEnemies();
                    try {
                        Thread.sleep(enemyMovingSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("roles done");
            }
        }

        // 控制AI机器人发射的子弹类射击
        class bulletShooting implements Runnable {
            public synchronized void run() {
                System.out.println("bullet shooting");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                while (gamePlaying && player != null) {
                    int i = 1; // i = 1代表AI机器人
                    if (roles[i] != null && roles[i].type == 3) {
                        for (int j = EnemyNr; j < EnemyNr + BulletNr; j++) {
                            if (roles[j] == null) {
                                roles[j] = ((AIRobot) roles[i]).shoot(j);
                                break;
                            }
                        }
                    }
                    gui.printAllEnemies();
                    try {
                        Thread.sleep(bulletShootingSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("bullet done");
            }
        }

        // 控制滑稽的眼泪发射
        class TearShooting implements Runnable {
            public synchronized void run() {
                System.out.println("Player shooting");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                player[0].changeTypeShoot();

                if (player[0] != null) {
                    for (int j = EnemyNr + BulletNr; j < EnemyNr + BulletNr + TearNr; j++) {
                        if (roles[j] == null) {
                            roles[j] = player[0].shoot(j);
                            gui.printAllEnemies();
                            player[0].changeTypeShoot();
                            // 射击一次，暂停一下
                            try {
                                Thread.sleep(PlayerShootingSpeed);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                player[0].changeTypeNormal();
                System.out.println("Tear done");
            }
        }


        // 控制派大星射击痞老板类
        class BigStarShooting implements Runnable {
            public synchronized void run() {
                System.out.println("BigStar shooting");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                while (gamePlaying && player != null) {
                    int i = 0; // i = 0代表派大星
                    if (roles[i] != null && (roles[i].type == 4 || roles[i].type == 8) && ((BigStar) roles[i]).boomArouned()) {
                        ((BigStar) roles[i]).changeType();

                        try {
                            Thread.sleep(BeforeBossShooting);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Role[] pis = ((BigStar) roles[i]).shoot(EnemyNr + BulletNr + TearNr);
                        for (int j = 0; j < PiNr; j++) {
                            roles[EnemyNr + BulletNr + TearNr + j] = pis[j];
                        }

                        gui.printAllEnemies();
                        try {
                            Thread.sleep(BeforeBossShooting);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ((BigStar) roles[i]).changeType();
                    }
                    try {
                        Thread.sleep(BossShootingSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("BigStar done");
            }
        }


        // 碰撞监测
        class countScore implements Runnable {
            @SuppressWarnings("unused")
            public synchronized void run() {
                System.out.println("counting score");
                while (gamePlaying) {
                    for (int i = 0; i < EnemyNr + BulletNr; i++) {
                        if (roles[i] != null && player != null) {
                            if (boom(roles[i], player[0])) {
                                //导弹/炸药/子弹检测，扣除生命值
                                if (roles[i].type == 2 || roles[i].type == 5 ||
                                        roles[i].type == 7) {
                                    player[0].changeTypeCry();
                                    gui.removeRole(roles[i]);
                                    roles[i] = null;
                                    gui.jProBar.addValue(-5);
                                }
                                //被派大星抓到，直接暴毙
                                else if (roles[i].type == 4 || roles[i].type == 9) {
                                    gamePlaying = false;
                                }
                                //药剂检测，恢复生命值
                                else if (roles[i].type == 6) {
                                    player[0].changeTypeNormal();
                                    roles[i] = null;
                                    //增加血量
                                    gui.jProBar.addValue(20);
                                    //增加经验值
                                    gui.jProBar2.addValue(5);
                                }

//                                // 刚碰撞后，会有一秒钟无敌时间
//                                try {
//                                    Thread.sleep(1000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        }
                    }

                    // 滑稽发射的眼泪射中导弹可以使敌人和子弹都消失(除了派大星)
                    for (int i = EnemyNr + BulletNr; i < EnemyNr + BulletNr + TearNr; i++) {
                        for (int j = 1; j < EnemyNr + BulletNr; j++) {
                            if (roles[i] != null && roles[j] != null) {
                                if (boom(roles[i], roles[j])) {
                                    gui.removeRole(roles[i]);
                                    roles[i] = null;
                                    gui.removeRole(roles[j]);
                                    roles[j] = null;
                                }
                            }
                        }
                        // 清除痞老板
                        for (int j = EnemyNr + BulletNr + TearNr; j < EnemyNr + BulletNr + TearNr + PiNr; j++) {
                            if (roles[i] != null && roles[j] != null) {
                                if (boom(roles[i], roles[j])) {
                                    gui.removeRole(roles[i]);
                                    roles[i] = null;
                                    gui.removeRole(roles[j]);
                                    roles[j] = null;
                                }
                            }
                        }
                    }


                    // 痞老板，扣除生命值
                    for (int i = EnemyNr + BulletNr + TearNr; i < EnemyNr + BulletNr + TearNr + PiNr; i++) {
                        if (roles[i] != null && player != null) {
                            if (boom(roles[i], player[0])) {
                                gui.removeRole(roles[i]);
                                roles[i] = null;
                                gui.jProBar.addValue(-10);
                            }
                            //派大星发射的炮弹会摧毁沿途的墙体
                        }
                    }
                }

                for (int i = 0; i < roles.length; i++) {
                    roles[i] = null;
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

        /**
         * 控制游戏时间，改变游戏关卡
         */
        class progressUI implements Runnable {
            public synchronized void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (gui.jProBar.getValue() > 0 && gamePlaying) {
                    gui.jProBar2.addValue(1);
                    score++;
                    speedTimeCal++;
                    // 判断历史分数
                    if (score > historyScore) {
                        historyScore = score;
                    }
                    // 显示分数
                    gui.currentScoreLabel.setText("当前得分：" + Game.score);
                    gui.historyScore.setText("历史最高分：" + Game.historyScore);
                    gui.huajiBloodLabel.setText("技能栏：闪现 D " + (FlashTimes - flashUsedTimes) + "次，加速 S " + (SpeedTimes - speedUsedTimes) + "次，喷射眼泪 W " + (CryTimes - cryUsedTimes) + "次");
                    // 判断加速模式是否结束
                    if (player[0].speedPatten && speedTimeCal > 0) {
                        Game.playerMovingSpeed *= 2;
                        player[0].speedPatten = false;
                        player[0].changeTypeNormal();
                    }else if(player[0].speedPatten && speedTimeCal <= 0){
                        player[0].changeTypeSpeed();
                    }
                    //关卡改变
                    if (gui.jProBar2.getValue() == 100 && GameLevel < 4) {
                        GameLevel++;
                        gui.gameLevelLabel.setText("难度等级：" + Game.GameLevel);
                        gui.jProBar2.addValue(-100);
                        //更新怪物
                        for (int i = 0; i < EnemyNr; i++) {
                            createRoles(i, roles, player);
                        }
                        gui.deleteWalls();
                        gui.createWalls(GameLevel);
                        player[0].setWalls(gui.getWalls());
                        gui.jf.getContentPane().repaint();
                    }

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 死亡，重置游戏参数
                resetGame();
            }
        }

        playerMoving pmc = new playerMoving();
        Thread playerMC = new Thread(pmc);
        enemyMoving em = new enemyMoving();
        Thread eM = new Thread(em);
        bulletShooting bs = new bulletShooting();
        Thread bS = new Thread(bs);
        TearShooting ts = new TearShooting();
        Thread tS = new Thread(ts);
        BigStarShooting bss = new BigStarShooting();
        Thread bsS = new Thread(bss);
        countScore cs = new countScore();
        Thread cS = new Thread(cs);
        progressUI pUI = new progressUI();
        Thread tProgress = new Thread(pUI);

        playerMC.start();
        eM.start();
        bS.start();
        bsS.start();
        cS.start();
        tProgress.start();

        // 按下W键发射眼泪
        gui.jf.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e1) {
            }

            public void keyPressed(KeyEvent e1) {
                if (!tS.isAlive() && e1.getKeyCode() == KeyEvent.VK_W) {
                    cryUsedTimes++;
                    tS.start();
                }
            }

            public void keyReleased(KeyEvent e1) {
            }
        });
        // 通过键盘控制滑稽的两个技能，加速和闪现
        gui.jf.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e1) {
            }

            public void keyPressed(KeyEvent e1) {

                if (e1.getKeyCode() == KeyEvent.VK_S && speedUsedTimes < SpeedTimes) {
                    speedTimeCal = -20;
                    player[0].changeTypeSpeed();
                    Game.playerMovingSpeed = Game.playerMovingSpeed / 2;
                    speedUsedTimes += 1;
                }

                if (e1.getKeyCode() == KeyEvent.VK_D && flashUsedTimes < FlashTimes) {
                    Player.flashx = 50;
                    Player.flashy = 50;
                    flashUsedTimes += 1;
                }
            }

            public void keyReleased(KeyEvent e1) {
                if (e1.getKeyCode() == KeyEvent.VK_D) {
                    Player.flashx = 0;
                    Player.flashy = 0;
                }
            }
        });
        gui.jf.setFocusable(true);
        gui.jf.setVisible(true);
        gui.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 根据编号创造新角色
     */
    public void createRoles(int i, Role[] enemies, Player[] player) {
        if (i == 0 && GameLevel >= 3) { //第四关，Boss派大星出现
            do {
                enemies[i] = BigStar.createBigStar(i, player[0], gui);
            } while (boom(enemies[i], player[0]));
        } else if (i == 1 && GameLevel >= 2) { //第三关，增加AI机器人一个
            do {
                enemies[i] = AIRobot.createNewRobot(i, player[0], gui);
            } while (boom(enemies[i], player[0]));
        } else if (i < EnemyNr / 5 * 1 && GameLevel >= 1) { //第二关，增加地雷
            do {
                enemies[i] = Boom.createNewBoom(i, gui);
            } while (boom(enemies[i], player[0]));
        } else if (i < EnemyNr / 5 * 3 && GameLevel == 0) { //第一关，导弹数量较少
            do {
                enemies[i] = Missile.createNewMissile(i, player[0], gui);
            } while (boom(enemies[i], player[0]));
        } else if (i < EnemyNr / 5 * 4 && GameLevel >= 1) { //第二关，导弹数量增加
            do {
                enemies[i] = Missile.createNewMissile(i, player[0], gui);
            } while (boom(enemies[i], player[0]));
        } else { //为药水
            do {
                enemies[i] = Medicine.createNewMedicine(i, gui);
            } while (boom(enemies[i], player[0]));
        }
    }

    /**
     * 重置游戏参数
     */
    public void resetGame(){
        gamePlaying = false;
        gui.deleteWalls();
        score = 0;
        flashUsedTimes = 0;
        speedUsedTimes = 0;
        cryUsedTimes = 0;
        speedTimeCal = 0;
    }

    /**
     * 游戏结束，退回到主菜单
     */
    public void backMain(GUI gui) {
        gui.jf.getContentPane().setBackground(GUI.initColor);
        gui.jf.setBounds(gui.graphWidth / 2 - 300, gui.graphHeight / 2 - 400, 600, 800);
        gui.backPic.setVisible(false);
        gui.huajiBloodLabel.setVisible(false);
        gui.exit.setBounds(200, 400, 200, 70);
        gui.exit.setVisible(true);
        gui.letHuaJiFly.setText("再试试看吧！");
        gui.letHuaJiFly.setVisible(true);
        gui.letHuaJiFly.setBounds(120, 120, 500, 100);
        gui.jProBar.getjProgressBar().setVisible(false);
        gui.jProBar2.getjProgressBar().setVisible(false);
        gui.clearRole();
        GameLevel = 0;
        gui.jf.getContentPane().repaint();
    }
}
