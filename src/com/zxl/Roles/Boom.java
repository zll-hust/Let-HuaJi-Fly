package com.zxl.Roles;

import com.zxl.GUI.GUI;

import static com.zxl.Game.Game.random;

/**
 * Description:
 * 地雷类，减生命值，不动
 *
 * @encode UTF-8
 */
public class Boom extends Role{
    private static final int r = 25;

    public Boom(double X, double Y, int id, GUI gui) {
        super(X, Y, r, id, 5, gui);
    }

    /*
     * 产生地雷
     * 随机生成在任何位置
     */
    public static Role createNewBoom(int id,  GUI gui) {
        return new Boom(random.nextInt(gui.graphWidth), random.nextInt(gui.graphHeight - GUI.LabelHeight * 2) + GUI.LabelHeight * 2, id, gui);
    }
}