package com.zxl.Roles;

import com.zxl.GUI.GUI;

/**
 * Description:
 * 痞老板
 * 作为派大星发出的子弹
 *
 * @encode UTF-8
 */
public class BossPi extends Role {
    private static int speed = 15; //控制速度，越大越快
    private static final int r = 10;

    public BossPi(double X, double Y, int id, GUI gui, int angle) {
        super(X, Y, r, id, 9, gui);
        this.angle = angle;
        calMoveDirection();
    }

    protected void calMoveDirection() {
        dx = speed * Math.cos(Math.toRadians(angle));
        dy = speed * Math.sin(Math.toRadians(angle));
    }

    public static Role createNewBossPi(double X, double Y, int id, GUI gui, int angle) {
        return new BossPi(X, Y, id, gui, angle);
    }
}