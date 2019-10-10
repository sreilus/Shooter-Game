package com.example.shooter.core;

import android.graphics.Bitmap;

import com.example.shooter.Environment;
import com.example.shooter.core.Graphics;

import java.security.spec.EncodedKeySpec;

/**
 * @author Muhammet
 */
public abstract class MotionGraphics extends Graphics {

    /**
     * Yerçekimli bir ortamda ve çarpışmalarda kullanabilmemiz için nesneye ağırlık tanımlanıyor.
     */
    protected float weight;
    /**
     * Bu oyun 2 boyutlu olduğu için nesneler hareket ettiğinde x ve y eksenlerinde hızı vardır.
     * speedX nesnenin x eksenindeki hızını belirler
     */
    protected float speedX;

    public Environment environment;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    /**
     * speedY nesnenin y eksenindeki hızını belirler
     */
    protected float speedY;

    public MotionGraphics(Bitmap[] image) {
        super(image);
        environment=new Environment();
    }

    public MotionGraphics(Bitmap image) {
        super(image);
        environment=new Environment();
    }

    /**
     * Her karede resmin konumu, hızı, ölçeği(scale) ve yönü güncellenir
     */
    @Override
    public void update() {

    }

}
