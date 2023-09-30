package com.example.tokways_september_30;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String cityName = intent.getStringExtra("CITY_NAME");

        TextView resultCityName = findViewById(R.id.resultCityName);
        TextView cityDescriptionTextView = findViewById(R.id.cityDescription);

        resultCityName.setText(cityName);

        String cityDescription = getCityDescription(cityName);
        cityDescriptionTextView.setText(cityDescription);

        Button buttonRestart = findViewById(R.id.buttonRestart);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private String getCityDescription(String cityName) {
        return "Описание города " + cityName;
    }
}
