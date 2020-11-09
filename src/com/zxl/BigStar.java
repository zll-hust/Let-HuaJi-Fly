package com.zxl;


/**
 * Description:
 * BOSS 派大星
 * 技能：发射循环痞老板
 *
 * @encode UTF-8
 */
public class BigStar extends Role {
    private Player p; //玩家
    private static int speed = 10; //控制速度，越大越快
    private static final int r = 32;

    public BigStar(double X, double Y, int id, GUI gui, Player p) {
        super(X, Y, r, id, 4, gui);
        this.p = p;
        this.angle = 0; // 派大星不转动
        calMoveDirection();
    }

    protected void calMoveDirection() {
        dx = speed * (p.getX() - x) / Math.sqrt(Math.pow(p.getY() - y, 2) + Math.pow(p.getX() - x, 2));
        dy = speed * (p.getY() - y) / (Math.sqrt(Math.pow(p.getY() - y, 2) + Math.pow(p.getX() - x, 2)));
    }

    /*
     * 派大星运动方式：向滑稽直线移动。
     */
    public void move() {
        calMoveDirection();
        x += dx;
        y += dy;
        if (x + radius > gui.graphWidth) {
            calMoveDirection();
        }
        if (y + radius > gui.graphHeight) {
            calMoveDirection();
        }
        draw();
    }


    /*
     * 发射子弹
     */
    public Role shoot(int id) {
        //TODO
        return BossPi.createNewBullet(this.getX() + this.getR(), this.getY() + this.getR(), id, gui, angle);
    }

    /*
     * 产生怪物3：派大星
     * 随机生成在围墙上
     */
    public static Role createBigStar(int id, Player p, GUI gui) {
        return new BigStar(100, 100, id, gui, p);
    }
}