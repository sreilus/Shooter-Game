package com.example.shooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.shooter.core.Drawable;
import com.example.shooter.core.Graphics;

public class StrengthBar extends Drawable  {

    Paint paint = new Paint();


    public StrengthBar() {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        setScaleX(1f);
        setScaleY(1f);
        setPosX(0);
        setPosY(0);
    }

    @Override
    public void draw(Canvas canvas) {
          canvas.drawRect(30, 30, 80, 80, paint);
    }

    @Override
    public void update() {

    }
}
