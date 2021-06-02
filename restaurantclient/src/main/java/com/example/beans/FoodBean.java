package com.example.beans;



import com.example.Models.Cuisine;

import java.io.Serializable;

import cn.leancloud.AVObject;

public class FoodBean implements Serializable{
	public String id;
	public String foodName;
	public String foodType;
	public String foodSummary;
	public int foodSale;
	public double foodPrice;
	public byte[] foodImage;
	public int selectCount=0;
	public String Image_url;
	public String cuisineOBJ;

	public String getId() {
		return id;
	}

	public void changeSelectCount(int num){
		if(num>=0){
			selectCount+=num;
		}else{
			if(selectCount+num<0)selectCount=0;
			else selectCount+=num;
		}
	}
	public FoodBean(String id, String name, String type, String summary, int sale, double foodPrice){
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
		this.cuisineOBJ = cuisineOBJ.toString();
	}

	public AVObject getCuisineOBJ(){
		return AVObject.parseAVObject(cuisineOBJ);
	}

	public FoodBean(){

	}
}
