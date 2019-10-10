package com.example.shooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.example.shooter.core.Graphics;
import com.example.shooter.core.ThrowableObject;
import com.example.shooter.guns.Arrow;
import com.example.shooter.guns.Bomb;
import com.example.shooter.guns.Stone;
import com.example.shooter.players.Character;

public class SelectableGun  extends Graphics {

    private Bitmap pressedImage;

    private Bitmap relaseImage;

    public Bitmap getPressedImage() {
        return pressedImage;
    }

    public void setPressedImage(Bitmap pressedImage) {
        this.pressedImage = pressedImage;
    }

    public Bitmap getRelaseImage() {
        return relaseImage;
    }

    public void setRelaseImage(Bitmap relaseImage) {
        this.relaseImage = relaseImage;
    }

    private Character character;

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    private TextView txtRemainingRights;

    public TextView getTxtRemainingRights() {
        return txtRemainingRights;
    }

    public void setTxtRemainingRights(TextView txtRemainingRights) {
        this.txtRemainingRights = txtRemainingRights;
    }

    public int getRemainingRights() {
        return remainingRights;
    }

    public void setRemainingRights(int remainingRights) {
        this.remainingRights = remainingRights;
    }

    //Kalan kullanım hakkı
    private int remainingRights=0;

    private ThrowableObject gun;

    public ThrowableObject getGun() {
        return gun;
    }

    public void setGun(ThrowableObject gun) {
        this.gun = gun;
    }


    public SelectableGun(Bitmap[] image, ThrowableObject gun, Character character) {
        super(image);
        setGun(gun);
        setCharacter(character);
    }

    public SelectableGun(Bitmap image,ThrowableObject gun,Character character) {
        super(image);
        setGun(gun);
        setCharacter(character);
        setScaleX(0.23f);
        setScaleY(0.23f);
        txtRemainingRights=new TextView("new txt");
        if (getGun() instanceof Arrow)//Eğer bu silah ok ise kalan kullanım hakkı 12 olsun.
            setRemainingRights(12);
        else if (getGun() instanceof Bomb)//Eğer bu silah bomba ise kalan kullanım hakkı 5 olsun
            setRemainingRights(5);
        else if (getGun() instanceof Stone)//Eğer bu silah taş ise kalan kullanım hakkı 9999 olsun
            setRemainingRights(9999);
    }

    public void reductionOfRemainingRights()
    {
        remainingRights--;
        txtRemainingRights.setText(""+getRemainingRights());
    }

    @Override
    public void update() {

    }
}
