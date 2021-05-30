package com.app.beans;

import android.graphics.Bitmap;

import java.io.Serializable;

public class MerchantBean implements Serializable {
    public String id;
    public String mctName;
    public Bitmap mctImage;

    public MerchantBean(String id, String name){
        this.id = id;
        mctName = name;
    }
}
