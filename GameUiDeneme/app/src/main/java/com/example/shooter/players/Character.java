package com.example.shooter.players;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.shooter.GameView;
import com.example.shooter.HealthBar;
import com.example.shooter.IThrow;
import com.example.shooter.MainActivity;
import com.example.shooter.SelectableGun;
import com.example.shooter.ThrowValues;
import com.example.shooter.core.MotionGraphics;
import com.example.shooter.core.ThrowableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Character extends MotionGraphics implements IThrow {

    /**
     * Karakterin can takibini yapar. Değeri 0 olduğunda karakter ölür.
     */
    private HealthBar healthBar;
    /**
     * O an hangi silahın seçili olduğu bilgisini tutar
     */
    private SelectableGun selectableGun;
    /**
     * Karakterin o an kullanabileceği silahların bilgisini tutar
     */
    List<SelectableGun> selectableWeapon;

    private float angle = 45;
    public List<ThrowValues> throwValuesList = new ArrayList<>();
    private String characterName;

    private MainActivity mainActivity;


    private Character enemyCharacter;

    public Character getEnemyCharacter() {
        return enemyCharacter;
    }

    public void setEnemyCharacter(Character enemyCharacter) {
        this.enemyCharacter = enemyCharacter;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

    //Karakteri kontrol edenin bir insan mı yoksa yapay zeka mı olduğunu belirtir.
    //Eğer "Human" ise bir insan yönetiyordur. Eğer "Computer" yazıyor ise yapay zeka tarafından yönetiliyordur.
    private CharacterType characterType = CharacterType.HUMAN;

    public enum CharacterType {
        HUMAN(3),  //calls constructor with value 3
        COMPUTER(2),  //calls constructor with value 2
        ; // semicolon needed when fields / methods follow

        private final int levelCode;

        CharacterType(int typeCode) {
            this.levelCode = typeCode;
        }

        public int getLevelCode() {
            return this.levelCode;
        }

    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    //Oyundaki gameViewîn referansını tutar.
    private GameView gameView;


    /**
     * This is used to calculate the
     * maximum distance that a character
     * can throw an object to.
     */
    private float strength = 100;

    /*
     *Karakteri tek resim olarak oluşturur
     */
    public Character(Bitmap image) {
        super(image);
        setScaleX(0.47f);
        setScaleY(0.47f);
        selectableWeapon = new CopyOnWriteArrayList<>();
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public void setHealthBar(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    public ThrowableObject getGun() {
        return selectableGun.getGun();
    }

    public SelectableGun getSelectableGun() {
        return selectableGun;
    }

    public void setSelectableGun(SelectableGun selectableGun) {
        Log.i("TAG", "Set Gun FirstSpeedX: " + selectableGun.getGun().getSpeedX() + " SpeedY: " + selectableGun.getGun().getSpeedY());
        for (SelectableGun selectableGunFor : getSelectableWeapon()) {
            if (selectableGun.getGun().getClass() == selectableGunFor.getGun().getClass()) {
                this.selectableGun = selectableGunFor;
                this.selectableGun.getGun().setShouldDraw(true);
                this.selectableGun.getGun().setSpeedY(0);
                this.selectableGun.getGun().setSpeedX(0);
                this.selectableGun.setImage(this.selectableGun.getPressedImage());
                Log.i("TAG", "Set Gun FirstSpeedX: " + this.selectableGun.getGun().getSpeedX() + " SpeedY: " + this.selectableGun.getGun().getSpeedY());
            } else {
                selectableGunFor.setImage(selectableGunFor.getRelaseImage());
                selectableGunFor.getGun().setShouldDraw(false);
            }
        }
        this.selectableGun.getGun().setThisCharacter(this);
        this.selectableGun.getGun().setShouldDraw(true);
        setStartingPosThrowableObject(this.selectableGun.getGun());
    }

    public void setStartingPosThrowableObject(ThrowableObject throwableObject) {
        if (this.getScaleX() > 0)//Eğer karakterin yüzü sağa tarafa dönük ise
        {
            if (throwableObject.getObjectName().equals("Arrow"))
            {
                throwableObject.setPosX(this.getPosX() + (gameView.getWidth() / 8));
                throwableObject.setPosY(this.getPosY() + (gameView.getHeight() / 12));
                Log.i("TAG","Arrow");
            }
            else {
                //Atılacak cisim karaktere yakın bir yere konumlandırılıyor.
                throwableObject.setPosX(this.getPosX() + (gameView.getWidth() / 17));
                throwableObject.setPosY(this.getPosY() + (gameView.getHeight() / 12));
            }
            throwableObject.setRotation(1);
        } else //Eğer karakterin yüzü sola dönük ise
        {
            if (throwableObject.getObjectName().equals("Arrow"))
            {
                throwableObject.setPosX(this.getPosX() - gameView.getWidth() / 8);

            }
            else
            {
                //Atılacak cisim karaktere yakın bir yere konumlandırılıyor.
                throwableObject.setPosX(this.getPosX() - gameView.getWidth() / 11);
            }
            throwableObject.setPosY(this.getPosY() + gameView.getHeight() / 15);
            throwableObject.setScaleX(Math.abs(this.selectableGun.getGun().getScaleX()));
            throwableObject.setRotation(-1);
        }
    }

    public void setGun(ThrowableObject gun) {
        this.selectableGun.setGun(gun);
        this.selectableGun.getGun().setThisCharacter(this);

    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public float getStrength() {
        return strength;
    }

    @Override
    public void update() {
        super.update();
    }


    /**
     * Nesne fırlatma işlemi yapılır ve kalan atış hakkı bir azaltılır.
     */
    @Override
    public void _throw(SelectableGun selectableGun, float firstSpeed, float angle) {
        selectableGun.getGun().shot(firstSpeed, angle, true);
        selectableGun.reductionOfRemainingRights();//Kalan atış hakkı bir azaltılır.
    }

    public void _throwWithAI() {
        if (throwValuesList.size() > 0) {
            ThrowValues tempThrowValues = throwValuesList.get(throwValuesList.size() - 1);
            if (tempThrowValues.isCollisionBuilding || tempThrowValues.isCollisionCharacter) {
                if (tempThrowValues.isCollisionCharacter)//Eğer son atışında düşman karaktere isabet ettirmiş ise
                {
                    selectableGun.getGun().shot(tempThrowValues.firstSpeed, tempThrowValues.angle, true);
                } else {
                    angle = (tempThrowValues.angle + 25) % 90;
                    selectableGun.getGun().shot(tempThrowValues.firstSpeed+5, angle, true);
                }
            }
            else {
                angle+=15;
                angle=angle%90;
                selectableGun.getGun().shot(35, 90, true);
            }
        } else {
            angle+=15;
            angle=angle%90;
            selectableGun.getGun().shot(35, 45, true);
        }
        mainActivity.count++;
        mainActivity.triangle.setPosByCharacter(enemyCharacter);
    }

    //Karakterin seçebileceği silahlar listesine
    //silah ekler.
    public void addSelectableWeapon(SelectableGun gun) {
        selectableWeapon.add(gun);
    }

    public List<SelectableGun> getSelectableWeapon() {
        return selectableWeapon;
    }

    //Karakterin seçebileceği silahlar listesinden
    //silah siler.
    public void removeSelectableWeapon(SelectableGun gun) {
        selectableWeapon.remove(gun);
    }

}
