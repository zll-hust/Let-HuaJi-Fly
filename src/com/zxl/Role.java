package com.zxl;


import java.util.Random;

/**
 * Description:
 * 角色类，作为玩家类、怪物类的父类
 *
 * @encode UTF-8
 */
public class Role {
    protected int radius, x, y, dx, dy;
    private int id;
    protected GUI gui;
    private int type;
    public static String[] imgPath = {"./res/normal.png", "./res/rocket.png",
            "./res/missile.png", "./res/bigStar.png", "./res/boom.png", "./res/life.png"};

    public Role(int X, int Y, int R, int id, int type, GUI gui, int dx, int dy) {
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

    public Role(int X, int Y, int R, int id, GUI gui) {
        x = X;
        y = Y;
        radius = R;
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
        if (x + radius > gui.graphWidth || x - radius < 0) {
            dx = -dx;
        }
        if (y + radius > gui.graphHeight || y - radius < GUI.PROGRESSWIDTH) {
            dy = -dy;
        }
        draw();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getR() {
        return radius;
    }

    public int getD() {
        return radius << 1;
    }

    public int getID() {
        return id;
    }

}