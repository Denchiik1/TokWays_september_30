package com.example.tokways_september_30;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        EditText editTextClimate = findViewById(R.id.editTextClimate);
        EditText editTextEconomy = findViewById(R.id.editTextEconomy);
        EditText editTextArchitecture = findViewById(R.id.editTextArchitecture);

        Button nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получите значения из полей ввода
                String climate = editTextClimate.getText().toString();
                String economyStr = editTextEconomy.getText().toString();
                String architecture = editTextArchitecture.getText().toString();

                double economy = 0.0;
                try {
                    economy = Double.parseDouble(economyStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(InputActivity.this, "Некорректное значение экономики", Toast.LENGTH_SHORT).show();
                    return; // Выйдите из обработчика нажатия кнопки
                }

                String cityName = findMatchingCity(climate, economyStr, architecture);

                Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                intent.putExtra("CITY_NAME", cityName);
                startActivity(intent);
            }
        });
    }

    private String findMatchingCity(String climate, String economy, String architecture) {
        SQLiteDatabase database = new DatabaseHelper(this).getReadableDatabase();
        String cityName = "";

        try {
            String query = "SELECT " + DatabaseHelper.COLUMN_NAME + " FROM " + DatabaseHelper.TABLE_CITIES +
                    " WHERE " + DatabaseHelper.COLUMN_CLIMATE + " = ?" +
                    " AND " + DatabaseHelper.COLUMN_ECONOMY + " >= ?" +
                    " AND " + DatabaseHelper.COLUMN_ARCHITECTURE + " = ?";

            Cursor cursor = database.rawQuery(query, new String[]{climate, economy, architecture});

            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME);
                cityName = cursor.getString(columnIndex);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close();
        }

        return cityName;
    }


}
