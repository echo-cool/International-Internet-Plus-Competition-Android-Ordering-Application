package com.example.restaurantclient.Models;

import android.graphics.Bitmap;

public interface ImageListener {
    void success(Bitmap data);
    void failed(String reason);
}
