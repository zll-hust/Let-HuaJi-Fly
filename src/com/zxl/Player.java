package com.zxl;

import com.zxl.GUI.*;

/**
 * Description:
 * 玩家类，即滑稽
 *
 * @encode UTF-8
 */
public class Player extends Role 
{
    private int Max;
    public static final int r = 90;

    public Player(int X, int Y, int id, GUI gui, int Maxsize) {
        super(X, Y, r, id, 1, gui);
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
        x = gui.mouseX ;
        y = gui.mouseY ;
        draw();
    }
    
    	

    public int getMax() {
        return Max;
    }

}