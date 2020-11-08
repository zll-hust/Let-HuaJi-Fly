package com.zxl;


import java.util.Random;

/**
 * Description:
 * 角色类，作为玩家类、怪物类的父类
 *
 * @encode UTF-8
 */
public class Role {
    protected double x, y, dx, dy;
    protected int radius;
    // 角色的半径（尽量把角色处理为圆形，分配碰撞检测），角色的坐标，前进的距离
    private int id;
    protected GUI gui;
    public int type; // 角色的类型，不写枚举类了麻烦
    public String color; //小球的颜色，以后要删除这个，暂时保留调试代码
    // 1代表滑稽，2代表导弹，3代表机器人，4代表派大星，5代表炸药，6代表药水，7代表子弹
    public static String[] imgPath = {" ", "./res/normal.png", "./res/rocket.png",
            "./res/AIRobot.png", "./res/bigStar.png", "./res/boom.png"
            , "./res/life.png", "./res/bullet.png"};
    public int angle = 0; //图片转向的角度(不是弧度)

    public Role(double X, double Y, int R, int id, int type, GUI gui, double dx, double dy) {
        x = X;
        y = Y;
        radius = R;
        Random random = new Random();
        this.dx = random.nextBoolean() ? dx : -dx;
        this.dy = random.nextBoolean() ? dy : -dy;
        this.gui = gui;
        this.type = type;
        this.id = id;
        draw();
    }

    public Role(double X, double Y, int R, int id, int type, GUI gui) {
        x = X;
        y = Y;
        radius = R;
        this.type = type;
        this.gui = gui;
        this.id = id;
    }

    public Role(){
    }

    public void draw() {
        gui.updateRole(this);
    }

    public void move() {
        x += dx;
        y += dy;
        if (x + radius > gui.graphWidth || x + radius < 0) {
            calMoveDirection();
        }
        if (y + radius > gui.graphHeight|| y + radius < 0) {
            calMoveDirection();
        }
        draw();
    }

    protected void calMoveDirection() {
        dx = -dx;
        dy = -dy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return radius;
    }

    public int getD() {
        return radius << 1;
    }

    public int getID() {
        return id;
    }

}