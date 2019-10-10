package com.example.shooter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import com.example.shooter.core.Drawable;

public class TextView extends Drawable {

    public TextView() {
       initialValues();
    }

    public TextView(String text) {
        this.text = text;
        initialValues();
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    Paint paint = new Paint();



    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text,getPosX(), getPosY(), paint);
        //Log.i("TAG","Çizildii "+canvas.getWidth()+" Y: "+canvas.getHeight());
    }


    @Override
    public void update() {

    }

    /**
     * Yazı boyutunu ayarlar.
     */
    public void setTextSize(int size)
    {
        paint.setTextSize(size);
    }

    /**
     * Bu Nesneye başlangıç değerlerini verir.
     */
    public void initialValues()
    {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        setShouldDraw(true);
    }
}
