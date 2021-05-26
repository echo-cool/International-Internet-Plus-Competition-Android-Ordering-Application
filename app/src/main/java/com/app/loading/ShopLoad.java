package com.app.loading;

import android.content.Context;

import com.app.beans.FoodBean;
import com.app.beans.TypeBean;

import java.util.ArrayList;
import java.util.List;

public class ShopLoad {
    public static List<TypeBean> getTypes() {
        ArrayList<TypeBean> tList = new ArrayList<>();
        String[] classes=new String[]{"Staple food","Snack"} ;
        for (int i = 0; i < 2; i++) {
            TypeBean typeBean = new TypeBean();
            typeBean.setName(classes[i]);
            tList.add(typeBean);
        }
        return tList;
    }

//    public static List<FoodBean> getFoods(Context context) {
//        ArrayList<FoodBean> fList = new ArrayList<>();
//        String[] classes=new String[]{"Staple food","Snack"} ;
//        for (int i = 0; i < 8; i++) {
//            FoodBean foodBean = new FoodBean();
//            foodBean.setId(i);
//           // foodBean.setName(foods[i]);
//            //foodBean.setPrice(BigDecimal.valueOf(prices[i]).setScale(1, BigDecimal.ROUND_HALF_DOWN));
//            //foodBean.setSale("Days sales:" + sales[i]);
//            foodBean.setType(classes[i/ 4] );
//            int resID = context.getResources().getIdentifier("food" + i, "drawable", "com.example.canteenapp");
//            foodBean.setIcon(resID);
//            fList.add(foodBean);
//        }
//        return fList;
//    }

    public static List<FoodBean> getDetails(List<FoodBean> fList) {
        ArrayList<FoodBean> flist = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            if (fList.size() > i * 10) {
                flist.add(fList.get(i * 10 - 1));
                flist.add(fList.get(i * 10));
            } else {
                break;
            }
        }
        return flist;
    }
}
