package com.zxl.Roles;


import com.zxl.GUI.GUI;

/**
 * Description:
 * 玩家类，即滑稽
 *
 * @encode UTF-8
 */
public class Player extends Role {
    private static int speed = 15;  //控制滑稽的速度
    public boolean speedPatten = false;
    private static final int r = 50;
    public static double flashx = 0;
    public static double flashy = 0;
    public Wall[][] walls;

    public Player(int X, int Y, int id, GUI gui, Wall[][] walls) {
        super(X, Y, r, id, 1, gui);
        this.walls = walls;
        checkCrashMoveDirection();
        draw();
    }

    public void move() {
        checkCrashMoveDirection();
        testWall();
        x = x + dx + flashx * Math.cos(Math.toRadians(angle));
        y = y + dy + flashy * Math.sin(Math.toRadians(angle));
        if (x + radius > gui.graphWidth - 50 || x + radius < 50) {
            checkCrashMoveDirection();
        }
        if (y + radius > gui.graphHeight - 50 || y + radius < 150) {
            checkCrashMoveDirection();
        }
        draw();
    }

    protected void checkCrashMoveDirection() {
        int beforeAngle = angle;
        angle = ((int) Math.toDegrees(Math.atan2(gui.mouseY - y, gui.mouseX - x)) + 360) % 360;
        dx = speed * Math.cos(Math.toRadians(angle));
        dy = speed * Math.sin(Math.toRadians(angle));
        if (type == 0) {
            angle = beforeAngle + 20;
        }
    }


    /**
     * 滑稽变换喷水形态
     */
    public void changeTypeShoot() {
        this.type = 0;
    }

    /**
     * 滑稽变换加速形态
     */
    public void changeTypeSpeed() {
        this.type = 12;
        this.speedPatten = true;
    }

    /**
     * 滑稽变换哭泣形态
     */
    public void changeTypeCry() {
        this.type = 11;
    }

    /**
     * 滑稽变换正常形态
     */
    public void changeTypeNormal() {
        this.type = 1;
    }

    /**
     * 滑稽的喷水技能
     */
    public Role shoot(int id) {
        return Tear.createNewTear(this.getX() + this.getR(), this.getY() + this.getR(), id, gui, angle);
    }

    /**
     * 碰撞后设置速度
     *
     * @param speed
     */
    public void setDx(double speed) {
        this.dx = speed;
    }

    public void setDy(double speed) {
        this.dy = speed;
    }

    /**
     * 检测和墙是否碰撞
     */
    public boolean checkCrash(double x1, double y1, double x2, double y2, double r1,
                              double r2) {
        return r1 + r2 >= Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * 判断是否碰撞，若碰撞返回true
     */
    public boolean boom(Role a, Role b) {
        return a.getR() + b.getR() >=
                Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    /**
     * 检查和墙距离
     */
    public void testWall() {
        if(walls != null)
            for (Wall[] wallBlock : walls)
                if (wallBlock != null)
                    for (Wall wall : wallBlock)
                        if(wall != null)
                            if (boom(wall, this)) {
                                if (checkCrash(wall.getX(), wall.getY(), this.getX() + this.dx * 2, this.getY(), wall.getR(), this.getR())) {
                                    this.setDx(0);
                                }

                                if (checkCrash(wall.getX(), wall.getY(), this.getX(), this.getY() + this.dy * 2, wall.getR(), this.getR())) {
                                    this.setDy(0);
                                }
                            }
    }

    public void setWalls(Wall[][] walls){
        this.walls = walls;
    }
}

