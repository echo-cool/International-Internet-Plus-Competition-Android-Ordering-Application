package com.app.utils;


import android.content.Context;
import android.graphics.BitmapFactory;


import com.app.beans.FoodBean;
import com.app.beans.TypeBean;
import com.app.myapplication.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class BaseUtils {
	static String[] foods={ "烤肉","炸串","肉丸子","肉汤","猪排","火烧驴肉", "锅包肉","烤面筋"};
	static double[] prices={9,12.5,8,14.5,15,8,16,15,8,20};
	static int[] sales={21,8,15,7,7,3,19,10,14,13,11,11,11};



	public static List<FoodBean> getDatas(Context context) {
		ArrayList<FoodBean> fList = new ArrayList<>();
		String[] classes=new String[]{"猪食","小吃"} ;
		for (int i = 0; i < 8; i++) {
			FoodBean foodBean = new FoodBean();
			foodBean.id=i;
			foodBean.foodName=(foods[i]);
			foodBean.foodPrice=prices[i];
			foodBean.foodSale=10;
			foodBean.foodSummary="hehhe";
			foodBean.selectCount=0;
			foodBean.foodImage= BitmapFactory.decodeResource(context.getResources(),R.drawable.foodexample1);
			if(i<1)
				foodBean.foodType=(classes[0] );
			else
				foodBean.foodType=(classes[1] );


			fList.add(foodBean);
		}
		return fList;
	}




}
