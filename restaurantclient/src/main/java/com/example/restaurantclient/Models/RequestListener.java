package com.example.restaurantclient.Models;

import java.util.List;

public interface RequestListener<T> {
    void success(List<T> data);
    void processing(int now, int size, List<T> data);
    void failed(String reason);
}
