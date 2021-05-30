package com.app.Models;

import android.graphics.Bitmap;

import com.app.myapplication.adapters.FoodAdapter;

public interface ImageListener {
    void success(Bitmap data);
    void failed(String reason);
}
