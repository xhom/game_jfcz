package com.visy.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.visy.model.DefaultPin;
import com.visy.model.MyPin;
import com.visy.model.Turntable;
import com.visy.view.MainWindow;

public class Controller {
    private static Controller controller = null;
    private int level = 10;
    private Turntable turntable = new Turntable();
    private boolean direct = true;//true顺时针false逆时针
    private List<DefaultPin> pinList = new ArrayList<DefaultPin>();//转盘上插的大头针（包括默认的）
    private List<MyPin> shootPinList = new ArrayList<MyPin>();//发射状态中的大头针
    private Stack<MyPin> pinStack = new Stack<MyPin>();//待發射的大頭針
    private boolean key_blank = false;//空格键发射keycode:32
    private boolean key_p = false;//P键暂停keycode:80
    private boolean key_r = false;//R键重玩keycode:82

    private boolean pause = false;

    public static final int STATE_RUNNING = 0;//运行
    public static final int STATE_RETRY = 1;//重玩
    public static final int STATE_PAUSE = 2;//暂停
    public static final int STATE_GAMEOVER = 3;//游戏结束
    public static final int STATE_SUCCESS = 4;//游戏通过


    public static int state = 0;//默认运行状态

    private SoundController soundController = new SoundController();


    private Controller() {
        initDefaultPin();
        initMyPin();

        Thread th = new Thread(soundController);
        th.start();
    }

    public void initDefaultPin() {
        int x = turntable.getX();
        int y = turntable.getY();
        int R = turntable.getR();

        addPinToList(new DefaultPin(0, x, y, R));
        addPinToList(new DefaultPin(90, x, y, R));
        addPinToList(new DefaultPin(180, x, y, R));
        addPinToList(new DefaultPin(270, x, y, R));
        addPinToList(new DefaultPin(45, x, y, R));
        addPinToList(new DefaultPin(135, x, y, R));
        addPinToList(new DefaultPin(225, x, y, R));
        addPinToList(new DefaultPin(315, x, y, R));
    }

    public void initMyPin() {

        int x = MainWindow.WIN_WIDTH / 2;
        for (int i = 1; i <= level; i++) {
            int y = 420 + 50 * (level + 1 - i);
            MyPin myPin = new MyPin(x, y, i);
            myPin.setCenterX(turntable.getX());
            myPin.setCenterY(turntable.getY());
            addPinToStack(myPin);

        }
    }

    //键盘按下事件
    public void handleKeyPressed(int keyCode) {
        switch (keyCode) {
            case 32:
                key_blank = true;
                break;
            case 80:
                key_p = true;
                break;
            case 82:
                key_r = true;
                break;

            default:
                break;
        }
        keyEventHandle();
    }

    //键盘释放事件
    public void handleKeyReleased(int keyCode) {
        switch (keyCode) {
            case 32:
                key_blank = false;
                break;
            case 80:
                key_p = false;
                break;
            case 82:
                key_r = false;
                break;

            default:
                break;
        }
        keyEventHandle();
    }

    public void keyEventHandle() {
        if (key_blank && !pinStack.isEmpty() && state == STATE_RUNNING) {
            MyPin myPin = pinStack.pop();
            addPinToShootPin(myPin);
            soundController.setPlayShootSound(true);
        }
        if (key_p && state != STATE_GAMEOVER) {
            pause = !pause;
            System.out.println("pause:" + pause);
            if (pause) {
                state = STATE_PAUSE;
            } else {
                state = STATE_RUNNING;
            }
        }
        if (key_r) {
            pinList.clear();
            pinStack.clear();
            shootPinList.clear();

            initDefaultPin();
            initMyPin();

            state = STATE_RUNNING;
        }
    }

    public void paint(Graphics g) {
        g.setFont(new Font("宋体", Font.BOLD, 13));
        g.setColor(Color.RED);
        g.drawString("Level " + level, MainWindow.WIN_WIDTH - 90, 20);
        g.setColor(Color.BLACK);
        turntable.setValue(level);
        turntable.draw(g);

        List<MyPin> shootPin = getShootPin();
        List<DefaultPin> defaultPins = getPinList();

        for (int i = 0; i < shootPin.size(); i++) {
            for (int j = 0; j < defaultPins.size(); j++) {
                MyPin pin1 = shootPin.get(i);
                DefaultPin pin2 = defaultPins.get(j);
                if (pin1.getRectangle().intersects(pin2.getRectangle())) {
                    pin1.setColor(Color.RED);
                    pin2.setColor(Color.RED);

                    soundController.setPlayGameOverSound(true);

                    state = STATE_GAMEOVER;
                }
            }
        }
        drawPins(g);
        drawMyPin(g);
        drawShootPin(g);


        if (pinStack.isEmpty() && state == STATE_RUNNING) {

            level++;
            if (level % 2 == 0) {
                setDirect(true);
            } else {
                setDirect(false);
            }
            pinList.clear();
            pinStack.clear();
            shootPinList.clear();

            initDefaultPin();
            initMyPin();


            state = STATE_RUNNING;
        }
    }

    //畫圓盤上的大頭針
    public void drawPins(Graphics g) {
        for (int i = 0; i < pinList.size(); i++) {
            pinList.get(i).draw(g);
        }
    }

    //画准备发射的大头针
    public void drawMyPin(Graphics g) {
        for (int i = 0; i < pinStack.size(); i++) {
            int y = 420 + 50 * (pinStack.size() - i);
            pinStack.get(i).setY(y);

            pinStack.get(i).draw(g);

        }
    }

    public void drawShootPin(Graphics g) {
        for (int i = 0; i < shootPinList.size(); i++) {
            shootPinList.get(i).shoot();
            shootPinList.get(i).draw(g);
            if (!shootPinList.get(i).isAlive()) {
                shootPinList.remove(i);
            }
        }

    }


    public boolean getDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<DefaultPin> getPinList() {
        return pinList;
    }

    public void addPinToList(DefaultPin pin) {
        this.pinList.add(pin);
    }

    public Stack<MyPin> getPinStack() {
        return pinStack;
    }

    public void addPinToStack(MyPin pin) {
        this.pinStack.push(pin);
    }

    public List<MyPin> getShootPin() {
        return shootPinList;
    }

    public void addPinToShootPin(MyPin pin) {
        this.shootPinList.add(pin);
    }


    public SoundController getSoundController() {
        return soundController;
    }

    //獲取Controller的單例
    public synchronized static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }
}
