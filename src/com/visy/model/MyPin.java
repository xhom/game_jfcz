package com.visy.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.visy.controller.Controller;

public class MyPin {
    protected int x;
    protected int y;
    protected int r = 13;
    protected int length = 150;//针的长度
    //转盘圆心半径
    protected int R;
    protected int value = -1;
    //转盘中心坐标
    protected int centerX;
    protected int centerY;
    protected final double PI = Math.PI;
    private boolean flag = true;
    private boolean alive = true;
    protected Color color;

    public MyPin() {

    }

    public MyPin(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public void draw(Graphics g) {

        g.setColor(color);
        g.fillOval(x - r, y - r, 2 * r, 2 * r);
        g.setColor(Color.WHITE);
        g.drawString("" + value, x - 5, y + 5);
        g.setColor(Color.BLACK);
    }

    //發射方法
    public void shoot() {
        if (flag) {
            if ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) >= (length + 35) * (length + 35)) {
                y -= 5;
            } else {
                DefaultPin defaultPin = new DefaultPin(270, centerX, centerY, 30);
                defaultPin.setValue(this.value);
                Controller.getInstance().addPinToList(defaultPin);
                this.alive = false;
                flag = false;
                Controller.getInstance().getSoundController().setPlayShootedSound(true);
            }
        }

    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x - r, y - r, 2 * r, 2 * r);
    }
}
