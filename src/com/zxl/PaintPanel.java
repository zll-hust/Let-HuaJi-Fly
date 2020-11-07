package com.zxl;

//import sun.awt.image.BufferedImageGraphicsConfig;

import com.zxl.MyUtils;
import com.zxl.Role;

import java.awt.*;
import java.awt.image.BufferedImage;

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
            if (nowPainting != null) {
//                    System.out.println(nowPainting.color + " " + nowPainting.getID());
                if (nowPainting.type == 1) {
                    g.setColor(Color.decode(nowPainting.color));
                    g.fillOval((int)nowPainting.getX() - (int)nowPainting.getR(), (int)nowPainting.getY() - (int)nowPainting.getR(),
                            nowPainting.getD(), nowPainting.getD());
                }else{
                    Image image = Toolkit.getDefaultToolkit().getImage(Role.imgPath[nowPainting.type]);
                    BufferedImage img = MyUtils.Rotate(image, nowPainting.angle);
                    g.drawImage(img, (int)nowPainting.getX() - (int)nowPainting.getR(), (int)nowPainting.getY() - (int)nowPainting.getR(), this);
                }

            }
        }
    }
}