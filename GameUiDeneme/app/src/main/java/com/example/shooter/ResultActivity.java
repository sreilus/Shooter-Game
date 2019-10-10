package com.example.shooter;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    TextView txtResult;
    Button btnStartActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        txtResult=findViewById(R.id.txtResult);
        btnStartActivity=findViewById(R.id.btnStartActivity);

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("data");
        String gameType=extras.getString("gameType");
        txtResult.setText("Oyunu "+value+" kazandı!");
        txtResult.setTypeface(null, Typeface.BOLD);

        btnStartActivity.setText("Yeniden Oyna");
        btnStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MenuActivity.class);
                intent.putExtra("gameType",gameType);
                startActivity(intent);
                finish();
            }
        });
    }
}
