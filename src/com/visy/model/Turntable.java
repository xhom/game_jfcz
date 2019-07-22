package com.visy.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.visy.view.MainWindow;

//轉盤
public class Turntable {
    private int value;
    private int R = 30;
    private int x = MainWindow.WIN_WIDTH / 2 - R;
    private int y = MainWindow.WIN_HEIGHT / 2 - R - 150;

    //畫中心圓點
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, R * 2, R * 2);

        g.setColor(Color.WHITE);
        g.setFont(new Font("宋体", Font.BOLD, 25));
        g.drawString("" + value, x + R - 13, y + R + 10);
        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体", Font.BOLD, 13));
    }

    public int getR() {
        return R;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setValue(int level) {
        this.value = level;
    }

}
