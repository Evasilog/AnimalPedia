package com.example.animalpedia;

import android.graphics.Bitmap;

public class Animal{
    private String animalID;
    private String animalClass;
    private String continent;
    private String name;
    private String details;
    private Bitmap image;
    private String link;
    public Animal(){

    }

    public Animal(String animalID, String continent, String animalClass, String name, String details, String link, Bitmap image){
        this.animalClass = animalClass;
        this.animalID = animalID;
        this.name = name;
        this.details = details;
        this.image = image;
        this.link = link;
        this.continent = continent;
    }

    public String getAnimalID() {
        return this.animalID;
    }

    public Bitmap getImage() {
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

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
