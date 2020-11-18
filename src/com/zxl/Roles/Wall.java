package com.zxl.Roles;

import com.zxl.GUI.GUI;

import java.util.Random;

public class Wall extends Role {
    public static final int r = 25;
    public static final int id = 10;
    public static final int WallBlockNr = 5; // 一共几面墙
    public static final int WallNr = 100; // 一面墙几块

    public Wall(double X, double Y, GUI gui) {
        super(X, Y, r, id, 13, gui);
    }

    /**
     * 产生墙
     * 随机生成在任何位置
     */
    public static Wall[][] createNewWallRandom(GUI gui) {
        Random random = new Random();
        Wall[][] walls = new Wall[WallBlockNr][WallNr];
        for (int i = 0; i < 2; i++) {
            switch (i) {
                case 0: {
                    int x = random.nextInt(100) + 200;
                    int y = random.nextInt(100) + 200;
                    for (int n = 0; n < WallNr; n++) {
                        walls[i][n] = new Wall(x, y + 0.3 * n * r, gui);
                        walls[i + 1][n] = new Wall(x + 700, y + 0.3 * n * r, gui);
                    }
                    break;
                }
                case 1: {
                    int x = random.nextInt(300) + 400;
                    int y = random.nextInt(300) + 400;
                    for (int n = 0; n < WallNr; n++) {
                        walls[i + 1][n] = new Wall(x + 0.3 * n * r, y, gui);
                    }
                    break;
                }
            }
        }
        return walls;
    }

    /**
     * 产生墙
     * 自制地图 小彩蛋"JAVA"
     */
    public static Wall[][] createNewWall(GUI gui, int level) {
        Wall[][] walls = new Wall[WallBlockNr][WallNr];
        switch (level) {
            // J
            case 0: {
                for (int n = 0; n < 25; n++)
                    walls[level][n] = new Wall(1100, 200 + n * r, gui);
                for (int n = 25; n < 40; n++)
                    walls[level][n] = new Wall(1100 - 0.5 * (n - 25) * r, 800, gui);
                for (int n = 40; n < 50; n++)
                    walls[level][n] = new Wall(900, 1050 - (n - 30) * r, gui);
                break;
            }
            // A
            case 1: {
                for (int n = 0; n < 40; n++)
                    walls[level][n] = new Wall(1100 - 0.3 * n * r, 200 + 0.6 * n * r, gui);
                for (int n = 40; n < 80; n++)
                    walls[level][n] = new Wall(1100 + 0.3 * (n - 40) * r, 200 + 0.6 * (n - 40) * r, gui);
                for (int n = 80; n < 93; n++)
                    walls[level][n] = new Wall(950 + (n - 80) * r, 500, gui);
                break;
            }
            // V
            case 2: {
                for (int n = 0; n < 40; n++)
                    walls[level][n] = new Wall(1100 - 0.3 * n * r, 800 - 0.6 * n * r, gui);
                for (int n = 40; n < 80; n++)
                    walls[level][n] = new Wall(1100 + 0.3 * (n - 40) * r, 800 - 0.6 * (n - 40) * r, gui);
                break;
            }
            // A
            case 3: {
                for (int n = 0; n < 40; n++)
                    walls[level][n] = new Wall(1100 - 0.3 * n * r, 200 + 0.6 * n * r, gui);
                for (int n = 40; n < 80; n++)
                    walls[level][n] = new Wall(1100 + 0.3 * (n - 40) * r, 200 + 0.6 * (n - 40) * r, gui);
                for (int n = 80; n < 93; n++)
                    walls[level][n] = new Wall(950 + (n - 80) * r, 500, gui);
                break;
            }
            case 4: {
                // 生成J
                level = 0;
                for (int n = 0; n < 25; n++)
                    walls[level][n] = new Wall(300, 200 + n * r, gui);
                for (int n = 25; n < 40; n++)
                    walls[level][n] = new Wall(300 - 0.5 * (n - 25) * r, 800, gui);
                for (int n = 40; n < 50; n++)
                    walls[level][n] = new Wall(100, 1050 - (n - 30) * r, gui);
                level = 1;
                for (int n = 0; n < 40; n++)
                    walls[level][n] = new Wall(700 - 0.2 * n * r, 200 + 0.6 * n * r, gui);
                for (int n = 40; n < 80; n++)
                    walls[level][n] = new Wall(700 + 0.2 * (n - 40) * r, 200 + 0.6 * (n - 40) * r, gui);
                for (int n = 80; n < 89; n++)
                    walls[level][n] = new Wall(600 + (n - 80) * r, 500, gui);
                level = 2;
                for (int n = 0; n < 40; n++)
                    walls[level][n] = new Wall(1170 - 0.2 * n * r, 800 - 0.6 * n * r, gui);
                for (int n = 40; n < 80; n++)
                    walls[level][n] = new Wall(1170 + 0.2 * (n - 40) * r, 800 - 0.6 * (n - 40) * r, gui);
                level = 3;
                for (int n = 0; n < 40; n++)
                    walls[level][n] = new Wall(1600 - 0.2 * n * r, 200 + 0.6 * n * r, gui);
                for (int n = 40; n < 80; n++)
                    walls[level][n] = new Wall(1600 + 0.2 * (n - 40) * r, 200 + 0.6 * (n - 40) * r, gui);
                for (int n = 80; n < 89; n++)
                    walls[level][n] = new Wall(1500 + (n - 80) * r, 500, gui);
            }
        }
        return walls;
    }
}

