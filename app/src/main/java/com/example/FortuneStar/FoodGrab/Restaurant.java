package com.example.FortuneStar.FoodGrab;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Daniel on 27/05/2017.
 *
 *  Entity class for holding a single result of a Google Place API request.
 *
 *  Only to be used as a object. Put any functionality into other classes
 */

public class Restaurant {
    private String id; // Google's ID
    private String place_id; // Place ID - will be used to get details about the restaurant
    private String name;
    private boolean open_now; // Open at the current time
    private double rating; // Rating out of 5 stars
    private String vicinity; // Address of restaurant
    private ArrayList<Bitmap> images;

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen_now() {
        return open_now;
    }

    public void setOpen_now(boolean open_now) {
        this.open_now = open_now;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
