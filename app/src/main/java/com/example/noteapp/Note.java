package com.example.noteapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

public class Note {
    @SerializedName("Id")
    private int id;
    @SerializedName("Description")
    private String description;
    @SerializedName("Photo")
    private String photo;

    public Note(int id, String description, String photo) {
        this.id = id;
        this.description = description;
        this.photo = photo;
    }

    public Note(String description, String photo) {
        this.description = description;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Bitmap getPhotoData(){
        if (getPhoto()!=null){
            byte[] data = Base64.decode(getPhoto(),Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(data,0,data.length);
        }
        else return null;
    }
}
