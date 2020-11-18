package com.zxl.Roles;

import com.zxl.GUI.GUI;

import static com.zxl.Game.Game.random;

/**
 * Description:
 * 导弹类，这是一种喜欢横冲直撞的生物
 *
 * @encode UTF-8
 */
public class Missile extends Role {
    private Player p; //玩家
    private static int speed = 15; //控制速度，越大越快
    private static final int r = 40;

    public Missile(double X, double Y, int id, GUI gui, Player p) {
        super(X, Y, r, id, 2, gui);
        this.p = p;
        calMoveDirection();
    }

    /**
     * 导弹行动算法：根据创建角色时滑稽的位置定位导弹发出的方向，导弹由墙体出发，射向滑稽
     */
    protected void calMoveDirection() {
        angle = ((int) Math.toDegrees(Math.atan2(p.getY() - y, p.getX() - x) + Math.PI * 0.25) + 360) % 360;
        dx = speed * (p.getX() - x) / Math.sqrt(Math.pow(p.getY() - y, 2) + Math.pow(p.getX() - x, 2));
        dy = speed * (p.getY() - y) / (Math.sqrt(Math.pow(p.getY() - y, 2) + Math.pow(p.getX() - x, 2)));
    }

    /**
     * 产生怪物1：导弹
     * 随机生成在围墙上
     */
    public static Role createNewMissile(int id, Player p, GUI gui) {
        if (random.nextDouble() < 0.25) {
            return new Missile(random.nextInt(gui.graphWidth), GUI.LabelHeight * 3, id, gui, p);
        } else if (random.nextDouble() > 0.25 && random.nextDouble() < 0.5) {
            return new Missile(gui.graphWidth, random.nextInt(gui.graphHeight - GUI.LabelHeight * 3) + GUI.LabelHeight * 3, id, gui, p);
        } else if (random.nextDouble() > 0.5 && random.nextDouble() < 0.75) {
            return new Missile(random.nextInt(gui.graphWidth), gui.graphHeight, id, gui, p);
        } else {
            return new Missile(0, random.nextInt(gui.graphHeight - GUI.LabelHeight * 3) + GUI.LabelHeight * 3, id, gui, p);
        }
    }
}
