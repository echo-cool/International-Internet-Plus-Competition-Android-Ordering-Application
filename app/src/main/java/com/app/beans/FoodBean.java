package com.app.beans;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;


import com.app.Models.Cuisine;
import com.app.Models.ImageListener;
import com.app.myapplication.fragments.ShopOrderFragment;
import com.app.utils.ViewUtils;

import java.io.Serializable;
import java.math.BigDecimal;

public class FoodBean implements Serializable{
	public String id;
	public String foodName;
	public String foodType;
	public String foodSummary;
	public int foodSale;
	public double foodPrice;
	public Bitmap foodImage;
	public int selectCount=0;
	public TypeBean FoodTypeBean;
	public String Image_url;
	public Cuisine cuisineOBJ;
	public void changeSelectCount(int num){
		if(num>=0){
			selectCount+=num;
		}else{
			if(selectCount+num<0)selectCount=0;
			else selectCount+=num;
		}
	}
	public FoodBean(String id,String name,String type,String summary,int sale,double foodPrice){
		this.id=id;
		this.foodName=name;
		this.foodType=type;
		this.foodSummary=summary;
		this.foodSale=sale;
		this.foodPrice=foodPrice;
	}

	public void setImage_url(String image_url) {
		Image_url = image_url;
	}

	public void setCuisineOBJ(Cuisine cuisineOBJ) {
		this.cuisineOBJ = cuisineOBJ;
	}

	public FoodBean(){

	}
}
