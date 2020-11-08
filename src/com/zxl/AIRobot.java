package com.zxl;

import java.util.ArrayList;

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
        angle = ((int) Math.toDegrees(Math.atan2(p.getY() - y, p.getX() - x)) + 360) % 360;
        dx = speed * Math.cos(Math.toRadians(angle));
        dy = speed * Math.sin(Math.toRadians(angle));
    }

    /*
     * AI机器人运动方式：停在滑稽周围一圈
     */
    public void move() {
        calMoveDirection();
        if (boomArouned()) {
            dx = 0;
            dy = 0;
        } else {
            x += dx;
            y += dy;
            if (x + radius > gui.graphWidth) {
                calMoveDirection();
            }
            if (y + radius > gui.graphHeight) {
                calMoveDirection();
            }
        }
        draw();
    }

    private boolean boomArouned() {
        return this.getR() + p.getR() + 150 >=
                Math.sqrt(Math.pow(this.getX() - p.getX(), 2) + Math.pow(this.getY() - p.getY(), 2));
    }

    /*
     * 发射子弹
     */
    public Role shoot(int id){
        return Bullet.createNewBullet(this.getX() + this.getR(), this.getY() + this.getR(), id, gui, p, angle);
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