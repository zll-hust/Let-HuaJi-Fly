package com.zxl;

public class Bullet extends Role {
    private Player p; //玩家
    private static int speed = 10; //控制速度，越大越快
    private static final int r = 10;
    protected int area = 400;

    public Bullet(double X, double Y, int id, GUI gui, Player p, int angle) {
        super(X, Y, r, id, 7, gui);
        this.p = p;
        this.angle = angle;
        calMoveDirection();
    }

    protected void calMoveDirection() {
        dx = speed * Math.cos(Math.toRadians(angle));
        dy = speed * Math.sin(Math.toRadians(angle));
    }

    public boolean moveForecast() {
        double fx = x + dx;
        double fy = y + dy;
        if (fx + radius > gui.graphWidth) {
            return false;
        }
        if (fy + radius > gui.graphHeight) {
            return false;
        }
        if (boomArouned()){
            return false;
        }
        return true;
    }

    private boolean boomArouned() {
        return this.getR() + p.getR() + area >=
                Math.sqrt(Math.pow(this.getX() - p.getX(), 2) + Math.pow(this.getY() - p.getY(), 2));
    }

    /*
     * 产生怪物1：导弹
     * 随机生成在围墙上
     */
    public static Role createNewBullet(double X, double Y, int id, GUI gui, Player p, int angle) {
        return new Bullet(X, Y, id, gui, p, angle);
    }
}