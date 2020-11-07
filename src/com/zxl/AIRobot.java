package com.zxl;

import static com.zxl.Game.random;

/**
 * Description:
 * AI机器人，会根据地形跟踪滑稽，发射炮弹
 *
 * @encode UTF-8
 */
public class AIRobot extends Role {
    private Player p; //玩家
    private static int speed = 15; //控制速度，越大越快
    private static final int r = 32;

    public AIRobot(double X, double Y, int id, GUI gui, Player p) {
        super(X, Y, r, id, 3, gui);
        this.p = p;
        calMoveDirection();
    }

    /*
     * AI机器人
     */
    protected void calMoveDirection() {

    }

    /*
     * 产生怪物2：AI机器人
     * 随机生成在围墙上
     */
    public static Role createNewRobot(int id, Player p, GUI gui) {
        if (random.nextDouble() < 0.25) {
            return new AIRobot(random.nextInt(gui.graphWidth), 0, id, gui, p);
        } else if (random.nextDouble() > 0.25 && random.nextDouble() < 0.5) {
            return new AIRobot(gui.graphWidth, random.nextInt(gui.graphHeight), id, gui, p);
        } else if (random.nextDouble() > 0.5 && random.nextDouble() < 0.75) {
            return new AIRobot(random.nextInt(gui.graphWidth), gui.graphHeight, id, gui, p);
        } else {
            return new AIRobot(0, random.nextInt(gui.graphHeight), id, gui, p);
        }
    }
}