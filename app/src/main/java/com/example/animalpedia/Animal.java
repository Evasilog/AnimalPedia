package com.example.animalpedia;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Animal implements Parcelable {
    private String animalID;
    private String animalClass;
    private String continent;
    private String name;
    private String details;
    private byte[] image;
    private String link;

    public Animal(){

    }

    public Animal(String animalID, String continent, String animalClass, String name, String details, String link, byte[] image){
        this.animalClass = animalClass;
        this.animalID = animalID;
        this.name = name;
        this.details = details;
        this.image = image;
        this.link = link;
        this.continent = continent;
    }


    protected Animal(Parcel in) {
        animalID = in.readString();
        animalClass = in.readString();
        continent = in.readString();
        name = in.readString();
        details = in.readString();
        image = in.createByteArray();
        link = in.readString();
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
        return this.name;
    }

    public String getContinent(){
        return this.continent;
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
        this.name = name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(animalID);
        dest.writeString(animalClass);
        dest.writeString(continent);
        dest.writeString(name);
        dest.writeString(details);
        dest.writeByteArray(image);
        dest.writeString(link);
    }
}
