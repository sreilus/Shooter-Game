package com.example.shooter.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author Muhammet
 * @see Graphics
 * Bütün grafik nesneleri bu class'dan türer
 */
public abstract class Graphics extends Drawable {
    /**
     * image değişkeni grafiğin ekranda görünecek resmini tutar
     * Bağlantısı olmayan class'lardan erişilememesi ve içeriğinin değiştirlememesi için protected yapıldı
     */
    protected final Bitmap[] image;

    /**
     * Resmin gölgesinin yoğunluğu belirlenir. 0 ile 1 arasında tanımlanır
     */
    float shadowDensisty = 1f;

    /**
     * Eğer bitmap nesnesi dizisi ile başlatılırsa bu class'daki image nesnesi gelen image nesnesinw eşitlenir
     *
     * @param image
     */
    public Graphics(Bitmap[] image) {
        this.image = image;
        imageIndex = 0;
        scaleX = 1f;
        scaleY = 1f;
        paint = new Paint();
        setTranslateCanvas(true);
    }

    private int imageIndex;
    private Paint paint;

    /**
     * Eğer bitmap nesnesi tek bir resim olarak gönderilirse bu class'daki image dizisinin uzunluğu building_6 olarak ayarlanır
     * Hareketsiz resimlerde bu metod kullanılır
     *
     * @param image
     */
    public Graphics(Bitmap image) {
        this(new Bitmap[]{image});
        setTranslateCanvas(true);
    }

    public float getWidth() {
        return getImage().getWidth() * getScaleX();
    }

    public float getHeight() {
        return getImage().getHeight() * getScaleY();
    }

    public Rect getRectangle() {
        if (getScaleX() > 0)
            return new Rect((int) posX, (int) posY, (int) (posX + getWidth()), (int) (posY + getHeight()));
        else
            return new Rect((int) posX, (int) posY, (int) (posX - getWidth()), (int) (posY + getHeight()));
    }

    public Matrix getMatrix() {
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        //Eğer shouldRaw değişkeninin değeri false
        //ise resmin çizilmesi engellenir
        //Eğer shouldRaw değişkeninin değeri true ise
        //resim her karede tekrar çizilir
        if (!getShouldDraw())
            return;

        canvas.drawBitmap(getImage(), 0, 0, getPaint());
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        image[imageIndex] = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return image[imageIndex];
    }

    public Bitmap getImage() {
        return image[imageIndex];
    }

    public void setImage(Bitmap image) {
        this.image[imageIndex] = image;
    }

    public Paint getPaint() {
        return paint;
    }


}
