package com.example.shooter;

import android.graphics.Bitmap;

import com.example.shooter.core.MotionGraphics;

/**
 * Oyunun duraklatılmasını sağlayan butondur
 */
public class PauseButton extends MotionGraphics {
    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
//        gameView.setPaused(paused);
    }

    /**
     * Oyunun duraktılmış olup olmadığını kontrol eder
     */
    private boolean isPaused;

    GameView gameView;
    /**
     * Birden çok resimli hareketli bir grafik olduğu belirtilir
     * @param image
     */
    public PauseButton(Bitmap image) {
        super(image);
    }

    /**
     * Oyun durduğunda olacaklar
     */
    void pause() {

    }

    /**
     * Oyun tekrar devam ettirildiğinde olacaklar
     */
    void contuine() {

    }
}