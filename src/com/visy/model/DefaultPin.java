package com.visy.model;

import java.awt.Color;
import java.awt.Graphics;

import com.visy.controller.Controller;

//大头针
public class DefaultPin extends MyPin {


    private int degree = 0;

    public DefaultPin() {
        super();
    }

    public DefaultPin(int degree, int centerX, int centerY, int R) {
        this.degree = degree;
        this.centerX = centerX;
        this.centerY = centerY;
        this.R = R;

    }

    public void draw(Graphics g) {
        x = (int) (centerX + R + length * Math.cos(PI * degree / 180));
        y = (int) (centerY + R + (-length * Math.sin(PI * degree / 180)));

        int line_start_x = (int) (centerX + R + R * Math.cos(PI * degree / 180));
        int line_start_y = (int) (centerY + R + (-R * Math.sin(PI * degree / 180)));

        g.setColor(color);
        g.fillOval(x - r, y - r, 2 * r, 2 * r);
        g.drawLine(line_start_x, line_start_y, x, y);

        g.setColor(Color.WHITE);
        if (value != -1) {
            g.drawString("" + value, x - 5, y + 5);
        }
        g.setColor(Color.BLACK);

        spin();
    }

    //旋转方法
    public void spin() {
        boolean direct = Controller.getInstance().getDirect();

        if (direct) {
            degree--;
        } else {
            degree++;
        }

        if (degree >= 360) {
            degree = 1;
        }
        if (degree <= 0) {
            degree = 359;
        }
    }
}
