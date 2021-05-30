package com.app.beans;

import android.graphics.Bitmap;

import java.io.Serializable;

import cn.leancloud.AVObject;

public class MerchantBean implements Serializable {
    public String id;
    public String mctName;
    public Bitmap mctImage;
    public AVObject merchantOBJ;

    public MerchantBean(String id, String name){
        this.id = id;
        mctName = name;
    }

    public AVObject getMerchantOBJ() {
        return merchantOBJ;
    }

    public void setMerchantOBJ(AVObject merchantOBJ) {
        this.merchantOBJ = merchantOBJ;
    }
}
