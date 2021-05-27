package com.app.beans;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;


import com.app.utils.ViewUtils;

import java.io.Serializable;
import java.math.BigDecimal;

public class FoodBean implements Serializable{
	public int id;
	public String foodName;
	public String foodType;
	public String foodSummary;
	public int foodSale;
	public double foodPrice;
	public Bitmap foodImage;
	public int selectCount=0;
	public TypeBean FoodTypeBean;
	public void changeSelectCount(int num){
		if(num>=0){
			selectCount+=num;
		}else{
			if(selectCount+num<0)selectCount=0;
			else selectCount+=num;
		}
	}

	public FoodBean() {
	}

	public FoodBean(int id, String name, String type, String summary, int sale, double foodPrice){

	}
}
