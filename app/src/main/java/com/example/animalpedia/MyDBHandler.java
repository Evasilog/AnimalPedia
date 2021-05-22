package com.example.animalpedia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Animal12.db";

    private static final String COLUMN_IMAGE = "Image";


    MyDBHandler(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        // Path where the database will be copied
        String outFileName = context.getDatabasePath(DATABASE_NAME).getPath();
        // Check if the database exists before copying
        boolean initialiseDatabase = (new File(outFileName)).exists();
        // If the database doesn't exist, copy it to the specified path directory
        if (!initialiseDatabase) {
            try{
                InputStream mInput = context.getAssets().open("database/" + DATABASE_NAME);
                OutputStream mOutput = new FileOutputStream(outFileName);
                byte[] mBuffer = new byte[1024];
                int mLength;
                while ((mLength = mInput.read(mBuffer)) > 0) {
                    mOutput.write(mBuffer, 0, mLength);
                }
                mOutput.flush();
                mOutput.close();
                mInput.close();

            }
            catch (IOException e){

            }
        }
    }

    public List<Animal> getAnimalCategory(String animalCategory, int mode) {
        // mode == 1 σημαίνει οτι θα εμφανίσει τα ζώα ανάλογα με την κλάση που είναι
        // mode != 1 σημαίνει ότι θα επιλέξει ζώα ανάλογα με την ήπειρο που βρίσκονται

        // Initialize the database
        SQLiteDatabase db = this.getWritableDatabase();
        // Execute the query and put the result inside a cursor
        Cursor cursor;
        if(mode == 1) {
            cursor = db.rawQuery("SELECT *" + " FROM Animal" + " WHERE Class ='" + animalCategory + "'", null);
        }else if(mode == 2){
            cursor = db.rawQuery("SELECT *" + " FROM Animal", null);
        }else{
            cursor = db.rawQuery("SELECT *" + " FROM Animal" + " WHERE Continent ='" + animalCategory + "'", null);
        }
        // Initialize an empty ArrayList to store the result of the query
        List<Animal> animalList = new ArrayList<>();
        // Initialize each beer and add it to the ArrayList
        while (cursor.moveToNext()) {
            Animal animal = new Animal();
            animal.setAnimalID(cursor.getString(0));//Animal ID
            animal.setContinent(cursor.getString(1));//Animal Continent
            animal.setAnimalClass(cursor.getString(2)); // Animal Class
            animal.setName(cursor.getString(3)); // Animal Name
            animal.setDetails(cursor.getString(4)); // Animal Details
            animal.setLink(cursor.getString(5)); // Animal link
            animal.setImage(getImage(cursor.getString(0))); // Animal image
           // animal.setImage(getImage("1")); // Animal image
            // Add the beer to the beers list
            animalList.add(animal);
        }
        cursor.close();
        // Return the list containing all the beers
        return animalList;
    }


    public byte[] getImage(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        byte[] img = new byte[0];
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_IMAGE + " FROM Animal" + " WHERE idAnimal ='" + id + "'", null);
        if(cursor.moveToNext()){
            img = cursor.getBlob(0);
        }
        return  img;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Animal");
        onCreate(db);
    }


}
