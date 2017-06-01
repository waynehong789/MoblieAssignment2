package com.example.FortuneStar.FoodGrab;

import android.graphics.Bitmap;

/**
 * Created by pdant on 31/05/2017.
 *
 *  Holds the information of the menu items
 */

public class MenuListItem {
    private String name;
    private String description;
    private String id;
    private double price;
    private String imageID;

    public MenuListItem(){

    }

    public MenuListItem(String name, String description, String id, double price, String imageID){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }
}
