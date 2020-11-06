package com.zxl;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * Description:
 * 没仔细看，好像是背景颜色什么的
 *
 * @encode UTF-8
 */
public class ProgressUI {

    private JProgressBar jProgressBar;
    private int progressvalue;

    ProgressUI() {
        this.jProgressBar = new JProgressBar();
    }

    ProgressUI(JProgressBar jProgressBar) {
        this.jProgressBar = jProgressBar;
    }

    public void addValue(int num) {
        progressvalue = this.jProgressBar.getValue() + num;
        this.jProgressBar.setValue(progressvalue);
        if (progressvalue < 20) {
            this.jProgressBar.setForeground(Color.RED);
        } else if (progressvalue < 40) {
            this.jProgressBar.setForeground(Color.YELLOW);
        } else if (progressvalue < 60) {
            this.jProgressBar.setForeground(Color.BLUE);
        } else if (progressvalue < 80) {
            this.jProgressBar.setForeground(Color.GREEN);
        } else {
            this.jProgressBar.setForeground(Color.CYAN);
        }
        this.jProgressBar.setValue(progressvalue);
    }

    public int getValue() {
        return jProgressBar.getValue();
    }

    public JProgressBar getjProgressBar() {
        return jProgressBar;
    }

    public void setjProgressBar(JProgressBar jProgressBar) {
        this.jProgressBar = jProgressBar;
    }
}