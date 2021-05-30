package com.app.utils;

import com.app.beans.FoodBean;

import java.util.List;


import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TestUtils {

    public static void test1(String id){
        AVQuery<AVObject> query = new AVQuery<>("Cuisine");
        query.include("Cuisine_Type");
        AVObject pointer = AVObject.createWithoutData("Restaurant", id);
        query.whereEqualTo("Restaurant",pointer);
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {System.out.println();}
            public void onNext(List<AVObject> list) {
                for (AVObject avObject : list) {
                    System.out.println(avObject.get("Name"));
                }
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        });
    }
}
