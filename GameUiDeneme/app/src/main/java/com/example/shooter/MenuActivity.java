package com.example.shooter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button play_compueter_button;
    Button play_human_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        play_compueter_button = findViewById(R.id.play_against_computer);
        play_human_button = findViewById(R.id.play_aganist_human);

        play_human_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(MenuActivity.this, MainActivity.class);
                intentMain.putExtra("gameType", "Human");
                startActivity(new Intent(intentMain));
                MenuActivity.this.finish();
            }
        });

        play_compueter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(MenuActivity.this, MainActivity.class);
                intentMain.putExtra("gameType", "Computer");
                startActivity(new Intent(intentMain));
                MenuActivity.this.finish();
            }
        });
    }
}
