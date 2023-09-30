package com.example.tokways_september_30;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cities.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CITIES = "cities";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CLIMATE = "climate";
    public static final String COLUMN_ECONOMY = "economy";
    public static final String COLUMN_ARCHITECTURE = "architecture";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTableQuery = "CREATE TABLE " + TABLE_CITIES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_CLIMATE + " TEXT NOT NULL, " +
                COLUMN_ECONOMY + " TEXT NOT NULL, " +
                COLUMN_ARCHITECTURE + " TEXT NOT NULL);";
        database.execSQL(createTableQuery);

        fillDatabaseWithData(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void fillDatabaseWithData(SQLiteDatabase database) {
        addCity(database, "Волгоград", "Южный", "44000", "Великая Отечественная война");
        addCity(database, "Выборг", "Умеренный морской", "70000", "Европейская");
        addCity(database, "Москва", "Умеренно континентальный", "73000", "Центр");
        addCity(database, "Санкт-Петербург", "Атлантико-континентальной", "62000", "Европейская");
        addCity(database, "Великий Новгород", "Умеренно континентальный", "72000", "Древнерусская");

    }

    private void addCity(SQLiteDatabase database, String name, String climate, String economy, String architecture) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CLIMATE, climate);
        values.put(COLUMN_ECONOMY, economy);
        values.put(COLUMN_ARCHITECTURE, architecture);

        database.insert(TABLE_CITIES, null, values);
    }

    public String findMatchingCity(String climate, double economy, String architecture) {
        SQLiteDatabase database = this.getReadableDatabase();
        String cityName = "";

        try {
            String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_CITIES +
                    " WHERE " + COLUMN_CLIMATE + " = ?" +
                    " AND " + COLUMN_ECONOMY + " = ?" +
                    " AND " + COLUMN_ARCHITECTURE + " = ?";

            Cursor cursor = database.rawQuery(query, new String[]{climate, String.valueOf(economy), architecture});

            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
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
