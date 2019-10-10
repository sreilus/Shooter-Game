package com.example.shooter;

import android.graphics.Bitmap;

import com.example.shooter.core.Graphics;

/**
 * @author Muhammet
 */

public class Background extends Graphics {

    /**
     * Hareketsiz, tek bir resimden oluşan arkaplan resmi oluşturulacaksa bu constuctor kullanılır
     *
     * @param image
     */
    Background(Bitmap image) {
        super(image);
    }

    /**
     * Resmin konumu güncellenir
     */
    @Override
    public void update() {

    }

}
