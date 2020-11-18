package com.zxl.GUI;

import com.zxl.Roles.Role;
import com.zxl.Roles.Wall;
import com.zxl.Utils.MyUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Description:
 * 把角色画到背景上的类
 *
 * @encode UTF-8
 */
public class PaintPanel extends JPanel {
    private Role[] roles;
    public Wall[][] walls;
    private GUI gui;
//    public boolean flag = false;

    public PaintPanel(Role[] roles, Wall[][] walls, GUI gui) {
        this.roles = roles;
        this.gui = gui;
        this.walls = walls;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 画出墙体
        if (walls != null)
            for (Wall[] wallBlocks : walls) {
                if (wallBlocks != null) {
                    for (Wall wall : wallBlocks) {
                        if (wall != null) {
                            Image image = Toolkit.getDefaultToolkit().getImage(Role.imgPath[wall.type]);
                            BufferedImage img = MyUtils.Rotate(image, 0);
                            g.drawImage(img, (int) wall.getX() - (int) wall.getR(), (int) wall.getY() - (int) wall.getR(), this);
                        }
                    }
                }
            }
        // 画出角色
        for (Role nowPainting : roles) {
            if (nowPainting != null) {
                Image image = Toolkit.getDefaultToolkit().getImage(Role.imgPath[nowPainting.type]);
                BufferedImage img = MyUtils.Rotate(image, nowPainting.angle);
                g.drawImage(img, (int) nowPainting.getX() - (int) nowPainting.getR(), (int) nowPainting.getY() - (int) nowPainting.getR(), this);
            }
        }
    }
}
