package com.example.restaurantclient.Models;

import android.os.Parcelable;

import cn.leancloud.AVObject;
import cn.leancloud.AVParcelableObject;
import cn.leancloud.annotation.AVClassName;

@AVClassName("Cuisine_Type")
public class Cuisine_Type extends AVObject{
    public static final Parcelable.Creator CREATOR = AVParcelableObject.AVObjectCreator.instance;
    public static final String NAME = "Name";
    public static final String ENABLE = "Enable";

    public Cuisine_Type() {
    }

    public String getNAME() {
        return getString(NAME);
    }

    public Boolean getENABLE() {
        return getBoolean(ENABLE);
    }
    public void setName(String name){
        put(NAME, name);
    }
}
