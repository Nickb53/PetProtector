package edu.miracostacollege.cs134.petprotector.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = DBHelper.class.getSimpleName();

    //Constants for table values
    public static final String DATABASE_NAME = "PetProtector";
    public static final String DATABASE_TABLE = "Pets";
    public static final String FIELD_PRIMARY_KEY = "_id";
    public static final String FIELD_DETAILS = "details";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_IMAGEURI = "uri";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        //execute our create table statement
        String sqlString = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "("
                + FIELD_PRIMARY_KEY + " INTEGER PRIMARY KEY,"
                + FIELD_DETAILS + " TEXT,"
                + FIELD_PHONE + " TEXT,"
                + FIELD_NAME + " TEXT,"
                + FIELD_IMAGEURI + " TEXT" + ")";


        //log the sql String
        Log.i(TAG, sqlString);

        db.execSQL(sqlString);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addPet(Pet pet){
        String name = pet.getName();
        String details = pet.getDetails();
        String phone = pet.getPhone();
        String imageUri = pet.getImageURI().toString();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, name);
        values.put(FIELD_DETAILS, details);
        values.put(FIELD_PHONE, phone);
        values.put(FIELD_IMAGEURI, imageUri);

        int id = (int) db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public List getAllPets(){
        List<Pet> allPets = new ArrayList<>();

        //Fill it from the database
        SQLiteDatabase db = this.getReadableDatabase();
        //Make a query to extract everything!
        //Query results in SQLite are called Cursor objects
        Cursor cursor = db.query(DATABASE_TABLE,
                new String[]{FIELD_PRIMARY_KEY, FIELD_DETAILS, FIELD_PHONE, FIELD_NAME, FIELD_IMAGEURI},
                null,
                null,
                null,
                null,
                null);

        //Loop through the cursor results, one at a time
        //Create Task objects and add them to the List
        //First determine if there are results
        if (cursor.moveToFirst()){
            do{
                //name details phone URI
                Uri petUri = Uri.parse(cursor.getString(4));

                //name details phone uri
                Pet pet = new Pet(cursor.getString(3),
                        cursor.getString(1),
                        cursor.getString(2),
                        petUri);
                allPets.add(pet);
            } while (cursor.moveToNext());
        }
        //Remember to close the Cursor and the database
        cursor.close();
        db.close();

        //Return the filled list
        return allPets;
    }

    public void deleteAllPets() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }
}
