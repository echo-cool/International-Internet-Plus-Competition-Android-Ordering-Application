package com.example.restaurantclient.Models;

import android.os.Parcelable;

import java.io.Serializable;

import cn.leancloud.AVObject;
import cn.leancloud.AVParcelableObject;
import cn.leancloud.annotation.AVClassName;

@AVClassName("Cuisine")
public class Cuisine extends AVObject implements Serializable {
    public static final Parcelable.Creator CREATOR = AVParcelableObject.AVObjectCreator.instance;
    public static final String NAME = "Name";
    public static final String DESCRIPTION = "Description";
    public static final String LOCATION = "Location";
    public static final String PHOTOS = "Photos";
    public static final String Restaurant = "Restaurant";
    public static final String Type = "Type";
    public static final String foodPrice = "foodPrice";
    public static final String Stock = "Stock";

    public Cuisine() {
    }

    public String getNAME() {
        return getString(NAME);
    }

    public Boolean getDESCRIPTION() {
        return getBoolean(DESCRIPTION);
    }

    public String getLocation(){

        return getString(LOCATION);
    }
    public AVObject getPHOTOS(){
        return getAVObject(PHOTOS);
    }
    public AVObject getRestaurant(){
        return getAVObject(Restaurant);
    }
    public AVObject getType(){
        return getAVObject(Type);
    }
    public Double getfoodPrice(){
        return (Double) getDouble(foodPrice);
    }
    public Number getStock(){
        return getNumber(Stock);
    }

}
