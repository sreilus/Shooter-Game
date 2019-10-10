package com.example.shooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shooter.core.Graphics;
import com.example.shooter.core.ThrowableObject;
import com.example.shooter.guns.Arrow;
import com.example.shooter.guns.Bomb;
import com.example.shooter.guns.Stone;
import com.example.shooter.players.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Muhammet
 */
public class MainActivity extends AppCompatActivity {
    GameView gameView;

    Thread gameThread;
    //Oyuna eklenen binaların listesini tutar.
    //Karakterler binaların üzerine konumlandırılır.
    List<Building> buildings;
    //Oyunda kullanılabilcek bütün binaların drawable klasöründeki resim id'lerini saklar
    //Oyun oluşturulurken binaların rasgetele olarak yüklenmesine yardımcı olur
    List<Integer> buildingImageIds;

    //Oyuna eklenen karakterlerin listesini tutar.
    List<Character> characters;
    //Oyunda seçilebilecek silahların listesini tutar.
    List<SelectableGun> selectableGunList;
    Graphics bos_sari_daire;
    public String gameType;
    int screenWidth;
    int screenHeight;
    public int count = 0;
    float maxSubstitution = 0;
    float moveX;
    float moveY;
    boolean isClickedSelectableGun = false;
    boolean isClickedPauseButton=false;
    public Triangle triangle;

    //<editor-fold desc="Create EditTexts">
    TextView txtRemindRightsArrowMan = new TextView("new textview");
    TextView txtRemindRightsArrowNinja = new TextView("new textview");
    TextView txtRemindRightsBombMan = new TextView("new textview");
    TextView txtRemindRightsBombNinja = new TextView("new textview");
    TextView txtRemindRightsStoneMan = new TextView("new textview");
    TextView txtRemindRightsStoneNinja = new TextView("new textview");
    //</editor-fold>

    PowerBar powerBar;
    //Turun hangi karakterde olduğu tutulur.
    Character selectedCharacter;
    //<editor-fold desc="Add Guns Bitmap">


    Bitmap bomb;
    Bitmap yellowCircleBomb;
    Bitmap redCircleBomb;
    Bitmap arrowBitmap;
    Bitmap yellowCircleArrow;
    Bitmap redCircleArrow;
    Bitmap stoneBitmap;
    Bitmap yellowCircleStone;
    Bitmap redCircleStone;
    Bitmap triangleImage;
    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        gameType = extras.getString("gameType");
        gameView = findViewById(R.id.gamePlay);
        //<editor-fold desc="Display Metrics">
        /**
         * Ekran boyutunu alır.
         */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        //</editor-fold">

        maxSubstitution = maxSubstitutionCalculate(moveX, moveY);//Maximum yerdeğiştirmeyi hesaplar.
        Log.i("TAG", "Max Sub: " + String.valueOf(maxSubstitution));

        buildings = new ArrayList<>();
        buildingImageIds = new ArrayList<>();
        characters = new CopyOnWriteArrayList<>();
        selectableGunList = new CopyOnWriteArrayList<>();

        //<editor-fold desc="Add Guns Bitmap">
        bomb = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
        yellowCircleBomb = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_circle_bomb);
        redCircleBomb = BitmapFactory.decodeResource(getResources(), R.drawable.red_circle_bomb);
        arrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
        yellowCircleArrow = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_circle_arrow);
        redCircleArrow = BitmapFactory.decodeResource(getResources(), R.drawable.red_circle_arrow);
        stoneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stone1);
        yellowCircleStone = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_circle_stone);
        redCircleStone = BitmapFactory.decodeResource(getResources(), R.drawable.red_circle_stone);
        triangleImage = BitmapFactory.decodeResource(getResources(), R.drawable.triangle);
        //</editor-fold>


        //<editor-fold desc="Add Building Images Ids">

        //Drawable klasöründeki resimlerin id'lerini buildingImageIds listesine
        //eklerken kullandığımız geçici değişken
        //resmin id'sini tutar
        buildingImageIds.add(R.drawable.building_1);
        buildingImageIds.add(R.drawable.building_2);
        buildingImageIds.add(R.drawable.building_3);
        buildingImageIds.add(R.drawable.building_4);
        buildingImageIds.add(R.drawable.building_5);
        buildingImageIds.add(R.drawable.building_6);
        buildingImageIds.add(R.drawable.building_7);
        buildingImageIds.add(R.drawable.celik_bina);
        buildingImageIds.add(R.drawable.celik_bina_2_kat);

        //</editor-fold>
        float maxWidth = screenWidth; //getMaxScreenWidth
        float maxHeight = screenHeight; //getMaxScreenHeight
        float currentWidth = 0;
        HashMap<Integer, Bitmap> imageMap = new HashMap<>();
        Random random = new Random();
        //<editor-fold desc="Add Buildings">

        do {
            int number = random.nextInt(buildingImageIds.size());
            if (buildings.size() == 2) {
                number = (int) Math.random() * 2 + 1;
            }
            int buildingImageId = buildingImageIds.get(number);
            Bitmap bitmap = imageMap.get(number);
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(getResources(), buildingImageId);
                imageMap.put(number, bitmap);
            }

            Building building = new Building(bitmap);
            addBuilding(building, currentWidth);
            currentWidth += building.getWidth();
        } while (currentWidth < maxWidth);
        //</editor-fold>

        //Oyuna arkaplan resmi ekler.
        gameView.setBackground(getResources().getDrawable(R.drawable.background_color_grass_with_road));


        //<editor-fold desc="Create Characters">

        Character character_man = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.krkt_woman));
        character_man.setPosY(buildings.get(1).getPosY() - character_man.getHeight());
        character_man.setPosX(buildings.get(1).getPosX());
        character_man.setStrength(120);
        character_man.setGameView(gameView);
        character_man.setCharacterName("Jack");
        character_man.setCharacterType(Character.CharacterType.HUMAN);
        character_man.setMainActivity(this);
        if (character_man.getCharacterType().equals(Character.CharacterType.COMPUTER)) {

        }

        //Eğer karakterin önündeki bina karakterin üstünde
        //durduğu binadan yüksekse binaları yer değiştirir.
        if (buildings.get(buildings.size() - 2).getHeight() < buildings.get(buildings.size() - 3).getHeight())//E
        {
            float tempPosX3 = buildings.get(buildings.size() - 3).getPosX();
            Building tempBuilding = buildings.get(buildings.size() - 2);
            buildings.get(buildings.size() - 2).setPosX(tempPosX3);
            buildings.get((buildings.size() - 3)).setPosX(tempPosX3 + buildings.get(buildings.size() - 2).getWidth());
            buildings.set((buildings.size() - 2), buildings.get(buildings.size() - 3));
            buildings.set((buildings.size() - 3), tempBuilding);
        }
        Character character_ninja = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.krkt_ninja));
        character_ninja.setPosY(buildings.get(buildings.size() - 2).getPosY() - character_ninja.getHeight());//545
        character_ninja.setPosX(buildings.get(buildings.size() - 2).getPosX() + character_ninja.getWidth());
        character_ninja.setScaleX(character_ninja.getScaleX() * -1);
        character_ninja.setStrength(130);
        character_ninja.setGameView(gameView);
        character_ninja.setMainActivity(this);
        character_ninja.setCharacterType(Character.CharacterType.COMPUTER);
        character_ninja.setCharacterName("John");
        character_ninja.setEnemyCharacter(character_man);
        character_man.setEnemyCharacter(character_ninja);

        characters.add(character_ninja);
        characters.add(character_man);


        gameView.addDrawble(character_man);
        gameView.addDrawble(character_ninja);

        //</editor-fold>
        Bomb bomb_ch_man = new Bomb(bomb);
        Bomb bomb_ch_ninja = new Bomb(bomb);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);


        //<editor-fold desc="Add HealthBars">


        Bitmap healthBar = BitmapFactory.decodeResource(getResources(), R.drawable.health_bar);
        HealthBar healtBarCrhMan = new HealthBar(healthBar);
        healtBarCrhMan.setScaleX(0.07f);
        healtBarCrhMan.setScaleY(0.09f);
        healtBarCrhMan.setPosX(character_man.getPosX());
        healtBarCrhMan.setPosY(character_man.getPosY() - 50);
        character_man.setHealthBar(healtBarCrhMan);

        HealthBar healtBarCrhNinja = new HealthBar(healthBar);
        healtBarCrhNinja.setScaleX(0.07f);
        healtBarCrhNinja.setScaleY(0.09f);
        healtBarCrhNinja.setPosX(character_ninja.getPosX() - screenWidth / 15);
        healtBarCrhNinja.setPosY(character_ninja.getPosY() - screenHeight / 20);
        character_ninja.setHealthBar(healtBarCrhNinja);

        //</editor-fold>

        gameView.addDrawble(healtBarCrhMan);
        gameView.addDrawble(healtBarCrhNinja);

        //<editor-fold desc="Create Selectable Gun Bomb For Character_Man"

        SelectableGun selectableGunBombMan = new SelectableGun(yellowCircleBomb, bomb_ch_man, character_man);
        selectableGunBombMan.setPosX(selectableGunBombMan.getCharacter().getPosX() - screenWidth / 20);
        selectableGunBombMan.setPosY((screenHeight / 6) * 5);
        gameView.addDrawble(selectableGunBombMan);
        character_man.addSelectableWeapon(selectableGunBombMan);
        selectableGunList.add(selectableGunBombMan);
        selectableGunBombMan.setPressedImage(redCircleBomb);
        selectableGunBombMan.setRelaseImage(yellowCircleBomb);
        //</editor-fold>


        Arrow arrow_ch_man = new Arrow(arrowBitmap, character_man);

        //<editor-fold desc="Create Selectable Gun Arrow For Character_Man"

        SelectableGun selectableGunArrowMan = new SelectableGun(yellowCircleArrow, arrow_ch_man, character_man);
        selectableGunArrowMan.setPosX(selectableGunBombMan.getCharacter().getPosX() + screenWidth / 35);
        selectableGunArrowMan.setPosY((screenHeight / 6) * 5);
        gameView.addDrawble(selectableGunArrowMan);
        character_man.addSelectableWeapon(selectableGunArrowMan);
        selectableGunList.add(selectableGunArrowMan);
        selectableGunArrowMan.setRelaseImage(yellowCircleArrow);
        selectableGunArrowMan.setPressedImage(redCircleArrow);
        //</editor-fold>


        //<editor-fold desc="Create Stone For Character_Man"
        Stone stone_ch_man = new Stone(stoneBitmap);
        //</editor-fold>

        //<editor-fold desc="Create Selectable Gun Stone For Character_Man"

        SelectableGun selectableGunStoneMan = new SelectableGun(yellowCircleStone, stone_ch_man, character_man);
        selectableGunStoneMan.setPosX(selectableGunBombMan.getCharacter().getPosX() + screenWidth / 9);
        selectableGunStoneMan.setPosY((screenHeight / 6) * 5);
        gameView.addDrawble(selectableGunStoneMan);
        character_man.addSelectableWeapon(selectableGunStoneMan);
        selectableGunList.add(selectableGunStoneMan);
        selectableGunStoneMan.setPressedImage(redCircleStone);
        selectableGunStoneMan.setRelaseImage(yellowCircleStone);
        //</editor-fold>

        //<editor-fold desc="Create Selectable Gun Bomb For Character_Ninja"

        SelectableGun selectableGunBombNinja = new SelectableGun(yellowCircleBomb, bomb_ch_ninja, character_ninja);
        selectableGunBombNinja.setPosX(screenWidth - screenWidth / 4);
        selectableGunBombNinja.setPosY((screenHeight / 6) * 5);
        gameView.addDrawble(selectableGunBombNinja);
        selectableGunList.add(selectableGunBombNinja);
        selectableGunBombNinja.setRelaseImage(yellowCircleBomb);
        selectableGunBombNinja.setPressedImage(redCircleBomb);
        //</editor-fold>


        Arrow arrow_ch_ninja = new Arrow(arrowBitmap, character_ninja);
        //<editor-fold desc="Create Selectable Gun Arrow For Character_Ninja"

        SelectableGun selectableGunArrowNinja = new SelectableGun(yellowCircleArrow, arrow_ch_ninja, character_ninja);
        selectableGunArrowNinja.setPosX(screenWidth - screenWidth / 5.7f);
        selectableGunArrowNinja.setPosY((screenHeight / 6) * 5);
        gameView.addDrawble(selectableGunArrowNinja);
        selectableGunList.add(selectableGunArrowNinja);
        selectableGunArrowNinja.setPressedImage(redCircleArrow);
        selectableGunArrowNinja.setRelaseImage(yellowCircleArrow);
        //</editor-fold>


        //<editor-fold desc="Create Stone for Character_Ninja"
        Stone stone_ch_ninja = new Stone(stoneBitmap);
        //</editor-fold>

        //<editor-fold desc="Create Selectable Gun Stone For Character_Ninja"
        SelectableGun selectableGunStoneNinja = new SelectableGun(yellowCircleStone, stone_ch_ninja, character_ninja);
        selectableGunStoneNinja.setPosX(screenWidth - screenWidth / 10);
        selectableGunStoneNinja.setPosY((screenHeight / 6) * 5);
        gameView.addDrawble(selectableGunStoneNinja);
        selectableGunList.add(selectableGunStoneNinja);
        selectableGunStoneNinja.setPressedImage(redCircleStone);
        selectableGunStoneNinja.setRelaseImage(yellowCircleStone);
        //</editor-fold>

        //<editor-fold desc="Add Features For Throwables"

        //Silahlara gerekli oyun parametrelerini tanımlar.
        setThrowableObjectInitialValues(bomb_ch_man, selectableGunBombMan, character_man, character_ninja, txtRemindRightsBombMan);
        setThrowableObjectInitialValues(bomb_ch_ninja, selectableGunBombNinja, character_ninja, character_man, txtRemindRightsBombNinja);
        setThrowableObjectInitialValues(arrow_ch_man, selectableGunArrowMan, character_man, character_ninja, txtRemindRightsArrowMan);
        setThrowableObjectInitialValues(arrow_ch_ninja, selectableGunArrowNinja, character_ninja, character_man, txtRemindRightsArrowNinja);
        setThrowableObjectInitialValues(stone_ch_man, selectableGunStoneMan, character_man, character_ninja, txtRemindRightsStoneMan);
        setThrowableObjectInitialValues(stone_ch_ninja, selectableGunStoneNinja, character_ninja, character_man, txtRemindRightsStoneNinja);
        //</editor-fold>

        selectableGunArrowNinja.setImage(yellowCircleArrow);
        selectableGunBombNinja.setImage(yellowCircleBomb);
        selectableGunStoneNinja.setImage(yellowCircleStone);

        //<editor-fold desc="Create TextView Remind Rights for Character_Man"
        txtRemindRightsArrowMan.setText("Kalan Haklar: " + selectableGunArrowMan.getRemainingRights());
        txtRemindRightsArrowMan.setPosX(selectableGunArrowMan.getPosX());
        txtRemindRightsArrowMan.setPosY(selectableGunArrowMan.getPosY());
        gameView.addDrawble(txtRemindRightsArrowMan);
        //</editor-fold>


        Log.i("TAG", "X: " + screenWidth + " Y: " + screenHeight);

        //<editor-fold desc="Set Remind Rights TextView Initial Values"

        setTextViewInitialValues(txtRemindRightsBombMan, "" + selectableGunBombMan.getRemainingRights(), selectableGunBombMan.getPosX() + selectableGunBombMan.getWidth() / 2, selectableGunBombMan.getPosY() + selectableGunBombMan.getHeight() + selectableGunBombMan.getHeight() / 5);
        setTextViewInitialValues(txtRemindRightsArrowMan, "" + selectableGunArrowMan.getRemainingRights(), selectableGunArrowMan.getPosX() + selectableGunArrowMan.getWidth() / 2, selectableGunArrowMan.getPosY() + selectableGunArrowMan.getHeight() + selectableGunArrowMan.getHeight() / 5);
        setTextViewInitialValues(txtRemindRightsStoneMan, "" + selectableGunStoneMan.getRemainingRights(), selectableGunStoneMan.getPosX() + selectableGunStoneMan.getWidth() / 2, selectableGunStoneMan.getPosY() + selectableGunStoneMan.getHeight() + selectableGunStoneMan.getHeight() / 5);
        setTextViewInitialValues(txtRemindRightsBombNinja, "" + selectableGunBombNinja.getRemainingRights(), selectableGunBombNinja.getPosX() + selectableGunBombNinja.getWidth() / 2, selectableGunBombNinja.getPosY() + selectableGunBombNinja.getHeight() + selectableGunBombNinja.getHeight() / 5);
        setTextViewInitialValues(txtRemindRightsArrowNinja, "" + selectableGunArrowNinja.getRemainingRights(), selectableGunArrowNinja.getPosX() + selectableGunArrowNinja.getWidth() / 2, selectableGunArrowNinja.getPosY() + selectableGunArrowNinja.getHeight() + selectableGunArrowNinja.getHeight() / 5);
        setTextViewInitialValues(txtRemindRightsStoneNinja, "" + selectableGunStoneNinja.getRemainingRights(), selectableGunStoneNinja.getPosX() + selectableGunStoneNinja.getWidth() / 2, selectableGunStoneNinja.getPosY() + selectableGunStoneNinja.getHeight() + selectableGunStoneNinja.getHeight() / 5);
        //</editor-fold>

        //<editor-fold desc="Character_Ninja Add Selectable Weapon"

        character_ninja.addSelectableWeapon(selectableGunArrowNinja);
        character_ninja.addSelectableWeapon(selectableGunBombNinja);
        character_ninja.addSelectableWeapon(selectableGunStoneNinja);
        //</editor-fold>

        character_man.setSelectableGun(selectableGunBombMan);
        character_ninja.setSelectableGun(selectableGunStoneNinja);
        //healtBarCrhNinja.setScaleX(healtBarCrhNinja.getScaleX() - 0.06f);


        selectedCharacter = character_man;
        count++;
        triangle = new Triangle(selectedCharacter, triangleImage);

        character_man.setStrength(150);

        powerBar = new PowerBar();
        gameView.addDrawble(powerBar);
        gameView.addDrawble(triangle);
        powerBar.setShouldDraw(false);

        float circleRadius = 25;
        float circleCount = 0;
        for (Building building : buildings) {
            circleCount = circleCountCalculate(building, circleRadius);
            float tempX = building.getPosX() - circleRadius;
            float tempY = building.getPosY() - circleRadius;
            for (int i = 0; i < circleCount; i++) {
                if (tempX + circleRadius > building.getPosX() + building.getWidth()) {
                    tempX = building.getPosX() - circleRadius;
                    tempY += circleRadius + circleRadius;
                    if (tempY + circleRadius > building.getPosY() + building.getHeight()) {
                        Log.i("TAG", "bitti");
                        break;
                    }
                }
                building.addCircle(tempX + circleRadius + circleRadius, tempY + circleRadius + circleRadius, circleRadius);
                Log.i("TAG", "tempx: " + tempX + " tempY: " + tempY);
                tempX += circleRadius + circleRadius;
            }
            for (Building.HitCircle hitCircle : building.circleList) {
                //  gameView.addDrawble(hitCircle.circle);
            }
        }

        Bitmap pauseButtonImage = BitmapFactory.decodeResource(getResources(), R.drawable.pause_button);
        PauseButton pauseButton=new PauseButton(pauseButtonImage);
        pauseButton.setPosX(screenWidth/1.1f);
        pauseButton.setPosY(screenHeight/15f);
        pauseButton.setScaleX(0.3f);
        pauseButton.setScaleY(0.3f);
        gameView.addDrawble(pauseButton);

        switch (gameType) {
            case "Human":
                //<editor-fold desc="GameView onTouchListener">
                gameView.setOnTouchListener(new View.OnTouchListener() {
                    float firstX = 0, firstY = 0, lastX = 0, lastY = 0;
                    float angle = 0;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        float substitution = 0;
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:

                                // Log.i("TAG", "touched down x"+x+" y: "+y);
                                selectedCharacter.getSelectableGun().getGun().setTouchMoving(false);
                                firstX = event.getX();
                                firstY = event.getY();
                                //Tur sırasının kimde olduğunu belirler.
                                if (count % 2 == 0) {
                                    selectedCharacter = character_ninja;
                                } else {
                                    selectedCharacter = character_man;
                                }
                                triangle.setPosByCharacter(selectedCharacter);
                                powerBar.setShouldDraw(true);
                                powerBar.setPosX(firstX);
                                powerBar.setPosY(firstY);
                                powerBar.setLastX(firstX);
                                powerBar.setLastY(firstY);

                                isClickedSelectableGun = false;
                                for (SelectableGun selectableGun : selectableGunList) {
                                    if (setOnClickListenerForSelectableGun((int) firstX, (int) firstY, selectableGun)) {
                                        isClickedSelectableGun = true;
                                        break;
                                    }
                                }
                                pauseButton.setPaused(false);
                                isClickedPauseButton=setOnClickListenerForPauseButton((int) firstX,(int) firstY, pauseButton);
                                Log.i("TAg","basıldı");
                                break;

                            case MotionEvent.ACTION_MOVE:
                                if (!isClickedPauseButton) {
                                    if (!isClickedSelectableGun) {
                                        lastX = event.getX();
                                        lastY = event.getY();
                                        substitution = substitutionCalculate((int) firstX, (int) firstY, (int) lastX, (int) lastY);
                                        angle = (angleCalculate(firstX, firstY, lastX, lastY) + 90) % 360;

                                        if (count % 2 == 0) {
                                            selectedCharacter = character_ninja;
                                            angle = Math.abs(angle - 180);
                                        } else {
                                            selectedCharacter = character_man;
                                            //angle -= 270;
                                        }

                                        powerBar.setLastX(lastX);
                                        powerBar.setLastY(lastY);

                                        System.out.println("Angle: " + angle);
                                        Log.i("TAG", "" + angle);

                                        selectedCharacter.getSelectableGun().getGun().shot((substitution / maxSubstitution) * selectedCharacter.getStrength(), angle, false);
                                        selectedCharacter.getSelectableGun().getGun().setTouchMoving(true);
                                    }
                                }
                                break;

                            case MotionEvent.ACTION_UP:
                                if (!isClickedPauseButton) {
                                    if (!isClickedSelectableGun) {
                                        selectedCharacter.getSelectableGun().getGun().setTouchMoving(false);
                                        lastX = (int) event.getX();
                                        lastY = (int) event.getY();
                                        Log.i("TAG", "first [X=" + firstX + ", Y=" + firstY + "]");
                                        Log.i("TAG", "last [X=" + lastX + ", Y=" + lastY + "]");
                                        substitution = substitutionCalculate((int) firstX, (int) firstY, (int) lastX, (int) lastY);
                                        System.out.println("Power: " + (substitution / maxSubstitution) + " Angle: " + angle);
                                        Log.i("TAG", "hız Touch :" + substitution);
//                        angle = (angleCalculate(firstX, firstY, lastX, lastY) + 90) % 360;
//                                Log.i("TAG", "Açı:" + angle);

                                        powerBar.setShouldDraw(false);

                                        if (substitution > 0) {
                                            if (count % 2 == 0) {
                                                selectedCharacter = character_ninja;
//                                angle = Math.abs(angle - 180);
                                            } else
                                                selectedCharacter = character_man;

                                            if (selectedCharacter.getSelectableGun().getRemainingRights() > 0) {
                                                selectedCharacter._throw(selectedCharacter.getSelectableGun(), (substitution / maxSubstitution) * selectedCharacter.getStrength(), angle);
                                                count++;
                                            } else
                                                Toast.makeText(getApplicationContext(), R.string.out_of_use, Toast.LENGTH_SHORT).show();
                                        }
                                        if (count % 2 == 0)
                                            selectedCharacter = character_ninja;
                                        else
                                            selectedCharacter = character_man;
                                        triangle.setPosByCharacter(selectedCharacter);
                                    }
                                }
                                break;
                        }
                        return true;
                    }
                });
                //</editor-fold>
                break;

            case "Computer":
                gameView.setOnTouchListener(new View.OnTouchListener() {
                    float firstX = 0, firstY = 0, lastX = 0, lastY = 0;
                    float angle = 0;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        float substitution = 0;
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:

                                // Log.i("TAG", "touched down x"+x+" y: "+y);
                                selectedCharacter.getSelectableGun().getGun().setTouchMoving(false);
                                firstX = event.getX();
                                firstY = event.getY();
                                //Tur sırasının kimde olduğunu belirler.
                                if (count % 2 == 0) {
                                    selectedCharacter = character_ninja;
                                } else {
                                    selectedCharacter = character_man;
                                }
                                triangle.setPosByCharacter(selectedCharacter);
                                powerBar.setShouldDraw(true);
                                powerBar.setPosX(firstX);
                                powerBar.setPosY(firstY);
                                powerBar.setLastX(firstX);
                                powerBar.setLastY(firstY);

                                isClickedSelectableGun = false;
                                for (SelectableGun selectableGun : selectableGunList) {
                                    if (setOnClickListenerForSelectableGun((int) firstX, (int) firstY, selectableGun)) {
                                        isClickedSelectableGun = true;
                                        break;
                                    }
                                }
                                isClickedPauseButton=setOnClickListenerForPauseButton((int) firstX,(int) firstY, pauseButton);
                                break;

                            case MotionEvent.ACTION_MOVE:
                                if (!isClickedPauseButton) {
                                    if (!isClickedSelectableGun) {
                                        lastX = event.getX();
                                        lastY = event.getY();
                                        substitution = substitutionCalculate((int) firstX, (int) firstY, (int) lastX, (int) lastY);
                                        angle = (angleCalculate(firstX, firstY, lastX, lastY) + 90) % 360;

                                        if (count % 2 == 0) {
                                            selectedCharacter = character_ninja;
                                            angle = Math.abs(angle - 180);
                                        } else {
                                            selectedCharacter = character_man;
                                            //angle -= 270;
                                        }

                                        powerBar.setLastX(lastX);
                                        powerBar.setLastY(lastY);

                                        System.out.println("Angle: " + angle);
                                        selectedCharacter.getSelectableGun().getGun().shot((substitution / maxSubstitution) * selectedCharacter.getStrength(), angle, false);
                                        selectedCharacter.getSelectableGun().getGun().setTouchMoving(true);
                                    }
                                }
                                break;

                            case MotionEvent.ACTION_UP:
                                if (!isClickedPauseButton) {
                                    if (!isClickedSelectableGun) {
                                        selectedCharacter.getSelectableGun().getGun().setTouchMoving(false);
                                        lastX = (int) event.getX();
                                        lastY = (int) event.getY();
                                        Log.i("TAG", "first [X=" + firstX + ", Y=" + firstY + "]");
                                        Log.i("TAG", "last [X=" + lastX + ", Y=" + lastY + "]");
                                        substitution = substitutionCalculate((int) firstX, (int) firstY, (int) lastX, (int) lastY);
                                        System.out.println("Power: " + (substitution / maxSubstitution) + " Angle: " + angle);
                                        Log.i("TAG", "hız Touch :" + substitution);
//                        angle = (angleCalculate(firstX, firstY, lastX, lastY) + 90) % 360;
//                                Log.i("TAG", "Açı:" + angle);

                                        powerBar.setShouldDraw(false);

                                        if (substitution > 0) {
                                            if (count % 2 == 0) {
                                                selectedCharacter = character_ninja;
//                                angle = Math.abs(angle - 180);
                                            } else
                                                selectedCharacter = character_man;

                                            if (selectedCharacter.getSelectableGun().getRemainingRights() > 0) {
                                                selectedCharacter._throw(selectedCharacter.getSelectableGun(), (substitution / maxSubstitution) * selectedCharacter.getStrength(), angle);
                                                count++;
                                            } else
                                                Toast.makeText(getApplicationContext(), R.string.out_of_use, Toast.LENGTH_SHORT).show();
                                        }
                                        if (count % 2 == 0)
                                            selectedCharacter = character_ninja;
                                        else
                                            selectedCharacter = character_man;
                                        triangle.setPosByCharacter(selectedCharacter);
                                    }
                                }
                                break;
                        }
                        return true;
                    }
                });

                break;
            default:
                break;
        }


        gameThread = new Thread(() ->
        {
            while (true) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ignored) {
                }
                gameView.update();
                gameView.postInvalidate();//İlk fırsatta draw fonksiyonunu çağırıyor.
            }
        });
        gameThread.start();
        Log.i("TAG", "Bina sayısı :" + String.valueOf(buildings.size()));
        Log.i("TAG", "silah sayısı :" + selectableGunList.size());
    }

    //<editor-fold desc="Circle Count Calculate">

    /**
     * @param building'in içine ne kadar yuvarlak konulabileceğini hesaplar.
     * @param building
     * @param radius
     * @return
     */
    private float circleCountCalculate(Building building, float radius) {
        float result = (building.getWidth() / radius) * (building.getHeight() / radius);
        Log.i("TAG", "width: " + building.getWidth() + " height: " + building.getHeight() + " radius: " + radius + " result: " + result);
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="Substitution Calculate">

    /**
     * Bu fonksiyon 2 nokta arasındaki yer değiştirmeyi hesaplar.
     * Atılacak cismin hızını belirlemek için kullanılır.
     * Buradan gelecek sonuç karakterin atış gücü ile çarpılır ve
     * Cismin hızı belirlenmiş olur.
     *
     * @param firstX
     * @param firstY
     * @param lastX
     * @param lastY
     * @return
     */
    public float substitutionCalculate(int firstX, int firstY, int lastX, int lastY) {
        float substitution = 0;
        float moveX = Math.abs(lastX - firstX);
        float moveY = Math.abs(lastY - firstY);
        substitution = (float) Math.sqrt(moveX * moveX + moveY + moveY);
        Log.i("hız", "hızFuc : " + String.valueOf(substitution));
        return substitution;
    }
    //</editor-fold>

    //<editor-fold desc="Angle Calculate">

    /**
     * Bu fonksiyon atılacak cismin eğim açısını hesaplamaktadır.
     *
     * @return
     */
    public float angleCalculate(double firstX, double firstY, double lastX, double lastY) {
        double angle = Math.atan2((lastX - firstX), (lastY - firstY)) * 180 / Math.PI;
        if (angle < 0) {
            return (float) (360 + angle);
        } else {
            return (float) angle;
        }
    }

    //</editor-fold>

    //<editor-fold desc="Max Substitution Calculate">

    /**
     * Oyundaki maximum yer değiştirmenın ne kadar
     * olduğunu hesaplar.
     * Atış yaparken bu fonksiyondan yararlanılır.
     *
     * @param moveX
     * @param moveY
     * @return
     */
    public float maxSubstitutionCalculate(float moveX, float moveY) {
        moveX = Math.abs(screenWidth - 0);
        moveY = Math.abs(screenHeight - 0);
        return (float) Math.sqrt(moveX * moveX + moveY + moveY);
    }

    //</editor-fold >

    //<editor-fold desc="Add Building">

    /**
     * Oyuna yeni bina eklenmesini sağlar
     * Son Eklenen binanın sağına eklenir
     * İlk oluşturulan bina ile aynı {@link Building#getPosY()}'de olur
     *
     * @param buildingNew
     */
    public Building addBuilding(Building buildingNew, float posX) {
        //Eğer oyuna ilk bina eklenmişsse yeni bina oluştur ve ekrana ekle
        buildingNew.setPosX(posX);
        buildingNew.setPosY(screenHeight / 7 * 6 - buildingNew.getHeight());
        gameView.addDrawble(buildingNew);
        buildings.add(buildingNew);
        return buildingNew;
    }

    //</editor-fold>


    /**
     * Silah seçme butonlarından
     * birine tıklandığında neler olacağını belirler.
     * Karakter yeni seçilen silahı kulllanır.
     *
     * @param x
     * @param y
     * @param selectableGun
     * @return
     */
    public boolean setOnClickListenerForSelectableGun(int x, int y, SelectableGun selectableGun) {
        if (x >= selectableGun.getPosX() && x < (selectableGun.getPosX() + selectableGun.getWidth())//Eğer tıklanılan yerde silah seçme butonu varsa.
                && y >= selectableGun.getPosY() && y < (selectableGun.getPosY() + selectableGun.getHeight())) {
            if (selectableGun.getCharacter() == selectedCharacter) {
                if (selectableGun.getRemainingRights() > 0) {
                    selectedCharacter.setSelectableGun(selectableGun);//Seçili karakterin elindeki kullanacağı silahı değiştirir.
                } else
                    Toast.makeText(getApplicationContext(), R.string.out_of_use, Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), R.string.its_not_your_turn, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean setOnClickListenerForPauseButton(int x,int y,PauseButton pauseButton)
    {
        if (x >= pauseButton.getPosX() && x < (pauseButton.getPosX() + pauseButton.getWidth())//Eğer tıklanılan yerde silah seçme butonu varsa.
                && y >= pauseButton.getPosY() && y < (pauseButton.getPosY() + pauseButton.getHeight())) {
            if (pauseButton.isPaused())
              //  pauseButton.setPaused(false);

               // pauseButton.setPaused(true);
            Toast.makeText(getApplicationContext(),"Oyun Durudurldu",Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }

    /**
     * Oluşturulan atılavbilir cisimlere oyunla ilgili gerekli parametreler ekleniyor.
     */
    public void setThrowableObjectInitialValues(ThrowableObject throwableObject, SelectableGun selectableGun, Character character, Character enemyCharacter, TextView txtRemindRights) {
        if (character.getScaleX() > 0)//Eğer karakterin yüzü sağa tarafa dönük ise
        {
            throwableObject.setPosX(character.getPosX() + screenWidth / 17);
            throwableObject.setPosY(character.getPosY() + screenHeight / 12);
        } else {
            throwableObject.setPosX(character.getPosX() - screenWidth / 11);
            throwableObject.setPosY(character.getPosY() + screenHeight / 15);
        }
        throwableObject.setSelectableGun(selectableGun);
        throwableObject.buildingList = buildings;//Oyundaki binaların listesi alınıyor.
        throwableObject.setThisCharacter(character);//Cismin hangi karaktere ait olduğu belirleniyor.
        throwableObject.setEnemyCharacter(enemyCharacter);
        throwableObject.setGameView(gameView);//Oyun ekranının referansı ekleniyor.
        throwableObject.characters = characters;//Oyundaki karakterlerin listesi ekleniyor.
        throwableObject.setShouldDraw(false);//Oyuna ilk eklendiğinde çizilemez yapılıyor.
        //Eğer karakter bu silahı kullanacaksa bu cisim çizilebilir olacak.
        gameView.addDrawble(throwableObject);//Bu cisim oyun ekranına ekleniyor.
        throwableObject.setShouldDraw(false);//Nesne ekranda görünür yapılıyor.
        selectableGun.setTxtRemainingRights(txtRemindRights);
        throwableObject.setMainActivity(this);
    }

    /**
     * Oluşturulan texView nesnesine konum ve içerik bilgileri ekleniyor..
     */
    public void setTextViewInitialValues(TextView textView, String text, float posX, float posY) {
        textView.setPosX(posX);
        textView.setPosY(posY);
        textView.setText(text);
        gameView.addDrawble(textView);
        textView.setTextSize(50);
    }
}
