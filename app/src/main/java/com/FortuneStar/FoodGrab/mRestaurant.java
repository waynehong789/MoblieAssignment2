package com.FortuneStar.FoodGrab;

/**
 * Created by Wayne on 2017/6/13.
 */

public class mRestaurant {
    private String address, description,id,name,phone,price,type;

    public mRestaurant() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public mRestaurant(String address, String description, String id, String name, String phone, String price, String type) {
        this.address = address;
        this.description = description;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.type = type;
    }

}
