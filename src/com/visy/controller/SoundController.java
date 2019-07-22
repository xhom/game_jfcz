package com.visy.controller;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class SoundController implements Runnable {
    private static URL gameOverUrl;
    private static URL shootUrl;
    private static URL shootedUrl;

    private boolean playGameOverSound = false;
    private boolean playShootSound = false;
    private boolean playShootedSound = false;

    private AudioClip gameOverSound;
    private AudioClip shootSound;
    private AudioClip shootedSound;

    public SoundController() {
        gameOverUrl = SoundController.class.getClassLoader().getResource("com/visy/sounds/gameOver.wav");
        shootUrl = SoundController.class.getClassLoader().getResource("com/visy/sounds/shoot.wav");
        shootedUrl = SoundController.class.getClassLoader().getResource("com/visy/sounds/shooted.wav");

        gameOverSound = Applet.newAudioClip(gameOverUrl);
        shootSound = Applet.newAudioClip(shootUrl);
        shootedSound = Applet.newAudioClip(shootedUrl);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Ïß³Ì»½ÐÑÊ§°Ü£¡");
            }
            if (playGameOverSound) {
                gameOverSound.play();
                playGameOverSound = false;
            }
            if (playShootSound) {
                shootSound.play();
                playShootSound = false;
            }
            if (playShootedSound) {
                shootedSound.play();
                playShootedSound = false;
            }
        }

    }

    public void setPlayGameOverSound(boolean playGameOverSound) {
        this.playGameOverSound = playGameOverSound;
    }

    public void setPlayShootSound(boolean playShootSound) {
        this.playShootSound = playShootSound;
    }

    public void setPlayShootedSound(boolean playShootedSound) {
        this.playShootedSound = playShootedSound;
    }
}
