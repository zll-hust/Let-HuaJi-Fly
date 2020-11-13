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

    public Player(int X, int Y, int id, GUI gui, int Maxsize) {
        super(X, Y, r, id, 1, gui);
        Max = Maxsize;
        calMoveDirection();
        draw();
    }

    public void move() {
        calMoveDirection();
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
        angle = ((int) Math.toDegrees(Math.atan2(gui.mouseY - y, gui.mouseX - x)) + 360) % 360;
        dx = speed * Math.cos(Math.toRadians(angle));
        dy = speed * Math.sin(Math.toRadians(angle));
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
     * 滑稽的技能
     */
    public Role[] shoot(int id) {
        Role[] tears = new Role[16];
        // 向周围一圈发射
        for(int i = 0; i < 25; i++){
            tears[i] = Tear.createNewTear(this.getX() + this.getR(), this.getY() + this.getR(), id + i, gui, (int)(i * 22.5));
        }
        return tears;
    }
}

