package com.example.shooter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.shooter.core.Drawable;

public class Circle extends Drawable {
    Paint paint = new Paint();
    //Bu nesnenin yarıçapı.
    private float radius=30;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Circle() {
       initialValues();

    }

    public Circle(float posX,float posY,float radius)
    {
        this.setPosX(posX);
        this.setPosY(posY);
        this.setRadius(radius);
        initialValues();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(this.getPosX(), this.getPosY(), getRadius(), paint);
    }

    @Override
    public void update() {

    }

    public void initialValues()
    {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        setScaleX(1f);
        setScaleY(1f);
        shouldDraw = true;
    }
}