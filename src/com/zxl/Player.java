package com.zxl;


/**
 * Description:
 * 玩家类，即滑稽
 *
 * @encode UTF-8
 */
public class Player extends Role {
    private int Max;
    private static int speed = 15;  //控制滑稽的速度
    public static final int r = 40;
    public static double flashx = 0;
    public static double flashy = 0;

    public Player(int X, int Y, int id, GUI gui, int Maxsize) {
        super(X, Y, r, id, 1, gui);
        Max = Maxsize;
        calMoveDirection();
        draw();
    }

    public void move() {
        calMoveDirection();
        x = x + dx + flashx * Math.cos(Math.toRadians(angle));
        y = y + dy + flashy * Math.sin(Math.toRadians(angle));
        if (x + radius > gui.graphWidth || x + radius < 0) {
            calMoveDirection();
        }
        if (y + radius > gui.graphHeight || y + radius < 0) {
            calMoveDirection();
        }
        draw();
    }

    protected void calMoveDirection() {
        int beforeAngle = angle;
        angle = ((int) Math.toDegrees(Math.atan2(gui.mouseY - y, gui.mouseX - x)) + 360) % 360;
        dx = speed * Math.cos(Math.toRadians(angle));
        dy = speed * Math.sin(Math.toRadians(angle));
        if (type == 0) {
            angle = beforeAngle + 20;
        }
    }

    public void resize(int plusSize) {
        if (radius < Max) {
            radius += plusSize;
            draw();
        }
    }

    public int getMax() {
        return Max;
    }

    /**
     * 滑稽超级变换形态
     */
    public void changeType() {
        if (this.type == 1)
            this.type = 0;
        else
            this.type = 1;
    }

    /**
     * 滑稽的喷水技能
     */
    public Role shoot(int id) {
        return Tear.createNewTear(this.getX() + this.getR(), this.getY() + this.getR(), id, gui, angle);
    }

}

