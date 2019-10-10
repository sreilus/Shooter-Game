package com.example.shooter.core;

import android.graphics.Canvas;

public abstract class Drawable {
    /**
     * posX değişkeni resmin yataydaki konumunu tutar
     * Bağlantısı olmayan class'lardan erişilememesi ve içeriğinin değiştirlememesi için protected yapıldı
     */
    protected float posX;
    /**
     * Resmin dikeydeki konumunu belirler
     * Bağlantısı olmayan class'lardan erişilememesi ve içeriğinin değiştirlememesi için protected yapıldı
     */
    protected float posY;

    /**
     * Resmi istediğimiz şekilde oranlayabilmek için scaleX ve scaleY  değişkenlerinden yararlanıyoruz
     * scaleX değişkeni yatay eksende ne kadar oranlanacağını belirler
     * Bağlantısı olmayan class'lardan erişilememesi ve içeriğinin değiştirlememesi için protected yapıldı
     */
    protected float scaleX;
    /**
     * scaleY değişkeni dikey eksende ne kadar oranlanacağını belirler
     * Bağlantısı olmayan class'lardan erişilememesi ve içeriğinin değiştirlememesi için protected yapıldı
     */
    protected float scaleY;

    protected float rotation = 0;

    protected boolean shouldDraw;// Bu nesnesin ekranda çizilmesi gerekip gerekmediğini belirtir.

    public abstract void draw(Canvas canvas);
    public abstract void update();

    private boolean translateCanvas = false;

    public boolean isTranslateCanvas() {
        return translateCanvas;
    }

    public void setTranslateCanvas(boolean translateCanvas) {
        this.translateCanvas = translateCanvas;
    }

    public boolean getShouldDraw() {
        return shouldDraw;
    }

    public void setShouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }



    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }



    public Drawable() {
        setScaleY(1f);
        setScaleX(1f);
        shouldDraw=true;

    }

}
