package com.zxl.Roles;

import com.zxl.GUI.GUI;

/**
 * Description:
 * 眼泪类，作为滑稽发射的子弹
 *
 * @encode UTF-8
 */
public class Tear extends Role {
    private static int speed = 20; //控制速度，越大越快
    private static final int r = 20;

    public Tear(double X, double Y, int id, GUI gui, int angle) {
        super(X, Y, r, id, 10, gui);
        this.angle = angle;
        calMoveDirection();
    }

    protected void calMoveDirection() {
        dx = speed * Math.cos(Math.toRadians(angle));
        dy = speed * Math.sin(Math.toRadians(angle));
    }

    /**
     * 产生眼泪
     */
    public static Role createNewTear(double X, double Y, int id, GUI gui, int angle) {
        return new Tear(X, Y, id, gui, angle);
    }

}
