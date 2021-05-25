package com.app.utils;


import android.content.Context;


import com.app.beans.FoodBean;
import com.app.beans.TypeBean;
import com.app.myapplication.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class BaseUtils {
	static String[] foods={ "Grilled Cold Noodles","Skinny Pork Porridge with Egg","Spare Ribs Ramen","Chicken and Peanut Rice","Baked Rice with Cheese","Pork dumpling", "Cheese rice","Reef fried noodles","Steak rice","Pancakes with mixed grains"};
	static double[] prices={9,12.5,8,14.5,15,8,16,15,8,20};
	static int[] sales={21,8,15,7,7,3,19,10,14,13,11,11,11};

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

	public static List<FoodBean> getDatas(Context context) {
		ArrayList<FoodBean> fList = new ArrayList<>();
		String[] classes=new String[]{"Staple food","Snack"} ;
		for (int i = 0; i < 8; i++) {
			FoodBean foodBean = new FoodBean();
			foodBean.id=i;
			foodBean.foodName=(foods[i]);
			foodBean.foodPrice=prices[i];
			foodBean.foodSale=10;
			foodBean.foodType=(classes[i/ 4] );
			int resID =R.drawable.shop_image_loading;
			fList.add(foodBean);
		}
		return fList;
	}

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
