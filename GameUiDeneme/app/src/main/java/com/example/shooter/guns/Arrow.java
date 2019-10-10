package com.example.shooter.guns;

import android.graphics.Bitmap;

import com.example.shooter.Building;
import com.example.shooter.core.ThrowableObject;
import com.example.shooter.core.Graphics;
import com.example.shooter.players.Character;

public class Arrow extends ThrowableObject {
    /**
     * Tek resimli bir arrow nesnesi oluşturulacağı zaman bu constructor kullanılır
     * @param image
     */
    public Arrow(Bitmap image, Character character) {
        super(image);
        setThisCharacter(character);
        initialValues();//nesneye başlangıç değerleri verilir
        if (getThisCharacter().getScaleX()>0)
        {
            setScaleX(-0.04f);
            setScaleY(-0.04f);
        }
        else
        {
            setScaleX(0.04f);
            setScaleY(0.04f);
        }
    }

    /**
     * Nesneye başlangıç değerleri verilir
     */
    private void initialValues()
    {
        this.damageForce =30;//Max Hasar gücü 30 yapılır
        this.weight=0.5f;//Bu okun ağırlığı 300 gram olur
        this.setObjectName("Arrow");
    }

    /**
     * Başla bir grafik ile çarpıştığında neler olacağı belirlenir
     * Standart türünde bir binaya çarptığında binanın bir kısmı yıkılır
     * Bir karaktere çarptığında ise karakterin {@link #damageForce} kadar canını eksiltir
     * Çelik bir binaya çarptığında yere düşer. Çelik bina bu çarpışmadan etkilenmez
     * @see Building
     * @param graphics
     */
    @Override
    protected void collision(Graphics graphics) {

    }
}
