package com.example.shooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shooter.core.Drawable;
import com.example.shooter.core.Graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends SurfaceView {
    /**
     * This list holds the complete reference
     * of the graphics within the current screen.
     */
    protected final List<Drawable> graphicsList = new CopyOnWriteArrayList<>();

    ImageButton imagebutton=new ImageButton(getContext());
    /**
     * Oyundaki imageButtonların listesini tutar.
     */
    protected final List<ImageButton> imageButtonList = new CopyOnWriteArrayList<>();

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float rotate = 0;

    private int updateCount = 0;

    private boolean isPaused=false;

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void update() {
        if (!isPaused) {
            for (Drawable graphics : graphicsList) {
                graphics.update();
            }
            updateCount++;

            if (updateCount > 5000) {
                //remove dead objects
                updateCount = 0;
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        for (Drawable drawable : graphicsList) {
            //If the graphics object should not
            //be rendered in the screen, we will
            //abort the drawing action for that
            //specific object.
            if (!drawable.getShouldDraw())
                //This graphics object should not
                //be drawn on to the screen. Skipping
                //to the next one.
                continue;

            //We will store the canvas matrix and
            //stack a new one so that we can draw
            //the next Graphics without affecting
            //the original matrix.
            canvas.save();
            //canvas.rotate(rotate, 960, 540);

            //Performing the object transformation
            //in the correct order.
            if (drawable.isTranslateCanvas())
                //This drawable requires the canvas to
                //be translated as much as its position.
                canvas.translate(drawable.getPosX(), drawable.getPosY());

            canvas.rotate(drawable.getRotation());
            canvas.scale(drawable.getScaleX(), drawable.getScaleY());

            //This object should be drawn. We
            //will simply call the related method.
            drawable.draw(canvas);
            imagebutton.draw(canvas);
            //We are done drawing this Graphics object.
            //We will now restore the original matrix
            //for the canvas object.
            canvas.restore();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWillNotDraw(false);
    }

    /**
     * Grafik listesine grafik ekler.
     *
     * @param graphics
     */
    protected void addDrawble(Drawable graphics) {
//        if (graphics instanceof PowerBar)
            graphicsList.add(graphics);
    }

    protected void removeDrawble(Drawable graphics) {
        graphicsList.remove(graphics);
    }
}
