package com.example.shooter.core;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

import com.example.shooter.Building;
import com.example.shooter.Circle;
import com.example.shooter.GameView;
import com.example.shooter.MainActivity;
import com.example.shooter.R;
import com.example.shooter.ResultActivity;
import com.example.shooter.SelectableGun;
import com.example.shooter.ThrowValues;
import com.example.shooter.players.Character;

import java.util.List;

/**
 * @author Muhammet
 */
public abstract class ThrowableObject extends MotionGraphics {

    private MainActivity mainActivity;
    //Bu silahı kullanıcak olan karakterin referansı.
    private Character thisCharacter;
    //Düşman Karakterin referansı.
    private Character enemyCharacter;
    //Bu cismin bağlı olduğu butonun referansını belirtir.
    private SelectableGun selectableGun;
    //Bu silahın ismi.
    private String objectName;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public SelectableGun getSelectableGun() {
        return selectableGun;
    }

    public void setSelectableGun(SelectableGun selectableGun) {
        this.selectableGun = selectableGun;
    }

    public Character getThisCharacter() {
        return thisCharacter;
    }

    public void setThisCharacter(Character thisCharacter) {
        this.thisCharacter = thisCharacter;
    }

    public Character getEnemyCharacter() {
        return enemyCharacter;
    }

    public void setEnemyCharacter(Character enemyCharacter) {
        this.enemyCharacter = enemyCharacter;
    }

    //Bu cismin bulunduğu gameview.
    private GameView gameView;

    private float firstSpeed;

    public float getFirstSpeed() {
        return firstSpeed;
    }

    public void setFirstSpeed(float firstSpeed) {
        this.firstSpeed = firstSpeed;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Nesnenin farklı grafik nesnelerine çarptığında onlara vereceği hasarın gücü belirlenir
     */
    protected float damageForce;

    //Cismin şu an uçuyor olup olmadığını tutar.
    //True ise uçus halindedir.
    //False ise durmuş haldedir.
    private boolean isFlying = false;
    //Cismin Y eksenindeki ilk hızını tutar.
    private float firstSpeedY = 0;
    //Cismin X eksenindeki ilk hızını tutar.
    private float firstSpeedX = 0;
    //Fırlatılacak cismin eğim açısı.
    private float angle = 45;
    //Silahın oyundaki x ekseninde ilk pozisyonu.
    private float getFirstPosX = 0;
    //Ekrana dokunulup yer değiştirme yapılıp yapılmadığını
    //kontrol eder.
    //Ekranda atılacak cismin nereye gidebileceğini
    //gösterebilmek için kullanılır.
    private boolean isTouchMoving = false;

    public boolean isTouchMoving() {
        return isTouchMoving;
    }

    public void setTouchMoving(boolean touchMoving) {
        isTouchMoving = touchMoving;
    }

    //Oyundaki bimaların listesi.
    public List<Building> buildingList;

    public List<Character> characters;

    public float getGetFirstPosX() {
        return getFirstPosX;
    }

    public void setGetFirstPosX(float getFirstPosX) {
        this.getFirstPosX = getFirstPosX;
    }

    public float getGetFirstPosY() {
        return getFirstPosY;
    }

    public void setGetFirstPosY(float getFirstPosY) {
        this.getFirstPosY = getFirstPosY;
    }

    //Cismin ilk oluşturulurkenki y konumu.
    private float getFirstPosY = 0;

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }


    public float getDamageForce() {
        return damageForce;
    }

    public void setDamageForce(float damageForce) {
        this.damageForce = damageForce;
    }

    public boolean getFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }

    public float getFirstSpeedY() {
        return firstSpeedY;
    }

    public void setFirstSpeedY(float firstSpeedY) {
        this.firstSpeedY = firstSpeedY;
    }


    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void update() {
        super.update();
        //Eğer uçmaya hazır ise.
        if (isFlying) {

            //Bu objenin bina listesindeki herhangi bir binaya çarpıp çarpıp
            //çarpmadığını kontrol eder.
            //Eğer çarpmış ise çarptığı binanın
            //üzerinde yuvarlak oluşturur.
            for (Building building : buildingList) {
                if (collision(this, building)) {
                    Log.i("TAG", "Circle+++++++: " + "posX: " + this.getPosX() + " posY: " + this.getPosY());
                    gameView.postInvalidate();
                    if (building.isHit(this.getPosX(), this.getPosY()) == 0) {
                        setStartingPosition();
                        if (mainActivity.gameType.equals("Computer")) {
                            if (thisCharacter.getCharacterType() == Character.CharacterType.COMPUTER) {
                                ThrowValues throwValues = new ThrowValues(firstSpeed, angle, false, true);
                                thisCharacter.throwValuesList.add(throwValues);
                            }
                            throwWithAIForEnemeyCharacter();
                        }
                    }
                }
            }
            for (Character character : characters) {
                //  Log.i("TAG"," sayi: "+character.getRectangle()+" bomba : "+this.getRectangle());
                if (character != thisCharacter) {
                    // Log.i("Yeni2","ch: "+character.getHeight());
                    if (collision(this, character)) {
                        if (character.getScaleX() > 0 && character.getHealthBar().getScaleX() > 0.01)//Eğer sol taraftaki karakter ise ve
                        //can barı hala küçültülecebilecek düzeyde ise
                        {
                            Log.i("TAG", "scaleX: " + (character.getHealthBar().getScaleX()));
                            character.getHealthBar().setScaleX(character.getHealthBar().getScaleX() - 0.01f);
                            Log.i("TAG", "scaleXYeni: " + (character.getHealthBar().getScaleX()));
                            setStartingPosition();
                            if (mainActivity.gameType.equals("Computer")) {
                                if (thisCharacter.getCharacterType() == Character.CharacterType.COMPUTER) {
                                    ThrowValues throwValues = new ThrowValues(firstSpeed, angle, true, false);
                                    thisCharacter.throwValuesList.add(throwValues);
                                }
                                throwWithAIForEnemeyCharacter();
                            }
                        } else if (character.getScaleX() < 0 && character.getHealthBar().getScaleX() > 0.01)//Eğer sağ taraftaki karakter ise ve
                        //can barı hala küçültülecebilecek düzeyde ise
                        {
                            Log.i("TAG", "scaleX: " + (character.getHealthBar().getScaleX()));
                            character.getHealthBar().setScaleX(character.getHealthBar().getScaleX() - 0.01f);
                            Log.i("TAG", "scaleXYeni: " + (character.getHealthBar().getScaleX()));
                            setStartingPosition();
                            if (mainActivity.gameType.equals("Computer")) {
                                if (thisCharacter.getCharacterType() == Character.CharacterType.COMPUTER) {
                                    ThrowValues throwValues = new ThrowValues(firstSpeed, angle, true, false);
                                    thisCharacter.throwValuesList.add(throwValues);
                                }
                                throwWithAIForEnemeyCharacter();
                            }
                        } else {//Oyunun bittiğine dair Tost mesaj yazdırılır.
                            mainActivity.runOnUiThread(new Runnable() {
                                public void run() {
                                    Intent intent = new Intent(mainActivity, ResultActivity.class);
                                    intent.putExtra("data", getThisCharacter().getCharacterName());
                                    intent.putExtra("gameType", mainActivity.gameType);
                                    mainActivity.finish();
                                    mainActivity.startActivity(intent);
                                }
                            });
                        }
                    }
                }
            }
            setSpeedY(getSpeedY() - environment.gravity);//Y hızını yerçekimi kadar düşür.
            if (thisCharacter.scaleX > 0) {
                setPosX(getPosX() + getSpeedX());//X konumunu cismin X yönündeki hızı kadar arttır.
                setPosY(getPosY() - getSpeedY());//Y konumunu cismin Y yönündeki hızı kadar azalt.
                Log.i("hızY: ", "hızY :" + (getSpeedY()));
                if (getSpeedY() > 0)
                    rotation -= 1.8f;
                else
                    rotation += 3.8f;

            } else {
                setPosX(getPosX() - getSpeedX());//X konumunu cismin X yönündeki hızı kadar arttır.
                setPosY(getPosY() - getSpeedY());//Y konumunu cismin Y yönündeki hızı kadar azalt.
                rotation -= 2.5f;
            }
            if (getPosY() > 1150) {//Eğer hızı ilk hızını geçtiyse uçmayı durdur.
                setStartingPosition();
            }
        }
    }

    /**
     * Birden fazla resimli bir nesne olacaksa bu constructor kullanılır
     * Hareketli resim olmasına olanak sağlar
     *
     * @param image
     */
    public ThrowableObject(Bitmap[] image) {
        super(image);
    }

    /**
     * Hareketsiz resimli bir nesne üretmek istiyorsa tek resimli constructor kullanılır
     *
     * @param image
     */
    public ThrowableObject(Bitmap image) {
        super(image);
    }

    /**
     * Bu nesnenin başka bir graphics nesnesiyle çarpışacağı zaman neler olacağı bu metod ile belirlenir
     *
     * @param graphics
     */
    protected abstract void collision(Graphics graphics);

    /**
     * Oyuncudan ilk hız ve eğim derecesi alınır.
     * ilk hızın sinüs açısı ile çarpılmasından Y ekseninde ilk hız elde edilir.
     * ilk hızın cosünüs açısı ile çarpılmasından X ekseninde ilk hız elde edilir.
     * Cismin hızı buradan alınan ilk X ve Y hızlarına eşitlenir.
     * Cisim uçuşa hazır hale getirilir.
     *
     * @param firstSpeed
     * @param angle
     */
    public void shot(float firstSpeed, float angle, boolean isFlying) {
        if (getSelectableGun().getRemainingRights() > 0) //Eğer bu silahın atış hakkı kaldıysa
        {
            setFirstSpeed(firstSpeed);
            setAngle(angle);
            Log.i("açı", "fsX " + (firstSpeedX) + " fsY : " + (firstSpeedY) + " speedX: " + getSpeedX() + " speedY: " + getSpeedY());
            setSpeedX(0);
            setSpeedY(0);
            if (!getFlying() && firstSpeed > 0)//Eğer şu anda uçuş halinde değil ise
            {
                firstSpeedX = firstSpeed * (float) Math.cos(Math.toRadians(angle));//ilk hızın cosünüs açısı ile çarpılmasından X ekseninde ilk hız elde edilir.
                firstSpeedY = firstSpeed * (float) Math.sin(Math.toRadians(angle));//ilk hızın sinüs açısı ile çarpılmasından Y ekseninde ilk hız elde edilir.
                setSpeedX(firstSpeedX);
                setSpeedY(firstSpeedY);
                if (!isFlying) {
                    float maxY = (firstSpeedY * firstSpeedY) / (2 * environment.gravity);//Cismin gidebileceği max yüksekliği hesaplar.
                    float tFlying = firstSpeedY / environment.gravity;//Cismin toplam uçuş süresini hesaplar.
                }
                setFlying(isFlying);
                Log.i("açı", "fsX " + String.valueOf(firstSpeedX) + " fsY : " + String.valueOf(firstSpeedY) + "---speedX: " + getSpeedX() + " ---speedY: " + getSpeedY());
            }
        } else {

        }
    }

    /**
     * 2 objenin çarpışıp çarpışmadığını kontrol eder.
     *
     * @param a
     * @param b
     * @return
     */
    private boolean collision(Graphics a, Graphics b) {
        if (Rect.intersects(a.getRectangle(), b.getRectangle())) {
            return true;
        }
        return false;
    }

    /**
     * Bu objeyi başlangıç pozisyonuna getirir.
     */
    private void setStartingPosition() {
        isFlying = false;
        thisCharacter.setStartingPosThrowableObject(this);
        firstSpeedX = 0;
        firstSpeedY = 0;
        setSpeedX(0);
        setSpeedY(0);
    }

    /**
     * Bu silah bir binaya veya düşman karaktere çarptığında
     * Atış sırası diğer karaktere geçer.
     * Ve eğer düşman karakter AI tarafından yönetiliyor ise
     * Bu çarpışmadan hemen sonra AI atışı yapılır.
     */
    private void throwWithAIForEnemeyCharacter() {
        if (mainActivity.gameType.equals("Computer")) {
            if (enemyCharacter.getCharacterType() == Character.CharacterType.COMPUTER)
                enemyCharacter._throwWithAI();
        }
    }


}
