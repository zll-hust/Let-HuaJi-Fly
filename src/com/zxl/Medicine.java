package com.zxl;

import static com.zxl.Game.random;

/**
 * Description:
 * 药水类，吃了恢复生命值，不动
 *
 * @encode UTF-8
 */
public class Medicine extends Role{
    private static final int r = 16;

    public Medicine(double X, double Y, int id, GUI gui) {
        super(X, Y, r, id, 6, gui);
    }

    /*
     * 产生药水
     * 随机生成在任何位置
     */
    public static Role createNewMedicine(int id,  GUI gui) {
        return new Medicine(random.nextInt(gui.graphWidth), random.nextInt(gui.graphHeight), id, gui);
    }
}
