package com.example.shooter.guns;

import android.graphics.Bitmap;

import com.example.shooter.Building;
import com.example.shooter.core.ThrowableObject;
import com.example.shooter.core.Graphics;

public class Stone extends ThrowableObject {

    /**
     * Tek resim olarak oluşturulacaksa bu constructor kullanılır
     *
     * @param image
     */
    public Stone(Bitmap image) {
        super(image);
        initialValues();//Başlangıç değerleri verilir
        setScaleX(0.05f);
        setScaleY(0.05f);
    }

    /**
     * Başlangıç değerleri verilir
     */
    private void initialValues() {
        this.damageForce = 20;//Max Hasar gücü 20 yapılır.
        this.weight = 0.3f;//Bu taşın ağırlığı 200 gram olur.
        this.setObjectName("Stone");
    }

    /**
     * Başla bir grafik ile çarpıştığında neler olacağı belirlenir
     * Standart türünde bir binaya çarptığında binanın bir kısmı yıkılır
     * Bir karaktere çarptığında ise karakterin {@link #damageForce} kadar canını eksiltir
     * Çelik bir binaya çarptığında yere düşer. Çelik bina bu çarpışmadan etkilenmez
     *
     * @param graphics
     * @see Building
     */
    @Override
    protected void collision(Graphics graphics) {
        if (graphics.getClass() == Building.class) {
        }
    }
}
