package com.example.shooter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.shooter.core.Drawable;

public class PowerBar extends Drawable  {

    Paint paint = new Paint();

    private float lastX;

    public float getLastX() {
        return lastX;
    }

    public void setLastX(float lastX) {
        this.lastX = lastX;
    }

    public float getLastY() {
        return lastY;
    }

    public void setLastY(float lastY) {
        this.lastY = lastY;
    }

    private float lastY;

    public PowerBar() {
        setScaleX(1f);
        setScaleY(1f);
        setRotation(0);
        setPosX(0);
        setPosY(0);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
    }

    @Override
    public void draw(Canvas canvas) {
        System.out.println("PowerBar: [X1: " + getPosX() + "Y1: " + getPosY() + "X2: " + getLastX() + "Y2: " + getLastY());
        canvas.drawLine(getPosX(), getPosY(), getLastX(), getLastY(), paint);
    }

    @Override
    public void update() {

    }
}
