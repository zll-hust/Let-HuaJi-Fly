package com.zxl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 * Description:
 * 也不知道是啥，好像是把角色画到背景上的一个类
 *
 * @encode UTF-8
 */
public class PaintPanel extends JPanel {
    String name;

    private Role[] roles;

    public PaintPanel(Role[] roles, String name) {
        this.roles = roles;
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        for (Role nowPainting : roles) {
            int maxBoomsNr = 0;
            int maxLifesNr = 0;
            if (nowPainting != null) {
//                    System.out.println(nowPainting.color + " " + nowPainting.getID());
                if (nowPainting.color.equals("#EED5D2") && (maxBoomsNr <= 3)) {
                    Image image = Toolkit.getDefaultToolkit().getImage("./res/boom.png");
                    nowPainting.radius = 16;
                    g.drawImage(image, nowPainting.getX(), nowPainting.getY(), this);
                    maxBoomsNr++;
                    continue;
                } else if (nowPainting.color.equals("#6A5ACD") && (maxLifesNr <= 3)) {
                    Image image = Toolkit.getDefaultToolkit().getImage("./res/life.png");
                    nowPainting.radius = 16;
                    g.drawImage(image, nowPainting.getX(), nowPainting.getY(), this);
                    maxLifesNr++;
                    continue;
                }
                g.setColor(Color.decode(nowPainting.color));
                g.fillOval(nowPainting.getX() - nowPainting.getR(), nowPainting.getY() - nowPainting.getR(),
                        nowPainting.getD(), nowPainting.getD());
            }
        }
    }
}