package com.zxl;

import static com.zxl.Game.random;

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
     * 导弹行动算法：根据创建角色时滑稽的位置定位导弹发出的方向，导弹由墙体出发，射向滑稽
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