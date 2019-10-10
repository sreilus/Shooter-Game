package com.example.shooter.guns;

import android.graphics.Bitmap;

import com.example.shooter.Building;
import com.example.shooter.core.ThrowableObject;
import com.example.shooter.core.Graphics;

public class Bomb extends ThrowableObject {


    /**
     * Tek resim olarak oluşturulacaksa bu constructor kullanılır
     *
     * @param image
     */
    public Bomb(Bitmap image) {
        super(image);
        initialValues();//Başlangıç değerleri verilir
        setScaleX(0.10f);
        setScaleY(0.10f);
    }


    private void initialValues() {
        this.weight = 0.75f;//Bu bombanın ağırlığı 200 gram olur
        this.damageForce = 50;//Max Hasar gücü 50 yapılır
        this.setObjectName("Bomb");
    }

    /**
     * Başla bir grafik ile çarpıştığında neler olacağı belirlenir
     * Standart türünde bir binaya çarptığında binanın bir kısmı yıkılır
     * Bir karaktere çarptığında ise karakterin {@link #damageForce} kadar canını eksiltir
     * Çelik bir binaya çarptığında binanun bir kısmı yıkılır
     *
     * @param graphics
     * @see Building
     */
    @Override
    protected void collision(Graphics graphics) {

    }
}
