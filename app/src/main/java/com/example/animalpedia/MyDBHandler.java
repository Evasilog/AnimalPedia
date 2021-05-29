package com.example.animalpedia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Κλάση διαχείρισης της εξωτερικής βάσης δεδομένων
 */

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Animal.db";


    MyDBHandler(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        //το path της βάσης
        String fileName = context.getDatabasePath(DATABASE_NAME).getPath();

        // έλεγχος ύπαρξης της βάσης
        boolean checkDatabase = (new File(fileName)).exists();
        if (!checkDatabase) {
            try{
                InputStream input = context.getAssets().open("database/" + DATABASE_NAME);
                OutputStream output = new FileOutputStream(fileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                output.flush();
                output.close();
                input.close();

            }
            catch (IOException ignored){

            }
        }
    }

    public List<Animal> getAnimals(String animalCategory, int mode) {
        //mode == 1 σημαίνει οτι θα εμφανίσει τα ζώα ανάλογα με την κατηγορία που βρίσκεται
        //mode == 2 σημαίνει ότι θα επιλέξει όλα τα ζώα
        //mode == 3 σημαίνει ότι θα επιλέξει τα αγαπημένα ζώα
        //mode != από τις παραπάνω επιλογές σημαίνει ότι θα επιλέξει τα ζώα ανάλογα με την ήπειρο που βρίσκονται

        //αρχικοποίηση της βάσης
        SQLiteDatabase db = this.getWritableDatabase();

        //εκτέλεσης του ερωτήματος και αποθήκευση αποτελέσματος στο cursor
        Cursor cursor;
        if(mode == 1) {
            cursor = db.rawQuery("SELECT *" + " FROM Animal" + " WHERE Class ='" + animalCategory + "'", null);
        } else if(mode == 2){
            cursor = db.rawQuery("SELECT *" + " FROM Animal", null);
        } else if(mode == 3){
            cursor = db.rawQuery("SELECT *" + " FROM Animal" + " WHERE Favorite = '1'", null);
        } else{
            cursor = db.rawQuery("SELECT *" + " FROM Animal" + " WHERE Continent ='" + animalCategory + "'", null);
        }


        List<Animal> animalList = new ArrayList<>();


        //λήψη πληροφορίων απο τη βάση για τη δημιουργία ενός ζώου και αποθήκευση του στο arrayList
        while (cursor.moveToNext()) {
            Animal animal = new Animal();
            animal.setAnimalID(cursor.getString(0));
            animal.setContinent(cursor.getString(1));
            animal.setAnimalClass(cursor.getString(2));
            animal.setName(cursor.getString(3));
            animal.setDetails(cursor.getString(4));
            animal.setLink(cursor.getString(5));
            animal.setImage(cursor.getBlob(6));
            animal.setFavorite(cursor.getInt(7)>0);
            animalList.add(animal);
        }
        cursor.close();

        return animalList;
    }


    /**
     * στη μέθοδο αυτή χειριζόμαστε την κατάσταση που βρίσκεται το ζωό.
     * Αν ο χρήστης το έχει τοποθετήσει στα αγαπημένα η καταστασή του θα γίνει 1 στη βάση
     * ειδάλλως αν το έχει αφαιρέσει θα γίνει 0
     * @param animal
     */
    public void changeFavoriteState (Animal animal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Favorite", animal.getFavorite());
        db.update("Animal", values,"idAnimal=" + animal.getAnimalID(),null);
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
