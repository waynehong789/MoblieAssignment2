package com.FortuneStar.FoodGrab;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Daniel on 27/05/2017.
 *
 *  Entity class for holding a single result of a Google Place API request.
 *
 *  Only to be used as a object. Put any functionality into other classes
 */

public class Restaurant implements Parcelable {


    private String id; // Google's ID
    private String place_id; // Place ID - will be used to get details about the restaurant
    private String name;
    private boolean open_now; // Open at the current time
    private double rating; // Rating out of 5 stars
    private String vicinity; // Address of restaurant
    private ArrayList<Bitmap> images;

    private String address;
    private String contact;
    private String description;

    // Location data
    private Location location;
    private Location viewportNE;
    private Location viewportSW;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getViewportNE() {
        return viewportNE;
    }

    public void setViewportNE(Location viewportNE) {
        this.viewportNE = viewportNE;
    }

    public Location getViewportSW() {
        return viewportSW;
    }

    public void setViewportSW(Location viewportSW) {
        this.viewportSW = viewportSW;
    }

    public Restaurant() {

    }

    /**
     * Parcel constructor to be called after receiving parcel through intent. This will be used by
     * the parcel creator.
     *
     * @param source parcel that has been sent through intent
     */
    public Restaurant(Parcel source){
        // Reconstruct parcel
        id = source.readString();
        place_id = source.readString();
        name = source.readString();
        vicinity = source.readString();
        address = source.readString();
        contact = source.readString();
        description = source.readString();
        open_now = source.readByte() != 0;
        rating = source.readDouble();
        location = Location.CREATOR.createFromParcel(source);
        viewportNE = Location.CREATOR.createFromParcel(source);
        viewportSW = Location.CREATOR.createFromParcel(source);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Create a parcel to be passed to another activity
        dest.writeString(id);
        dest.writeString(place_id);
        dest.writeString(name);
        dest.writeString(vicinity);
        dest.writeString(address);
        dest.writeString(contact);
        dest.writeString(description);
        dest.writeByte((byte) (open_now ? 1 : 0));
        dest.writeDouble(rating);
        location.writeToParcel(dest, flags);
        viewportNE.writeToParcel(dest, flags);
        viewportSW.writeToParcel(dest, flags);
        // MUST MATCH ORDER OF THE CONSTRUCTOR
    }

    public static final Parcelable.Creator<Restaurant> CREATOR
            = new Parcelable.Creator<Restaurant>() {
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
