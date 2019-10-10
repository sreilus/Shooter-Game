package com.example.shooter;

import android.graphics.Bitmap;

import com.example.shooter.core.Graphics;

public class Stick extends Graphics {

    public Stick(Bitmap[] image) {
        super(image);
        initialValues();
    }

    public Stick(Bitmap image) {
        super(image);
        initialValues();
    }

    @Override
    public void update() {

    }

    public void initialValues() {
        setScaleX(1);
        setScaleY(1);
        setRotation(1);
    }
}
