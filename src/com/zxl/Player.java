package com.zxl;

/**
 * Description:
 * 玩家类，即滑稽
 *
 * @encode UTF-8
 */
public class Player extends Role {
    private int Max;

    public Player(int X, int Y, int R, int id, GUI gui, int Maxsize) {
        super(X, Y, R, id, gui);
        Max = Maxsize;
        draw();
    }

    public void resize(int plusSize) {
        if (radius < Max) {
            radius += plusSize;
            draw();
        }
    }

    public void move() {
        x = gui.mouseX;
        y = gui.mouseY;
        draw();
    }

    public int getMax() {
        return Max;
    }

}