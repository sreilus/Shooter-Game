package com.example.shooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.example.shooter.core.Drawable;
import com.example.shooter.core.Graphics;
import com.example.shooter.players.Character;

public class Triangle extends Graphics {


    public Triangle(Character character, Bitmap image)
    {
        super(image);
        setScaleX(0.1f);
        setScaleY(0.1f);
        setPosByCharacter(character);
    }
    @Override
    public void update() {

    }

    public void setPosByCharacter(Character character)
    {
        if (character.getScaleX()>0)
            setPosX(character.getPosX()+character.getWidth()/6);
        else
            setPosX(character.getPosX()+character.getWidth());
        setPosY(character.getHealthBar().getPosY()-character.getHeight()/3);
    }


}
