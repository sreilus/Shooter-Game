package com.example.shooter;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.example.shooter.core.Graphics;
import com.example.shooter.core.MotionGraphics;

public class HealthBar extends MotionGraphics {
    //<editor-fold desc="Variables">
    /**
     * Karakterinin canını takip etmek için kullanılır
     * Can barının max değeri. building_6.0 olarak belirlenir
     */
    private float maxValue;
    /**
     * Can barının min değeri. 0.0 olarak belirlenir
     */
    private float minValue;
    /**
     * Can barının anlık değerini gösterir
     */
    private float instantValue;
    /**
     * Can barının arkaplan rengi belirlenir
     */
    String backgroundColor;
    //</editor-fold>
    //<editor-fold desc="HealthBar">

    /**
     * Tek resim ve arkaplan rengi nesne oluşturulurken verilmek isteniyorsa bu constructor kullanılır
     *
     * @param image
     * @param backgroundColor
     */
    public HealthBar(Bitmap image, String backgroundColor) {
        super(image);
        initialValues();//Başlangıç değerleri verilir
        this.backgroundColor = backgroundColor;
    }
    //</editor-fold>
    //<editor-fold desc="HealthBar">

    /**
     * Tek resim verilir ve arkaplan rengi varsayılan değer olarak alınmak istenirse bu constructor kullanılır
     *
     * @param image
     */
    public HealthBar(Bitmap image) {
        super(image);
        initialValues();//Başlangıç değerleri verilir
        this.backgroundColor = "#fff";//Arkaplan rengi verilir
    }
    //</editor-fold>

    /**
     * Başlangıç değerleri verilir
     */
    private void initialValues() {
        this.setMaxValue(1.0f);//Can barının max değeri belirlenir
        this.setMinValue(0.0f);//Can barının min değeri belirlenir
        this.setInstantValue(1.0f);//Can Barının ilk değeri verilir
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getInstantValue() {
        return instantValue;
    }

    public void setInstantValue(float instantValue) {
        this.instantValue = instantValue;
    }


}
