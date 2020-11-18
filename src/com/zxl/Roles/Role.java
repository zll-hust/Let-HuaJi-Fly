package com.zxl.Roles;


import com.zxl.GUI.GUI;

import java.util.Random;

/**
 * Description:
 * 角色类，作为玩家类、怪物类的父类
 *
 * @encode UTF-8
 */
public class Role {
    private int id;
    protected GUI gui;
    protected double x, y, dx, dy; // 角色坐标、移动距离
    protected int radius; // 角色的半径（尽量把角色处理为圆形，分配碰撞检测），角色的坐标，前进的距离
    public int type; // 角色的类型，不写枚举类了麻烦
    // 0代表滑稽的哭泣形态，1代表滑稽的正常形态，2代表导弹，3代表机器人，4代表派大星，
    // 5代表炸药，6代表药水，7代表子弹，8代表派大星2，9代表痞老板，
    // 10代表眼泪，11代表滑稽的流泪形态，12代表滑稽的加速形态，13代表墙体
    public static String[] imgPath = {"./res/shoot.png", "./res/normal.png", "./res/rocket.png",
            "./res/AIRobot.png", "./res/BigStar1.png", "./res/boom.png",
            "./res/life.png", "./res/bullet.png", "./res/BigStar2.png",
            "./res/pi.png", "./res/tear.png", "./res/cry.png",
            "./res/speed.png", "./res/wall.png"};
    public int angle = 0; // 图片转向的角度(角度制)

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

    public Role() {
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
        if (y + radius > gui.graphHeight || y + radius < 150) {
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

    public int getID() {
        return id;
    }
}