package com.visy.view;

import java.applet.AudioClip;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.visy.controller.Controller;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    public static final int WIN_WIDTH = 480;
    public static final int WIN_HEIGHT = 700;

    public MainWindow() {
        initUI();
    }

    public void initUI() {

        JPanel drawPanel = new DrawPanel();

        this.add(drawPanel);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (Controller.state == 0) {
                        repaint();
                    }
                }
            }
        }).start();

        URL url = MainWindow.class.getClassLoader().getResource("com/visy/sounds/login.wav");
        AudioClip clip = java.applet.Applet.newAudioClip(url);
        clip.play();

        this.setTitle("见缝插针");
        this.setSize(WIN_WIDTH, WIN_HEIGHT);
        this.setResizable(false);// 窗口大小不可变
        this.setLocationRelativeTo(null);// 让窗口居中
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addKeyListener(new KeyAdapter() {
            @Override//键盘按下事件
            public void keyPressed(KeyEvent e) {
                Controller.getInstance().handleKeyPressed(e.getKeyCode());
            }

            @Override//键盘释放事件
            public void keyReleased(KeyEvent e) {
                Controller.getInstance().handleKeyReleased(e.getKeyCode());
            }
        });
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
