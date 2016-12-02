package com.oldzi.weeeather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.oldzi.weeeather.city_saved.UserCity;
import com.oldzi.weeeather.city_weather.data.City;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oldzi on 12.09.2016.
 */
public class CitiesDatabaseHelper extends SQLiteOpenHelper {

    public static final String LOG = "CDH";
    private static final String DATABASE_NAME = "citiesdbVOL3";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_CITIES = "citiestest";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_JSON = "json";
    public static final String COLUMN_ORDERINDEX = "orderindex";
    public static final String COLUMN_TIMES_CLICKED = "timesclicked";

    private static final String[] COLUMNS = {COLUMN_ID, COLUMN_NAME, COLUMN_TIMESTAMP, COLUMN_JSON, COLUMN_ORDERINDEX, COLUMN_TIMES_CLICKED};
    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_CITIES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_TIMESTAMP + " text not null,"
            + COLUMN_JSON + " text not null,"
            + COLUMN_ORDERINDEX + " int, "
            + COLUMN_TIMES_CLICKED + " int "
            + ");";


    public CitiesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.d(LOG, " in onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
        Log.d(LOG, "table dropped onUpgrade()");
        this.onCreate(db);

    }

    public void addCity(City city) {
        if (getCityByName(city.getName()) != null) {
            updateCity(city);

        } else {
            Log.d(LOG, city.getName());

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, city.getName());
            values.put(COLUMN_TIMESTAMP, String.valueOf(new Date(System.currentTimeMillis())));
            values.put(COLUMN_JSON, new Gson().toJson(city));
            values.put(COLUMN_TIMES_CLICKED, 0);
            long ider = db.insert(TABLE_CITIES, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values

            values.clear();
            values.put(COLUMN_ORDERINDEX, (int) ider);

            db.update(TABLE_CITIES, //table
                    values, // column/value
                    COLUMN_ID + " = ?", // selections
                    new String[]{String.valueOf(ider)}); //selection args
        }
    }


    public City getCityById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_CITIES, // a. table
                        COLUMNS, // b. column names - IN THIS CASE ALL COLS
                        COLUMN_ID + " = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String cityJson = cursor.getString(3); // 4th string is json
                City city = new Gson().fromJson(cityJson, City.class);
                cursor.close();
                return city;
            } else {
                Log.d(LOG, "City not found in getCityById()");
                cursor.close();
                return null;
            }

        } else {
            Log.d(LOG, "Cursor null");
            return null;
        }


    }

    public City getCityByName(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_CITIES, // a. table
                        COLUMNS, // b. column names
                        COLUMN_NAME + " like ?", // c. selections
                        new String[]{name}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit


        if (cursor != null) {
            if (cursor.moveToFirst() != false) {
                // getString(3) - it's fourth column containing json
                String cityJson = cursor.getString(3);
                City city = new Gson().fromJson(cityJson, City.class);
                return city;
            } else {
                Log.d(LOG, "City not found in getCityByName()");
                return null;
            }
        } else {
            Log.d(LOG, "Cursor null");
            return null;
        }
    }

    // Get All Books
    public List<City> getAllCities() {
        List<City> cities = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CITIES + " ORDER BY " + COLUMN_ORDERINDEX;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        City city = null;
        if (cursor.moveToFirst()) {
            do {
                Log.d(LOG, "City order is " + cursor.getString(4) + " and id is " + cursor.getString(0) + " and times clicked is " + cursor.getString(5));
                String cityJson = cursor.getString(3);
                city = new Gson().fromJson(cityJson, City.class);
                cities.add(city);
            } while (cursor.moveToNext());
        }

        Log.d(LOG, "There are " + cities.size() + " from getAllCities() method");
        return cities;
    }

    public List<UserCity> getUserCities() {
        List<UserCity> cities = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CITIES + " ORDER BY " + COLUMN_ORDERINDEX;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        UserCity userCity = null;
        if (cursor.moveToFirst()) {
            do {
                userCity = new UserCity();
                Log.d(LOG, "City order is " + cursor.getString(4) + " and id is " + cursor.getString(0) + " and times clicked is " + cursor.getString(5));
                userCity.setId(cursor.getInt(0));
                userCity.setName(cursor.getString(1));
                City city = new Gson().fromJson(cursor.getString(3), City.class);
                userCity.setCurrentTemperature(city.getDays().get(0).getHours().get(0).getTemperature());
                userCity.setOrderPosition(cursor.getInt(4));
                Log.d(LOG, "City order is " + cursor.getString(4) + " and id is " + cursor.getString(0));
                cities.add(userCity);

            } while (cursor.moveToNext());
        }
        return cities;
    }


    public int countCities() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM " + TABLE_CITIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            count = Integer.parseInt(cursor.getString(0));
        }

        Log.d(LOG, count + " Cities");
        return count;
    }


    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
        Log.d(LOG, "table dropped");
        // create fresh table
        this.onCreate(db);
    }

    public int updateCity(City city) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_JSON, new Gson().toJson(city)); // get title

        int i = db.update(TABLE_CITIES, //table
                values, // column/value
                COLUMN_NAME + " = ?", // selections
                new String[]{city.getName()}); //selection args

        db.close();
        Log.d(LOG, "Updated city " + city.getName());
        return i;
    }

    public void updateTimesClicked(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_CITIES + " SET timesclicked = (timesclicked + 1) WHERE _id == " + id + ";";
        db.execSQL(query);
        db.close();

        Log.d(LOG, "Updated city with id" + id);
    }

    public int changeOrder(int startingPosition, int endingPosition) {
        int start = startingPosition + 1;
        int end = endingPosition + 1;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDERINDEX, 0);

        db.update(TABLE_CITIES, //table
                values, // column/value
                COLUMN_ORDERINDEX + " = ?", // selections
                new String[]{String.valueOf(start)}); //selection args
        String query;
        //TODO:
        // work it outtttt!!!!
        if (startingPosition < endingPosition) {
            query = "UPDATE " + TABLE_CITIES + " SET orderindex = (orderindex - 1) WHERE orderindex <= " + end + " AND orderindex > " + start + ";";
        } else {
            query = "UPDATE " + TABLE_CITIES + " SET orderindex = (orderindex + 1) WHERE orderindex >= " + end + " AND orderindex < " + start + ";";
        }
        db.execSQL(query);

        values.clear();
        values.put(COLUMN_ORDERINDEX, end);

        int i = db.update(TABLE_CITIES, //table
                values, // column/value
                COLUMN_ORDERINDEX + " = ?", // selections
                new String[]{"0"}); //selection args
        db.close();
        return i;
    }

    public void deleteCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CITIES,
                COLUMN_NAME + " = ?",
                new String[]{city.getName()});

        db.close();
        Log.d(LOG, city.getName());
    }
}