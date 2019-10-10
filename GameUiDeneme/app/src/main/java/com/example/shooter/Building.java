package com.example.shooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.example.shooter.core.Drawable;
import com.example.shooter.core.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Building extends Graphics {
    /**
     * Oluşturulacak binanın standart bina mı yoksa çelik bina mı olacağı belirlenir
     * Standart bina olacaksa "standart" değeri verilir
     * Çelik bina olacaksa "celik" değeri verilir
     * Çelik bina arrow ve taşın çarpmasından etkilenmez. Bombanın çarpmasından etkilenir
     * Standart bina bütün atılan cisimlerin çarpmasından etkilenir
     */
    private String buildingType;
    public String getBuildingType() {
        return buildingType;
    }
    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    List<HitCircle> circleList = new ArrayList<>();

    /**
     * Binalar hareketsiz olduğu için tek resim halinde oluşturulur
     *
     * @param image
     */

    public Building(Bitmap image) {
        super(image);
        setScaleX(0.4f);
        setScaleY(0.35f);
    }


    /**
     * Resmin konumu güncellenir
     */
    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public int isHit(float x, float y) {
        List<HitCircle> tempList = new ArrayList<>();
        for (HitCircle circle : circleList) {
            Log.i("TAG","girdi+");
            if (!circle.isCollision) {
                float distX = circle.x - x;
                float distY = circle.y - y;
                double distance = Math.sqrt(distX * distX + distY + distY);
                if (distance < circle.r) {
                    Log.i("TAG", "çarptı");
                    circle.circle.paint.setColor(Color.BLUE);
                    circle.circle.paint.setStyle(Paint.Style.FILL_AND_STROKE);
                    circle.isCollision=true;
                    return circle.key;
                }
                if (2 == 3)
                    tempList.add(circle);
            }
            circleList.removeAll(tempList);
        }
        return KEY_NONE;
    }

    public void addCircle(float x, float y, float r) {
        Circle circle = new Circle(x, y, r);
        circleList.add(new HitCircle(x, y, r, circle));

        if (isHit(1, 2) == KEY_HEAD) {

        }
    }

    private static final int KEY_HEAD = 0;
    private static final int KEY_NONE = -1;

    public class HitCircle {
        float x;
        float y;
        float r;
        Circle circle;
        int key;
        boolean isCollision;

        HitCircle(float x, float y, float r, Circle circle) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.circle = circle;
            isCollision = false;
            key=KEY_HEAD;
        }
    }
}
