package com.example.animalpedia;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * en
 * This class represents an animal. An animal has a unique animalID, a class where it belongs, a continent where you
 * can find that animal (in this case an animal has only one continent where it belongs), a name, some details, an image
 * and a youtube link for each animal for more information.
 * -------------------------------------
 * el
 * Κλάση Animal που αποθηκεύει τα στοιχεία ενός ζώου.
 */


public class Animal implements Parcelable {
    private String animalID;
    private String animalClass;
    private String continent;
    private String nameAN;
    private String details;
    private byte[] image;
    private String link;
    private boolean favorite;

    public Animal(){

    }

    public Animal(String animalID, String continent, String animalClass, String name, String details, String link, byte[] image, boolean favorite){
        this.animalClass = animalClass;
        this.animalID = animalID;
        this.nameAN = name;
        this.details = details;
        this.image = image;
        this.link = link;
        this.continent = continent;
        this.favorite = favorite;
    }

    /*
     * the Parcelable methods that been used is for the transition of the class object
     * from one activity to another
     */

    /*
     * Οι μέθοδοι του Parcelable χρησιμοποιήθηκαν για την μετάβαση ενός αντικειμένουν της
     * κλάσης από activity σε activity
     */
    protected Animal(Parcel in) {
        animalID = in.readString();
        animalClass = in.readString();
        continent = in.readString();
        nameAN = in.readString();
        details = in.readString();
        image = in.createByteArray();
        link = in.readString();
        favorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(animalID);
        dest.writeString(animalClass);
        dest.writeString(continent);
        dest.writeString(nameAN);
        dest.writeString(details);
        dest.writeByteArray(image);
        dest.writeString(link);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };


    /*
     * Getters and Setters for the manipulation of the fields in this class
     */

    /*
     * Getters/ Setters για τον χειρισμό των πεδίων της κλάσης
     */
    public String getAnimalID() {
        return this.animalID;
    }

    public byte[] getImage() {
        return this.image;
    }

    public String getAnimalClass() {
        return this.animalClass;
    }

    public String getDetails() {
        return this.details;
    }

    public String getLink() {
        return this.link;
    }

    public String getName() {
        return this.nameAN;
    }

    public String getContinent(){
        return this.continent;
    }

    public boolean getFavorite() {
        return  this.favorite;
    }

    public void setAnimalID(String animalID) {
        this.animalID = animalID;
    }

    public void setAnimalClass(String animalClass) {
        this.animalClass = animalClass;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setName(String name) {
        this.nameAN = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }
}
